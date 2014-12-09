package de.th.wildau.recruiter.web;

import java.io.IOException;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import lombok.Getter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.th.wildau.recruiter.ejb.RoleName;

/**
 * Contains all general home/view affairs.
 * 
 * @author s7n
 *
 */
public abstract class AbstractHome implements Serializable {

	private static final long serialVersionUID = -7243694648872793544L;

	private final Logger log = LoggerFactory.getLogger(AbstractHome.class);

	@Inject
	protected May may;

	@Getter
	private final String rootContext = "recruiter";

	protected String redirectToRoot() {
		return redirect("");
	}

	protected String redirect(final String urlFromRootContext) {
		StringBuilder sb = new StringBuilder("/");
		// rootContext
		sb.append(this.rootContext).append("/");
		sb.append(urlFromRootContext);
		// parameter
		if (StringUtils.isNotBlank(urlFromRootContext)
				&& urlFromRootContext.matches("(.jsf)$|(.xhtml)$")) {
			sb.append("?faces-redirect=true");
		}
		try {
			// redirect
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect(sb.toString());
			return "";
		} catch (final IOException e) {
			this.log.error(e.getMessage());
		}
		return "NaviFail";
	}

	public boolean isAuthenticated() {
		final String remoteUser = FacesContext.getCurrentInstance()
				.getExternalContext().getRemoteUser();
		if (remoteUser != null && StringUtils.isNotBlank(remoteUser)
				&& !remoteUser.equalsIgnoreCase("anonymous")) {
			return true;
		}
		return false;
	}

	public boolean hasRole(final String roleName) {
		return getRequest().isUserInRole(roleName);
	}

	public boolean isAdmin() {
		return hasRole(RoleName.ADMIN.name());
	}

	public boolean isCompany() {
		return hasRole(RoleName.COMPANY.name());
	}

	public boolean isUser() {
		return hasRole(RoleName.USER.name());
	}

	private String getMessage(final FacesContext facesContext,
			final String msgKey, final Object... args) {
		Locale locale = facesContext.getViewRoot().getLocale();
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		ResourceBundle bundle = ResourceBundle.getBundle("messages", locale,
				classLoader);
		String msgValue = bundle.getString(msgKey);
		return MessageFormat.format(msgValue, args);
	}

	protected void addInfoMessage(final String message, final Object... args) {
		addMessage(FacesMessage.SEVERITY_INFO, message, args);
	}

	protected void addWarningMessage(final String message, final Object... args) {
		addMessage(FacesMessage.SEVERITY_WARN, message, args);
	}

	protected void addErrorMessage(final String message, Object... args) {
		addMessage(FacesMessage.SEVERITY_ERROR, message, args);
	}

	private void addMessage(final Severity severity, final String message,
			Object... args) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null,
				new FacesMessage(severity, getMessage(context, message, args),
						null));
	}

	protected HttpServletRequest getRequest() {
		Object request = FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		return request instanceof HttpServletRequest ? (HttpServletRequest) request
				: null;
	}

	protected String getParam(final String param) {
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> map = context.getExternalContext()
				.getRequestParameterMap();
		return map.get(param);
	}

	protected Long getParamId(final String param) {
		return Long.valueOf(getParam(param));
	}
}
