package de.th.wildau.recruiter.web;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import de.th.wildau.recruiter.ejb.model.User;
import de.th.wildau.recruiter.ejb.service.UserService;

@Named
@ViewScoped
public class DashboardAdmin extends AbstractHome {

	private static final long serialVersionUID = 7490377105913699444L;

	@Getter
	private List<User> users;

	@Inject
	private UserService userService;

	@PostConstruct
	public void init() {
		this.users = this.userService.getUsers();
	}

	// XXX
	// https://stackoverflow.com/questions/6141237/how-to-get-number-of-connected-users-and-their-role-using-j-security-check
}
