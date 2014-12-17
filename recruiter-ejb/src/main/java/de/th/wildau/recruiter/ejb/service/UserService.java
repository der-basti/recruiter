package de.th.wildau.recruiter.ejb.service;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.th.wildau.recruiter.ejb.model.User;

@Stateless
@LocalBean
public class UserService {

	private final Logger log = LoggerFactory.getLogger(UserService.class);

	@Inject
	private CrudService crud;

	@PersistenceContext
	protected EntityManager em;

	/**
	 * Find salt for a specific user.
	 * 
	 * @param email
	 * @return String salt
	 */
	@PermitAll
	public String getPasswordSalt(final String email) {
		try {
			CriteriaBuilder cb = this.crud.em.getCriteriaBuilder();
			CriteriaQuery<User> cq = cb.createQuery(User.class);
			Root<User> r = cq.from(User.class);
			cq.select(r);
			// TODO meta model
			cq.where(cb.equal(r.get("email"), email.toLowerCase()));
			TypedQuery<User> q = this.crud.em.createQuery(cq);
			return q.getSingleResult().getPasswordSalt();
		} catch (final NoResultException e) {
			this.log.error("can not find password salt for email " + email);
			return "";
		}
	}

	/**
	 * Check signin attempts.
	 * 
	 * @param email
	 * @return true when too much attempts, else false
	 */
	@PermitAll
	public boolean signinAttemptsTooMuch(final String email) {
		User u = getUser(email.toLowerCase());
		if (u != null) {
			return u.getSigninAttempts() >= 2;
		}
		return true;
	}

	/**
	 * Increase user signin attempt count.
	 * 
	 * @param email
	 */
	@PermitAll
	public void signinAttemptsPlus(final String email) {
		User u = getUser(email.toLowerCase());
		if (u != null) {
			Integer newCount = u.getSigninAttempts() + 1;
			u.setSigninAttempts(newCount);
			this.crud.merge(u);
		}
	}

	/**
	 * Reset user signin attempts.
	 * 
	 * @param email
	 */
	@PermitAll
	public void signinAttemptsReset(final String email) {
		User u = getUser(email.toLowerCase());
		if (u != null) {
			u.setSigninAttempts(0);
			this.crud.merge(u);
		}
	}

	/**
	 * Get user by email address.
	 * 
	 * @param email
	 * @return User or null if user doesn't exist.
	 */
	public User getUser(final String email) {
		CriteriaBuilder cb = this.em.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> r = cq.from(User.class);
		// TODO meta model
		cq.select(r).where(cb.equal(r.get("email"), email.toLowerCase()));
		return this.em.createQuery(cq).getSingleResult();
	}

	@RolesAllowed({ "ADMIN" })
	public List<User> getUsers() {
		CriteriaBuilder cb = this.em.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> r = cq.from(User.class);
		// TODO meta model
		cq.orderBy(cb.asc(r.get("email")));
		return this.em.createQuery(cq).getResultList();
	}
}
