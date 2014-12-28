package de.th.wildau.recruiter.web;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
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
	private User user;

	@Inject
	private UserService userService;

	@PostConstruct
	public void init() {
		this.user = this.userService.getUser(getRequest().getUserPrincipal()
				.getName(), "roles", "address");
	}
}
