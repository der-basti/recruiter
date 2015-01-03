package de.th.wildau.recruiter.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.th.wildau.recruiter.ejb.PayType;
import de.th.wildau.recruiter.ejb.model.Article;
import de.th.wildau.recruiter.ejb.model.PayBankCard;
import de.th.wildau.recruiter.ejb.model.Purchase;
import de.th.wildau.recruiter.ejb.service.ArticleService;

/**
 * Base home class for shopping cart and navigation cases.
 * 
 * @author s7n
 *
 */
@Named
@SessionScoped
public class CartHome extends AbstractHome {

	public enum CartState {
		OPEN, PAY;
	}

	private static final Logger log = LoggerFactory.getLogger(CartHome.class);

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
			final PayBankCard bc = new PayBankCard();
			bc.setBic("BYLADEM1002");
			bc.setIban("DE821008000009600309576");
			this.articleService.createArticle(this.article.getTitle(),
					this.article.getContent(), bc, null);
		} catch (final Exception e) {
			log.error(e.getMessage());
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
