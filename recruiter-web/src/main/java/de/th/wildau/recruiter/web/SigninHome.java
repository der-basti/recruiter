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

import de.th.wildau.recruiter.ejb.service.UserService;

@ManagedBean
@ViewScoped
public class SigninHome extends AbstractHome {

	private static final long serialVersionUID = 227908819395127476L;

	private final Logger log = LoggerFactory.getLogger(SigninHome.class);

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
		this.log.debug("authenticate user");
		if (!this.may.isAuthenticated()) {
			try {
				// FIXME move logic to backing beans
				// check login attempts
				if (this.userService.signinAttemptsTooMuch(this.email)) {
					addErrorMessage("msg.sign.resetPw");
					return "";
				}
				// login call
				getRequest().login(
						this.email.toLowerCase(),
						this.password
								+ this.userService.getPasswordSalt(this.email));
				// success login > reset attempts
				this.userService.signinAttemptsReset(this.email);
				addInfoMessage("msg.signin.successful");
				// navigation
				if (isUser() || isCompany()) {
					return redirect("my/");
				} else if (isAdmin()) {
					return redirect("admin/");
				}
			} catch (final ServletException e) {
				this.log.error(e.getMessage());
			}
			// login attempts +1
			this.userService.signinAttemptsPlus(this.email);
			addErrorMessage("msg.signin.failed");
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
				this.log.error(e.getMessage());
			}
			HttpSession session = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext()
					.getSession(false);
			if (session != null) {
				session.invalidate();
			}
			addInfoMessage("msg.signout");
		}
		return redirectToRoot();
	}
}
