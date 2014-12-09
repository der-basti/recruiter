package de.th.wildau.recruiter.ejb.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Represent user purchases.
 *
 * @author s7n
 */
@Entity
@Table(name = BaseEntity.DB_PREFIX + "purchase")
@Data
@EqualsAndHashCode(callSuper = false)
public class Purchase extends BaseEntity<Purchase> {

	private static final long serialVersionUID = 6366829386091762923L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;

	@Setter(AccessLevel.NONE)
	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date purchaseDate;

	// FIXME payment
	private PayAbstract pay;
	
	/**
	 * Default price is 1.
	 */
	@NotEmpty
	@Min(0)
	@Column(nullable = false, precision = 10, scale = 2)
	// FIXME columnDefinition = "Decimal(10,2) default '1.00'"
	private BigDecimal price;

	/**
	 * Default quantity is 1.
	 */
	@NotEmpty
	@Min(1)
	@Column(nullable = false)
	private Integer quantity;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "purchase")
	private Set<Article> articles;

	public Purchase() {
		// inizializer
		this.purchaseDate = new Date();
		this.price = BigDecimal.ONE;
		this.quantity = 1;
		this.articles = new HashSet<>();
	}

	public Purchase(final User user) {
		this();
		this.user = user;
	}

}
