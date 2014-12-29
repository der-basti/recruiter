package de.th.wildau.recruiter.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;
import de.th.wildau.recruiter.ejb.PayType;
import de.th.wildau.recruiter.ejb.model.Article;
import de.th.wildau.recruiter.ejb.model.Purchase;
import de.th.wildau.recruiter.ejb.service.ArticleService;

/**
 * Base home class for shopping cart and navigation cases.
 * 
 * @author s7n
 *
 */
@ManagedBean
@SessionScoped
public class CartHome extends AbstractHome {

	public enum CartState {
		OPEN, PAY;
	}

	private static final long serialVersionUID = 4745735016310100432L;

	@Getter
	@Setter
	private Article article;

	@Inject
	private ArticleService articleService;

	@Getter
	@Setter
	private PayType payType;

	@Getter
	@Setter
	private List<SelectItem> payTypes;

	@Getter
	private List<Purchase> purchases;

	public String buy() {
		try {
			this.articleService.createArticle(this.article.getTitle(),
					this.article.getContent());
		} catch (final Exception e) {
			// XXX log
		}
		return redirect("/public/browse.jsf");
	}

	@PostConstruct
	public void init() {
		this.article = new Article();
		this.payTypes = new ArrayList<>();
		for (final PayType item : PayType.values()) {
			this.payTypes.add(new SelectItem(item, getMessage(item)));
		}

		this.purchases = new ArrayList<>();

	}

	public void reset() {
		this.article = new Article();
		this.payType = null;
	}
}
