package de.th.wildau.recruiter.ejb.service.remote;

import java.math.BigDecimal;
import java.util.List;

import de.th.wildau.recruiter.ejb.BusinessException;
import de.th.wildau.recruiter.ejb.RoleName;
import de.th.wildau.recruiter.ejb.model.Article;
import de.th.wildau.recruiter.ejb.model.Comment;
import de.th.wildau.recruiter.ejb.model.PayBankCard;
import de.th.wildau.recruiter.ejb.model.PayCreditCard;
import de.th.wildau.recruiter.ejb.model.Price;
import de.th.wildau.recruiter.ejb.model.Purchase;

public interface ArticleServiceRemote {

	public void createArticle(final String title, final String content,
			final PayBankCard payBc, final PayCreditCard payCc)
			throws BusinessException;

	/**
	 * Create a comment for a article.
	 * 
	 * @param articleId
	 * @param content
	 * @throws BusinessException
	 */
	public void createComment(final Long articleId, final String content)
			throws BusinessException;

	/**
	 * Delete a article from current user.
	 * 
	 * @param articleId
	 * @throws BusinessException
	 */
	public void delete(final Long articleId) throws BusinessException;

	/**
	 * Get a specific article.
	 * 
	 * @param id
	 * @return Article or null
	 */
	public Article findArticle(final Long id, final String... fetch);

	/**
	 * Find all articles order by create date.
	 * 
	 * @return List of articles
	 */
	public List<Article> findArticles();

	public List<Article> findMyArticles();

	/**
	 * Find all comments from the current user.
	 * 
	 * @return
	 */
	public List<Comment> findMyComments();

	/**
	 * Get current price for all roles from the current user (a user should have
	 * only one role).
	 * 
	 * @return
	 */
	public Price findMyPrice();

	/**
	 * Find all purchases from the current user.
	 * 
	 * @return
	 */
	public List<Purchase> findMyPurchases();

	/**
	 * Get the price for the current user.
	 * 
	 * @return BigDecimal
	 */
	public BigDecimal getPrice();

	/**
	 * Get the price for specific role.
	 * 
	 * @return BigDecimal
	 */
	public BigDecimal getPrice(final RoleName role);

	/**
	 * Update a article from me.
	 * 
	 * @param article
	 * @throws BusinessException
	 */
	public Article update(final Article article) throws BusinessException;

}
