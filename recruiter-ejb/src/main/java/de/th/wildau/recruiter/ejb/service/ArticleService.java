package de.th.wildau.recruiter.ejb.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.th.wildau.recruiter.ejb.model.Article;

@Stateless
@LocalBean
public class ArticleService {

	private static final Logger log = LoggerFactory
			.getLogger(ArticleService.class);

	@Inject
	private CrudService crud;

	@Inject
	private Principal principal;

	@RolesAllowed({ "ADMIN", "COMPANY", "USER" })
	public void create() {
		// TODO implement
	}

	@RolesAllowed({ "ADMIN", "COMPANY", "USER" })
	public void delete() {
		// TODO implement
	}

	/**
	 * Find all articles order by create date.
	 * 
	 * @return List of articles
	 */
	@PermitAll
	public List<Article> findArticles() {
		try {
			final CriteriaBuilder cb = this.crud.em.getCriteriaBuilder();
			final CriteriaQuery<Article> cq = cb.createQuery(Article.class);
			final Root<Article> r = cq.from(Article.class);
			cq.select(r).orderBy(cb.desc(r.get("createDate")));
			final TypedQuery<Article> q = this.crud.em.createQuery(cq);
			return q.getResultList();
		} catch (final NoResultException e) {
			log.error("can not find articles", e);
			return new ArrayList<>();
		}
	}

	@RolesAllowed({ "ADMIN", "COMPANY", "USER" })
	public int findFreePurchases() {
		// TODO implement
		return -1;
	}

	/**
	 * Get a specific article.
	 * 
	 * @param id
	 * @return Article or null
	 */
	@PermitAll
	public Article getArticle(final Integer id) {
		try {
			final CriteriaBuilder cb = this.crud.em.getCriteriaBuilder();
			final CriteriaQuery<Article> cq = cb.createQuery(Article.class);
			final Root<Article> r = cq.from(Article.class);
			cq.select(r).where(cb.equal(r.get("id"), id));
			final TypedQuery<Article> q = this.crud.em.createQuery(cq);
			return q.getSingleResult();
		} catch (final NoResultException e) {
			log.error("can not find article", e);
			return null;
		}

	}

	@RolesAllowed({ "ADMIN", "COMPANY", "USER" })
	public void purchase(final int count) {
		// TODO implement
	}

	@RolesAllowed({ "ADMIN", "COMPANY", "USER" })
	public void update() {
		// TODO implement
	}
}
