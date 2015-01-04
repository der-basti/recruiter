package de.th.wildau.recruiter.web;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;

import de.th.wildau.recruiter.ejb.BusinessError;
import de.th.wildau.recruiter.ejb.BusinessException;
import de.th.wildau.recruiter.ejb.model.Article;
import de.th.wildau.recruiter.ejb.model.Comment;
import de.th.wildau.recruiter.ejb.service.ArticleService;

/**
 * Controller to view articles.
 * 
 * @author s7n
 *
 */
@Named
@ViewScoped
public class ViewHome extends AbstractHome {

	private static final long serialVersionUID = -6155008258787828330L;

	@Getter
	private Article article;

	@Inject
	private ArticleService articleService;

	@Getter
	@Setter
	private Comment comment;

	public String commentCharsRemaining() {
		final int length = 250;
		if (StringUtils.isNotEmpty(this.comment.getContent())) {
			return String.valueOf(length - this.comment.getContent().length());
		}
		return String.valueOf(length);
	}

	public String createComment() {
		if (StringUtils.isBlank(this.comment.getContent())) {
			addErrorMessage(new BusinessException(BusinessError.NOT_EMPTY));
			redirect("/public/view.jsf?id=" + this.article.getId());
		}
		try {
			this.articleService.createComment(this.article.getId(),
					this.comment.getContent());
			addInfoMessage("msg.comment.created");
		} catch (final BusinessException e) {
			addErrorMessage(e);
		} catch (final NumberFormatException e) {
			addErrorMessage(new BusinessException(BusinessError.INVALID_VIEW_ID));
		}
		return redirect("/public/view.jsf?id=" + this.article.getId());
	}

	public boolean hasComments() {
		if (this.article != null) {
			return this.article.getComments().size() == 0;
		}
		return false;
	}

	@PostConstruct
	public void init() {
		try {
			this.comment = new Comment();
			this.article = this.articleService.findArticle(
					Long.valueOf(getParam("id")), "comments");
		} catch (final NumberFormatException e) {
			addErrorMessage(new BusinessException(BusinessError.INVALID_VIEW_ID));
		}
	}

}
