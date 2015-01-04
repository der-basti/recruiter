package de.th.wildau.recruiter.web;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import de.th.wildau.recruiter.ejb.BusinessException;
import de.th.wildau.recruiter.ejb.model.User;
import de.th.wildau.recruiter.ejb.service.UserService;

/**
 * Base controller class for profile management.
 * 
 * @author s7n
 *
 */
@Named
@ViewScoped
public class ProfileHome extends AbstractHome {

	private static final long serialVersionUID = -8059542501435440194L;

	@Getter
	@Setter
	private User user;

	@Inject
	private UserService userService;

	/**
	 * Navigate to change password site.
	 * 
	 * @return String navigation
	 */
	public String changePw() {
		return redirect("/my/changePassword.jsf?email=" + this.user.getEmail());
	}

	@PostConstruct
	public void init() {
		this.user = this.userService.getUser(getRequest().getUserPrincipal()
				.getName(), "roles", "address");
	}

	public String updateProfile() {
		try {
			this.userService.updateProfile(this.user);
		} catch (final BusinessException e) {
			addErrorMessage(e);
		}
		return "";
	}
}
