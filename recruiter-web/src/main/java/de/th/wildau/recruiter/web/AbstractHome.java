package de.th.wildau.recruiter.web;

import java.io.IOException;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Validation;
import javax.validation.Validator;

import lombok.AccessLevel;
import lombok.Getter;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import de.th.wildau.recruiter.ejb.BusinessError;
import de.th.wildau.recruiter.ejb.BusinessException;
import de.th.wildau.recruiter.ejb.model.Role;

/**
 * Contains all general home/view affairs.
 * 
 * @author s7n
 *
 */
public abstract class AbstractHome implements Serializable {

	private static final long serialVersionUID = -7243694648872793544L;

	@Getter(value = AccessLevel.PROTECTED)
	private final String contextPublic = "/public/index.jsf";

	@Getter
	private final String contextRoot = "recruiter";

	// ((HttpServletRequest)
	// FacesContext.getCurrentInstance().getExternalContext().getRequest()).getContextPath()

	@Inject
	protected May may;

	/**
	 * Cutting a string to 100 charechters.
	 * 
	 * @param value
	 * @return String cutting
	 */
	public String cutting(final String value) {
		return cutting(value, 50);
	}

	public String roleSet2String(final Collection<Role> roles) {
		final StringBuilder sb = new StringBuilder();
		for (final Iterator<Role> i = roles.iterator(); i.hasNext();) {
			sb.append(getMessage(i.next().getName()));
			if (i.hasNext()) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}

	/**
	 * Unescape html string.
	 * 
	 * @param value
	 * @return String unescape
	 */
	public String unescape(final String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		return StringEscapeUtils.unescapeHtml4(value);
	}

	private void addFacesMessage(final Severity severity, final String message) {
		final FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(severity, message, null));
	}

	private String cutting(final String value, final int length) {
		if (StringUtils.isEmpty(value)) {
			return value;
		} else if (value.length() < length) {
			return value;
		} else {
			return value.substring(0, length) + "...";
		}
	}

	private String getMessage(final FacesContext facesContext,
			final String msgKey, final Object... args) {
		final Locale locale = facesContext.getViewRoot().getLocale();
		final ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		final ResourceBundle bundle = ResourceBundle.getBundle("messages",
				locale, classLoader);
		final String msgValue = bundle.getString(msgKey);
		return MessageFormat.format(msgValue, args);
	}

	protected void addErrorMessage(final BusinessException e) {
		for (final BusinessError error : e.getErrors()) {
			addFacesMessage(FacesMessage.SEVERITY_ERROR, getMessage(error));
		}
	}

	protected void addErrorMessage(final String message, final Object... args) {
		addFacesMessage(FacesMessage.SEVERITY_ERROR,
				getMessage(FacesContext.getCurrentInstance(), message, args));
	}

	protected void addInfoMessage(final String message, final Object... args) {
		addFacesMessage(FacesMessage.SEVERITY_INFO,
				getMessage(FacesContext.getCurrentInstance(), message, args));
	}

	protected void addWarningMessage(final String message, final Object... args) {
		addFacesMessage(FacesMessage.SEVERITY_WARN,
				getMessage(FacesContext.getCurrentInstance(), message, args));
	}

	protected String getMessage(final Enum<?> e) {
		return getMessage(FacesContext.getCurrentInstance(), "enum."
				+ e.getClass().getSimpleName() + "." + e.name());
	}

	protected String getParam(final String param) {
		final FacesContext context = FacesContext.getCurrentInstance();
		final Map<String, String> map = context.getExternalContext()
				.getRequestParameterMap();
		return map.get(param);
	}

	protected HttpServletRequest getRequest() {
		final Object request = FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
		return request instanceof HttpServletRequest ? (HttpServletRequest) request
				: null;
	}

	protected Validator getValidator() {
		return Validation.buildDefaultValidatorFactory().getValidator();
	}

	protected String redirect(final String urlFromRootContext) {
		try {
			// redirect
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("/" + this.contextRoot + urlFromRootContext);
		} catch (final IOException e) {
			// log.error(e.getMessage());
		}
		return urlFromRootContext;
	}
}
