package de.th.wildau.recruiter.ejb.service;

import javax.ejb.Asynchronous;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class SBean
 */
@Stateless
@LocalBean
@Asynchronous
public class SBean implements SBeanRemote, SBeanLocal {

	/**
	 * Default constructor.
	 */
	public SBean() {
		// TODO Auto-generated constructor stub
	}

}
