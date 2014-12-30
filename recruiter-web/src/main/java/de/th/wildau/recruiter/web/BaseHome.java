package de.th.wildau.recruiter.web;

import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.Facelet;
import javax.inject.Named;

import lombok.Getter;

/**
 * Base home class for index and navigation cases.
 * 
 * @author s7n
 *
 */
@Named
@RequestScoped
public class BaseHome extends AbstractHome {

	private static final long serialVersionUID = -1129291375869684411L;

	@Getter
	private String jsfApiLocation;
	@Getter
	private String jsfImplLocation;
	@Getter
	private String jsfTitle;
	@Getter
	private String jsfVendor;
	@Getter
	private String jsfVersion;

	@PostConstruct
	public void init() {
		this.jsfVersion = FacesContext.class.getPackage()
				.getImplementationVersion();
		this.jsfTitle = FacesContext.class.getPackage()
				.getImplementationTitle();
		this.jsfVendor = FacesContext.class.getPackage()
				.getImplementationVendor();
		this.jsfApiLocation = FacesContext.class.getProtectionDomain()
				.getCodeSource().toString();
		this.jsfImplLocation = Facelet.class.getProtectionDomain()
				.getCodeSource().toString();
	}
}
