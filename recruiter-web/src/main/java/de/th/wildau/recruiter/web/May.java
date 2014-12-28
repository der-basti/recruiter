package de.th.wildau.recruiter.web;

import java.io.Serializable;

import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import de.th.wildau.recruiter.ejb.RoleName;

/**
 * A general permissions / privileges class.
 * 
 * @author s7n
 *
 */
@Named
@RequestScoped
public class May implements Serializable {

	private static final long serialVersionUID = -8707045774926901160L;

	public boolean hasRole(final String roleName) {
		return getRequest().isUserInRole(roleName);
	}

	public boolean isAdmin() {
		return hasRole(RoleName.ADMIN.name());
	}

	public boolean isAuthenticated() {
		// final String remoteUser = FacesContext.getCurrentInstance()
		// .getExternalContext().getRemoteUser();
		// if (remoteUser != null && StringUtils.isNotBlank(remoteUser)
		// && !remoteUser.equalsIgnoreCase("anonymous")) {
		// return true;
		// }
		// return false;
		return getRequest().getUserPrincipal() != null;
	}

	public boolean isCompany() {
		return hasRole(RoleName.COMPANY.name());
	}

	public boolean isUser() {
		return hasRole(RoleName.USER.name());
	}

	public boolean isUserInRole(final String role) {
		return getRequest().isUserInRole(role);
	}

	protected HttpServletRequest getRequest() {
		final Object request = FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
		return request instanceof HttpServletRequest ? (HttpServletRequest) request
				: null;
	}
}