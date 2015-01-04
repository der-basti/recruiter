package de.th.wildau.recruiter.ejb.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.th.wildau.recruiter.ejb.BusinessError;
import de.th.wildau.recruiter.ejb.BusinessException;
import de.th.wildau.recruiter.ejb.RoleName;
import de.th.wildau.recruiter.ejb.model.Article;
import de.th.wildau.recruiter.ejb.model.Comment;
import de.th.wildau.recruiter.ejb.model.PayBankCard;
import de.th.wildau.recruiter.ejb.model.PayCreditCard;
import de.th.wildau.recruiter.ejb.model.Price;
import de.th.wildau.recruiter.ejb.model.Purchase;
import de.th.wildau.recruiter.ejb.model.Role;
import de.th.wildau.recruiter.ejb.model.User;

@Stateless
@LocalBean
public class ArticleService extends Crud {

	private static final Logger log = LoggerFactory
			.getLogger(ArticleService.class);

	@Inject
	private UserService userService;

	/**
	 * Create a new article.
	 * 
	 * @param title
	 * @param content
	 */
	@RolesAllowed({ "ADMIN", "COMPANY", "USER" })
	@Transactional
	public void createArticle(final String title, final String content,
			final PayBankCard payBc, final PayCreditCard payCc)
			throws BusinessException {
		// purchase
		final Purchase p = new Purchase();
		p.setPurchaseDate(new Date());
		p.setUser(this.userService.getCurrentUser());
		p.setQuantity(1);
		p.setPrice(findMyPrice().getPrice());
		// article
		final Article a = new Article();
		a.setCreateDate(new Date());
		a.setTitle(title);
		a.setContent(content);
		p.getArticles().add(a);
		// pay
		if (payBc != null) {
			final PayBankCard bc = new PayBankCard();
			bc.setBic(payBc.getBic());
			bc.setIban(payBc.getIban());
			p.setPayBc(bc);
		} else if (payCc != null) {
			final Calendar cal = Calendar.getInstance();
			if (cal.get(Calendar.YEAR) == Integer.valueOf(payCc.getExYear())
					&& cal.get(Calendar.MONTH) < Integer.valueOf(payCc
							.getExYear())) {
				throw new BusinessException(BusinessError.CREDIT_CARD_GONE);
			}
			final PayCreditCard cc = new PayCreditCard();
			cc.setCardType(payCc.getCardType());
			cc.setExMonth(payCc.getExMonth());
			cc.setExYear(payCc.getExYear());
			cc.setName(payCc.getName());
			cc.setNumber(payCc.getNumber());
			p.setPayCc(cc);
		} else {
			throw new BusinessException(BusinessError.INVALID_PAY_INFO);
		}
		save(p);
	}

	/**
	 * Create a comment for a article.
	 * 
	 * @param articleId
	 * @param content
	 * @throws BusinessException
	 */
	@PermitAll
	public void createComment(final Long articleId, final String content)
			throws BusinessException {
		final Article article = findArticle(articleId);
		if (article == null) {
			throw new BusinessException(BusinessError.INVALID_VIEW_ID);
		}
		final Comment comment = new Comment();
		comment.setCreateDate(new Date());
		comment.setArticle(article);
		comment.setContent(content);
		comment.setUser(this.userService.getCurrentUser());
		save(comment);
	}

	@RolesAllowed({ "ADMIN", "COMPANY", "USER" })
	public void delete(final Long articleId) throws BusinessException {
		final Article a = findArticle(articleId);
		if (a == null) {
			throw new BusinessException(BusinessError.INVALID_VIEW_ID);
		}
		isMy(a.getId());
		delete(a);
	}

	/**
	 * Get a specific article.
	 * 
	 * @param id
	 * @return Article or null
	 */
	@PermitAll
	public Article findArticle(final Long id, final String... fetch) {
		if (id == null) {
			return null;
		}
		try {
			final CriteriaBuilder cb = this.em.getCriteriaBuilder();
			final CriteriaQuery<Article> cq = cb.createQuery(Article.class);
			final Root<Article> r = cq.from(Article.class);
			cq.select(r).where(cb.equal(r.get("id"), id));
			// for (final String item : fetch) {
			// r.fetch(item);
			// }
			final Article res = this.em.createQuery(cq).getSingleResult();
			// final TypedQuery<Article> q = this.crud.em.createQuery(cq);
			// final Article res = q.getResultList().get(0);
			// TODO fetch comments.user.address
			if (res != null) {
				for (final Comment c : res.getComments()) {
					if (c.getUser() != null) {
						c.getUser().getAddress().getName();
					}
				}
			}
			return res;
		} catch (final NoResultException e) {
			log.error("can not find article [" + id + "]", e);
			return null;
		}
	}

	/**
	 * Find all articles order by create date.
	 * 
	 * @return List of articles
	 */
	@PermitAll
	public List<Article> findArticles() {
		try {
			final CriteriaBuilder cb = this.em.getCriteriaBuilder();
			final CriteriaQuery<Article> cq = cb.createQuery(Article.class);
			final Root<Article> r = cq.from(Article.class);
			cq.select(r).orderBy(cb.asc(r.get("createDate")),
					cb.asc(r.get("title")));
			final TypedQuery<Article> q = this.em.createQuery(cq);
			return q.getResultList();
		} catch (final NoResultException e) {
			log.error("can not find articles", e);
			return new ArrayList<>();
		}
	}

	@RolesAllowed({ "ADMIN", "COMPANY", "USER" })
	public List<Article> findMyArticles() {
		final User user = this.userService.getCurrentUser();
		if (user == null) {
			return new ArrayList<>();
		}
		final List<Article> res = new ArrayList<>();
		for (final Purchase item : findMyPurchases()) {
			res.addAll(item.getArticles());
		}
		return res;
	}

	/**
	 * Find all comments from the current user.
	 * 
	 * @return
	 */
	@RolesAllowed({ "ADMIN", "COMPANY", "USER" })
	public List<Comment> findMyComments() {
		final User user = this.userService.getCurrentUser();
		if (user == null) {
			return new ArrayList<>();
		}
		try {
			final CriteriaBuilder cb = this.em.getCriteriaBuilder();
			final CriteriaQuery<Comment> cq = cb.createQuery(Comment.class);
			final Root<Comment> r = cq.from(Comment.class);
			r.fetch("user");
			r.fetch("article");
			cq.select(r).where(cb.equal(r.get("user"), user));
			final TypedQuery<Comment> q = this.em.createQuery(cq);
			return q.getResultList();
		} catch (final NoResultException e) {
			log.error("can not find my comments", e);
			return new ArrayList<>();
		}
	}

	/**
	 * Get current price for all roles from the current user (a user should have
	 * only one role).
	 * 
	 * @return
	 */
	@RolesAllowed({ "ADMIN", "COMPANY", "USER" })
	public Price findMyPrice() {
		final User u = this.userService.getUser(getCurrentUserId(), "roles");
		try {
			final CriteriaBuilder cb = this.em.getCriteriaBuilder();
			final CriteriaQuery<Price> cq = cb.createQuery(Price.class);
			final Root<Price> r = cq.from(Price.class);
			for (final Role item : u.getRoles()) {
				cq.select(r).where(cb.equal(r.get("roleName"), item.getName()));
			}
			final TypedQuery<Price> q = this.em.createQuery(cq);
			return q.getSingleResult();
		} catch (final NoResultException e) {
			log.error("can not find my article", e);
			return null;
		}
	}

	/**
	 * Find all purchases from the current user.
	 * 
	 * @return
	 */
	@RolesAllowed({ "ADMIN", "COMPANY", "USER" })
	public List<Purchase> findMyPurchases() {
		final User user = this.userService.getCurrentUser();
		if (user == null) {
			return new ArrayList<>();
		}
		try {
			final CriteriaBuilder cb = this.em.getCriteriaBuilder();
			final CriteriaQuery<Purchase> cq = cb.createQuery(Purchase.class);
			final Root<Purchase> r = cq.from(Purchase.class);
			r.fetch("user");
			r.fetch("articles");
			cq.select(r).where(cb.equal(r.get("user"), user));
			final TypedQuery<Purchase> q = this.em.createQuery(cq);
			return q.getResultList();
		} catch (final NoResultException e) {
			log.error("can not find my article", e);
			return new ArrayList<>();
		}
	}

	/**
	 * Get the price for the current user.
	 * 
	 * @return BigDecimal
	 */
	@PermitAll
	public BigDecimal getPrice() {
		final User user = this.userService.getUser(getCurrentUserId(), "roles");
		if (user != null) {
			for (final Role role : user.getRoles()) {
				return getPrice(role.getName());
			}
		}
		return null;
	}

	/**
	 * Get the price for specific role.
	 * 
	 * @return BigDecimal
	 */
	@PermitAll
	public BigDecimal getPrice(final RoleName role) {
		try {
			final CriteriaBuilder cb = this.em.getCriteriaBuilder();
			final CriteriaQuery<Price> cq = cb.createQuery(Price.class);
			final Root<Price> r = cq.from(Price.class);
			cq.select(r).where(cb.equal(r.get("roleName"), role));
			final TypedQuery<Price> q = this.em.createQuery(cq);
			final Price res = q.getSingleResult();
			return res.getPrice();
		} catch (final NoResultException e) {
			log.error("can not find my price", e);
			return null;
		}
	}

	/**
	 * Update a article from me.
	 * 
	 * @param article
	 * @throws BusinessException
	 */
	@RolesAllowed({ "ADMIN", "COMPANY", "USER" })
	public Article update(final Article article) throws BusinessException {
		if (article == null) {
			throw new BusinessException(BusinessError.INVALID_VIEW_ID);
		}
		isMy(article.getId());
		return merge(article);
	}

	private void isMy(final Long articleId) throws BusinessException {
		final Article article = findArticle(articleId, "purchase.user");
		if (article.getPurchase().getUser().getId() != this.userService
				.getCurrentUser().getId()) {
			throw new BusinessException(BusinessError.THIS_IS_NOT_YOURS);
		}
	}
}
