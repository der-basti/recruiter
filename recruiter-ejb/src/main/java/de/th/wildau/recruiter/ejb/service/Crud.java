package de.th.wildau.recruiter.ejb.service;

import java.security.Principal;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import de.th.wildau.recruiter.ejb.model.BaseEntity;

/**
 * Create Read Update Delete.
 * 
 * @author s7n
 */
public abstract class Crud {

	@Inject
	private Principal principal;

	@PersistenceContext
	protected EntityManager em;

	/**
	 * Create new {@link BaseEntity} instance.
	 *
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	protected <T extends BaseEntity<T>> T createNew(final Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch (final InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Delete {@link BaseEntity}.
	 *
	 * @param <T>
	 * @param entity
	 */
	protected <T extends BaseEntity<T>> void delete(final T entity) {
		this.em.remove(entity);
	}

	/**
	 * Find all {@link BaseEntity} objects.
	 *
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	protected <T extends BaseEntity<T>> List<T> findAll(final Class<T> clazz) {
		final CriteriaBuilder builder = this.em.getCriteriaBuilder();
		final CriteriaQuery<T> query = builder.createQuery(clazz);
		return this.em.createQuery(query.select(query.from(clazz)))
				.getResultList();
	}

	/**
	 * Find a specific {@link BaseEntity}.
	 *
	 * @param <T>
	 * @param clazz
	 * @param id
	 * @return
	 */
	protected <T extends BaseEntity<T>> T findById(final Class<T> clazz,
			final long id) {
		return this.em.find(clazz, id);
	}

	/**
	 * Get current user id (email).
	 * 
	 * @return String email
	 */
	protected String getCurrentUserId() {
		return this.principal.getName();
	}

	/**
	 * Update (merge) given {@link BaseEntity}.
	 *
	 * @param <T>
	 * @param entity
	 * @return
	 */
	protected <T extends BaseEntity<T>> T merge(final T entity) {
		return this.em.merge(entity);
	}

	/**
	 * Persist the {@link BaseEntity}.
	 *
	 * @deprecated please use {@link #save(BaseEntity)}
	 * @param <T>
	 * @param entity
	 */
	@Deprecated
	protected <T extends BaseEntity<T>> void persist(final T entity) {
		this.em.persist(entity);
	}

	/**
	 * Save entity method. Persist entity, if is not and merge is entity already
	 * persist.
	 * 
	 * @param entity
	 */
	protected <T extends BaseEntity<T>> void save(final T entity) {
		if (entity.isTransient()) {
			this.em.merge(entity);
		} else {
			this.em.persist(entity);
		}
	}

}
