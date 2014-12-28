package de.th.wildau.recruiter.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import lombok.Getter;
import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.th.wildau.recruiter.ejb.BusinessError;
import de.th.wildau.recruiter.ejb.BusinessException;
import de.th.wildau.recruiter.ejb.service.UserService;

/**
 * Base controller class for account signin.
 * 
 * @author s7n
 *
 */
@ManagedBean
@ViewScoped
public class SigninHome extends AbstractHome {

	private static final Logger log = LoggerFactory.getLogger(SigninHome.class);

	private static final long serialVersionUID = 227908819395127476L;

	@Getter
	@Setter
	private String email;

	@Getter
	@Setter
	private String password;

	@Inject
	private UserService userService;

	/**
	 * Authenticate a user with the emai and password.
	 * 
	 * @return String navigation url
	 */
	public String authenticate() {
		log.debug("authenticate user");
		if (!this.may.isAuthenticated()) {
			try {
				this.userService.authenticatePre(this.email.toLowerCase());
				getRequest().login(
						this.email.toLowerCase(),
						this.password
								+ this.userService.getPasswordSalt(this.email));
				this.userService.authenticatePost(this.email);
				addInfoMessage("msg.signin.successful");
			} catch (final ServletException e) {
				log.error(e.getMessage());
				addErrorMessage(new BusinessException(
						BusinessError.SIGNIN_FAILED));
			} catch (final BusinessException e) {
				log.error(e.getMessage());
				addErrorMessage(e);
			}
		}
		// navigation cases
		if (this.may.isUser() || this.may.isCompany()) {
			return "/my/index.jsf";
		} else if (this.may.isAdmin()) {
			return "/admin/index.jsf";
		}
		return "";
	}

	/**
	 * Logout method, which kill the current session.
	 * 
	 * @return redirect to root path
	 * 
	 * @throws ServletException
	 */
	public String logout() {
		if (this.may.isAuthenticated()) {
			try {
				getRequest().logout();
			} catch (final ServletException e) {
				log.error(e.getMessage());
			}
			final HttpSession session = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext()
					.getSession(false);
			if (session != null) {
				session.invalidate();
			}
			addInfoMessage("msg.signout");
		}
		return getContextPublic();
	}
}
