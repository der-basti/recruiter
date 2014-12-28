package de.th.wildau.recruiter.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

import lombok.Getter;
import de.th.wildau.recruiter.ejb.model.Purchase;

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

	private static final long serialVersionUID = 4745735016310100432L;

	@Getter
	private List<Purchase> purchases;

	public void checkout() {

	}

	@PostConstruct
	public void init() {
		this.purchases = new ArrayList<>();
	}
}
