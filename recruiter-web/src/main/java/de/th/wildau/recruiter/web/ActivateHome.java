package de.th.wildau.recruiter.web;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.th.wildau.recruiter.ejb.BusinessException;
import de.th.wildau.recruiter.ejb.service.UserService;

@Named
@ViewScoped
public class ActivateHome extends AbstractHome {

	private static final Logger log = LoggerFactory
			.getLogger(ActivateHome.class);

	private static final long serialVersionUID = 1359760271587169839L;

	@Getter
	@Setter
	private String email;

	@Getter
	@Setter
	private String key;

	@Inject
	private UserService userService;

	public void init() {
		try {
			this.userService.activate(getParam("email"), getParam("key"));
			addInfoMessage("msg.signup.activated");
		} catch (final BusinessException e) {
			log.error(e.getMessage());
			addErrorMessage(e);
		}
	}
}
