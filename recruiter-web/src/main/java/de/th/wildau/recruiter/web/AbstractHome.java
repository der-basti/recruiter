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

public abstract class AbstractHome implements Serializable {

	private static final long serialVersionUID = -7243694648872793544L;

	@Inject
	protected May may;

	@Getter
	private final String rootContext = "was";

	protected String redirectToRoot() {
		return redirect("");
	}

	protected String redirect(final String urlFromRootContext) {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("/" + rootContext + "/" + urlFromRootContext);
		} catch (final IOException e) {
			e.printStackTrace();
			return "fail";
		}
		return "success";
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
