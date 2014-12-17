package de.th.wildau.recruiter.ejb.model;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * Contains general payment informations.
 * 
 * @author s7n
 *
 */
@MappedSuperclass
public abstract class PayAbstract extends BaseEntity<PayAbstract> {

	private static final long serialVersionUID = -8840052361539991123L;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private User user;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Purchase purchase;
}
