package de.th.wildau.recruiter.web;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.ServletException;

import lombok.Getter;
import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// @Named
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

	public String authenticate() {
		this.log.debug("authenticate user");
		if (!this.may.isAuthenticated()) {
			try {
				final String salt = "7MkZdGswgzvk1cDocG4v"; // this.userService.getSalt(this.email)
				getRequest().login(this.email.toLowerCase(), this.password + salt);
				// addInfoMessage("Sie haben sich erfolgreich angemeldet");
				return redirect("/my");
			} catch (final ServletException e) {
				this.log.error(e.getMessage());
			}
			// TODO +1 login try
			// addErrorMessage("Fehlerhafte Anmeldedaten");
		}
		return "public/signin.jsf";
	}
}
