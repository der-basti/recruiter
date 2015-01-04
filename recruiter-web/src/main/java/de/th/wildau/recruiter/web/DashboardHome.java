package de.th.wildau.recruiter.web;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import de.th.wildau.recruiter.ejb.BusinessException;
import de.th.wildau.recruiter.ejb.model.Article;
import de.th.wildau.recruiter.ejb.model.Comment;
import de.th.wildau.recruiter.ejb.model.Price;
import de.th.wildau.recruiter.ejb.service.ArticleService;

@Named
@ViewScoped
public class DashboardHome extends AbstractHome {

	private static final long serialVersionUID = -6155008258787828330L;

	@Getter
	@Setter
	private Article article;

	@Getter
	private List<Article> articles;

	@Inject
	private ArticleService articleService;

	@Getter
	private List<Comment> comments;

	/**
	 * Price for a article. A user should have currently only one role.
	 */
	@Getter
	private Price price;

	public String deleteArticle(final Long id) {
		try {
			this.articleService.delete(id);
			addInfoMessage("msg.article.deleted");
			return redirect("/my");
		} catch (final BusinessException e) {
			addErrorMessage(e);
		}
		return "";
	}

	@PostConstruct
	public void init() {
		this.articles = this.articleService.findMyArticles();
		this.comments = this.articleService.findMyComments();
		this.price = this.articleService.findMyPrice();
	}

	public String show(final String value) {
		return redirect("/my/edit.jsf?id=" + value);
	}
}
