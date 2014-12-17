package de.th.wildau.recruiter.ejb.service;

import javax.ejb.Asynchronous;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class SBean
 */
@Stateless
// https://stackoverflow.com/questions/10889563/ejb-3-1-localbean-vs-no-annotation/10896403#10896403
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
