package de.th.wildau.recruiter.web;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.th.wildau.recruiter.ejb.BusinessException;
import de.th.wildau.recruiter.ejb.service.UserService;

/**
 * 
 * @author s7n
 *
 */
@Named
@ViewScoped
public class ChangeEmailHome extends AbstractHome {

	private static final Logger log = LoggerFactory
			.getLogger(ChangeEmailHome.class);

	private static final long serialVersionUID = 7072531855657861217L;

	@Getter
	@Setter
	private String emailNew;

	@Getter
	private String emailOld;

	@Getter
	@Setter
	private String password;

	@Inject
	private SigninHome signinHome;

	@Inject
	private UserService userService;

	public String change() {
		log.info("change email");
		try {
			this.userService.changeEmail(this.password, this.emailOld,
					this.emailNew);
			addInfoMessage("msg.profile.updated");
			return this.signinHome.logout();
		} catch (final BusinessException e) {
			log.error(e.getMessage());
			addErrorMessage(e);
		}
		return "";
	}

	@PostConstruct
	public void init() {
		this.emailOld = getParam("email");
	}
}
