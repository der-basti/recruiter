package de.th.wildau.recruiter.web;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;
import de.th.wildau.recruiter.ejb.BusinessError;
import de.th.wildau.recruiter.ejb.BusinessException;
import de.th.wildau.recruiter.ejb.model.Article;
import de.th.wildau.recruiter.ejb.service.ArticleService;

/**
 * Controller to view articles.
 * 
 * @author s7n
 *
 */
@ManagedBean
@ViewScoped
public class ViewHome extends AbstractHome {

	private static final long serialVersionUID = -6155008258787828330L;

	@Getter
	private Article article;

	@Inject
	private ArticleService articleService;

	@Getter
	@Setter
	private String content;

	public String createComment() {
		try {
			this.articleService.createComment(this.article.getId(),
					this.content);
			addInfoMessage("msg.comment.created");
		} catch (final BusinessException e) {
			addErrorMessage(e);
		} catch (final NumberFormatException e) {
			addErrorMessage(new BusinessException(BusinessError.INVALID_VIEW_ID));
		}
		return redirect("/public/view.jsf?id=" + this.article.getId());
	}

	@PostConstruct
	public void init() {
		try {
			this.article = this.articleService.findArticle(
					Long.valueOf(getParam("id")), "comments");
		} catch (final NumberFormatException e) {
			addErrorMessage(new BusinessException(BusinessError.INVALID_VIEW_ID));
		}
	}

}
