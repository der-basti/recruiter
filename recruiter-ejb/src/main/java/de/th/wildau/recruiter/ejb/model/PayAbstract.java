package de.th.wildau.recruiter.ejb.model;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class PayAbstract extends BaseEntity<PayAbstract> {

	private static final long serialVersionUID = -8840052361539991123L;

	@ManyToOne
	private User user;
}
