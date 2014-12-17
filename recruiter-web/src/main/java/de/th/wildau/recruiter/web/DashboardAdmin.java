package de.th.wildau.recruiter.web;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.th.wildau.recruiter.ejb.model.User;
import de.th.wildau.recruiter.ejb.service.UserService;
import lombok.Getter;

@Named
@ViewScoped
public class DashboardAdmin extends AbstractHome {

	private static final long serialVersionUID = 7490377105913699444L;

	@Inject
	private UserService userService;

	@Getter
	private List<User> users;

	@PostConstruct
	public void init() {
		this.users = this.userService.getUsers();
	}

	// XXX
	// https://stackoverflow.com/questions/6141237/how-to-get-number-of-connected-users-and-their-role-using-j-security-check
}
