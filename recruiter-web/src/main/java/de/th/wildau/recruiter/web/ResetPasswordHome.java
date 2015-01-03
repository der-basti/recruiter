package de.th.wildau.recruiter.web;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.th.wildau.recruiter.ejb.service.UserService;

/**
 * 
 * @author s7n
 *
 */
@Named
@ViewScoped
public class ResetPasswordHome extends AbstractHome {

	private static final Logger log = LoggerFactory
			.getLogger(ResetPasswordHome.class);

	private static final long serialVersionUID = 7072531855657861217L;

	@Getter
	@Setter
	@Email
	private String email;

	@Inject
	private UserService userService;

	@PostConstruct
	public void init() {
		log.debug("init");

	}

	public String reset() {
		log.info("reset password");
		this.userService.resetPasswordTokens(this.email);
		return "";
	}
}
