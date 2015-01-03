package de.th.wildau.recruiter.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.th.wildau.recruiter.ejb.BusinessException;
import de.th.wildau.recruiter.ejb.RoleName;
import de.th.wildau.recruiter.ejb.model.Address;
import de.th.wildau.recruiter.ejb.model.Role;
import de.th.wildau.recruiter.ejb.model.User;
import de.th.wildau.recruiter.ejb.service.UserService;

/**
 * Base controller class for account registration.
 * 
 * @author s7n
 *
 */
@Named
@ViewScoped
public class RegisterHome extends AbstractHome {

	private static final Logger log = LoggerFactory
			.getLogger(RegisterHome.class);

	private static final long serialVersionUID = 1973236652184354659L;

	@Getter
	@Setter
	private Address address;

	@Getter
	@Setter
	@Email
	private String email;

	@Getter
	@Setter
	private String password;

	@Getter
	@Setter
	private RoleName role;

	@Getter
	private List<SelectItem> roleSelectItem;

	@Getter
	@Setter
	private User user;

	@Inject
	private UserService userService;

	@PostConstruct
	public void init() {
		log.debug("init");
		this.user = new User();
		this.address = new Address();
		this.role = RoleName.USER;
		this.roleSelectItem = new ArrayList<>();
		for (final Role role : this.userService.getRoles()) {
			this.roleSelectItem.add(new SelectItem(role.getName(),
					getMessage(role.getName())));
		}
	}

	public String register() {
		log.info("register new user");
		try {
			this.user.getRoles().add(this.userService.getRole(this.role));
			this.userService.register(this.email, this.password, this.role,
					this.user, this.address);
			log.info("... user created");
			addInfoMessage("msg.signup.emailSend");
			return getContextPublic();
		} catch (final BusinessException e) {
			log.error(e.getMessage());
			addErrorMessage(e);
		}
		return "";
	}
}
