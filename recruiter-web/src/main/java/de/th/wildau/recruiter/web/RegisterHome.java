package de.th.wildau.recruiter.web;

import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@RequestScoped
public class RegisterHome extends AbstractHome {

	private static final long serialVersionUID = 1973236652184354659L;

	private final Logger log = LoggerFactory.getLogger(RegisterHome.class);

	@PostConstruct
	public void init() {
		this.log.debug("init");
		// this.user = new User();
	}

	public String register() {
		this.log.info("register new user");
		return "";
	}

}
