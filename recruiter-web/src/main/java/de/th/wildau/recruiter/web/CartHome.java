package de.th.wildau.recruiter.web;

import java.util.ArrayList;
import java.util.Calendar;
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

import de.th.wildau.recruiter.ejb.BusinessError;
import de.th.wildau.recruiter.ejb.BusinessException;
import de.th.wildau.recruiter.ejb.PayCreditCardType;
import de.th.wildau.recruiter.ejb.PayType;
import de.th.wildau.recruiter.ejb.model.Article;
import de.th.wildau.recruiter.ejb.model.PayBankCard;
import de.th.wildau.recruiter.ejb.model.PayCreditCard;
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
	private PayCreditCardType ccType;

	@Getter
	@Setter
	private List<SelectItem> ccTypes;

	@Getter
	@Setter
	private List<SelectItem> exMonths;

	@Getter
	@Setter
	private List<SelectItem> exYears;

	@Getter
	@Setter
	private PayBankCard payBc;

	@Getter
	@Setter
	private PayCreditCard payCc;

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
			PayBankCard bc = null;
			PayCreditCard cc = null;
			switch (this.payType) {
			case BC:
				bc = this.payBc;
				break;
			case CC:
				cc = this.payCc;
				break;
			default:
				addErrorMessage(new BusinessException(
						BusinessError.INVALID_PAY_INFO));
				return "";
			}
			this.articleService.createArticle(this.article.getTitle(),
					this.article.getContent(), bc, cc);
			return redirect("/my/index.jsf");
		} catch (final BusinessException e) {
			addErrorMessage(e);
		} catch (final Exception e) {
			log.error(e.getMessage());
		}
		return "";
	}

	@PostConstruct
	public void init() {
		this.article = new Article();
		this.payBc = new PayBankCard();
		this.payCc = new PayCreditCard();
		this.payCc.setExMonth(String.valueOf(getCurrentMonth()));
		this.payCc.setExYear(String.valueOf(getCurrentYear()));
		this.payType = PayType.BC;
		this.payTypes = new ArrayList<>();
		for (final PayType item : PayType.values()) {
			this.payTypes.add(new SelectItem(item, getMessage(item)));
		}

		this.ccTypes = new ArrayList<>();
		for (final PayCreditCardType item : PayCreditCardType.values()) {
			this.ccTypes.add(new SelectItem(item, getMessage(item)));
		}
		this.exMonths = new ArrayList<SelectItem>();
		for (int i = 1; i <= 12; i++) {
			this.exMonths.add(new SelectItem(i, String.valueOf(i)));
		}
		this.exYears = new ArrayList<SelectItem>();
		for (int i = getCurrentYear(); i <= getCurrentYear() + 10; i++) {
			this.exYears.add(new SelectItem(i, String.valueOf(i)));
		}
		this.purchases = new ArrayList<>();
	}

	public void reset() {
		this.article = new Article();
		this.payType = null;
	}

	public boolean showBc() {
		return this.payType == PayType.BC;
	}

	private int getCurrentDate(final int calendarId) {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	private int getCurrentMonth() {
		return getCurrentDate(Calendar.MONTH);
	}

	private int getCurrentYear() {
		return getCurrentDate(Calendar.YEAR);
	}
}
