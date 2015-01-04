package de.th.wildau.recruiter.web;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.th.wildau.recruiter.ejb.model.Article;
import de.th.wildau.recruiter.ejb.service.ArticleService;

/**
 * Base home class for browse articles and navigation cases.
 * 
 * @author s7n
 *
 */
@Named
@RequestScoped
public class BrowseHome extends AbstractHome {

	private static final long serialVersionUID = 1823659925260976537L;

	@Inject
	private ArticleService articleService;

	/**
	 * Return o the list of articles, which are order by create date.
	 * 
	 * @return List of articles
	 */
	public List<Article> getArticles() {
		return this.articleService.findArticles();
	}

	public String show(final String value) {
		return redirect("/public/view.jsf?id=" + value);
	}
}
