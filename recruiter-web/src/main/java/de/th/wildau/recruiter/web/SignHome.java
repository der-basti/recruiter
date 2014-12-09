package de.th.wildau.recruiter.web;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import lombok.Getter;
import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean
@ViewScoped
public class SignHome extends AbstractHome {

	private static final long serialVersionUID = 227908819395127476L;

	private final Logger log = LoggerFactory.getLogger(SignHome.class);

	@Getter
	@Setter
	private String email;

	@Getter
	@Setter
	private String password;

	@PostConstruct
	public void init() {
		// this.user = new User();
	}

	public String register() {
		return "";
	}

	/**
	 * Authenticate a user with the emai and password.
	 * 
	 * @return String navigation url
	 */
	public String authenticate() {
		this.log.debug("authenticate user");
		if (!this.may.isAuthenticated()) {
			try {
				// FIXME password salt
				final String salt = "7MkZdGswgzvk1cDocG4v"; // this.userService.getSalt(this.email)
				getRequest().login(this.email.toLowerCase(),
						this.password + salt);
				addInfoMessage("msg.SigninSuccessful");
				if (isUser() || isCompany()) {
					return redirect("my/");
				} else if (isAdmin()) {
					return redirect("admin/");
				}
			} catch (final ServletException e) {
				this.log.error(e.getMessage());
			}
			// TODO +1 login try
			addErrorMessage("msg.SigninFailed");
		}
		return "public/signin.jsf";
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
			} catch (ServletException e) {
				this.log.error(e.getMessage());
			}
			HttpSession session = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext()
					.getSession(false);
			if (session != null) {
				session.invalidate();
			}
			// FIXME addInfoMessage("msgAuthHomeSignout");
		}
		return redirectToRoot();
	}
}
