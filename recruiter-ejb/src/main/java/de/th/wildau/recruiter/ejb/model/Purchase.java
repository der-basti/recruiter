package de.th.wildau.recruiter.ejb.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;

import lombok.Getter;
import lombok.Setter;

/**
 * Represent user purchases.
 *
 * @author s7n
 */
@Getter
@Setter
@Entity
@Table(name = BaseEntity.DB_PREFIX + "purchase")
public class Purchase extends BaseEntity<Purchase> {

	private static final long serialVersionUID = 6366829386091762923L;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "purchase", fetch = FetchType.LAZY)
	private Set<Article> articles;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private PayBankCard payBc;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private PayCreditCard payCc;

	/**
	 * Default price is 1. (precision = 10, scale = 2)
	 */
	@Min(0)
	@Column(nullable = false, columnDefinition = "decimal(10,2) default 1")
	private BigDecimal price;

	@Column(updatable = false, columnDefinition = "timestamp default current_timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date purchaseDate;

	/**
	 * Default quantity is 1.
	 */
	@Min(1)
	@Column(nullable = false)
	private Integer quantity;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private User user;

	public Purchase() {
		// inizializer
		this.price = BigDecimal.ONE;
		this.quantity = 1;
		this.articles = new HashSet<>();
	}

}
