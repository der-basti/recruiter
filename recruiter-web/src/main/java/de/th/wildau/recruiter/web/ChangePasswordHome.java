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
public class ChangePasswordHome extends AbstractHome {

	private static final Logger log = LoggerFactory
			.getLogger(ChangePasswordHome.class);

	private static final long serialVersionUID = 7072531855657861217L;

	@Getter
	private String email;

	@Getter
	@Setter
	private String newPw;

	@Getter
	@Setter
	private String oldPw;

	@Inject
	private UserService userService;

	public String change() {
		log.info("change password");
		try {
			this.userService.changePassword(this.email, this.oldPw, this.newPw);
			return redirect("/my");
		} catch (final BusinessException e) {
			log.error(e.getMessage());
			addErrorMessage(e);
		}
		return "";
	}

	@PostConstruct
	public void init() {
		this.email = getParam("email");
	}
}
