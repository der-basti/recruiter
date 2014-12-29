package de.th.wildau.recruiter.ejb.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import de.th.wildau.recruiter.ejb.RoleName;

/**
 * Represent application prices.
 *
 * @author s7n
 */
@Getter
@Setter
@Entity
@Table(name = BaseEntity.DB_PREFIX + "price")
public class Price extends BaseEntity<Price> {

	private static final long serialVersionUID = 4200697069607103301L;

	@NotNull
	@Min(0)
	@Column(nullable = false, columnDefinition = "decimal(10,2) default 1")
	private BigDecimal price;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(length = 31, nullable = false, unique = true)
	private RoleName roleName;

	public Price() {
		// inizializer
		this.price = BigDecimal.ONE;
	}

	public Price(final RoleName roleName) {
		this();
		this.roleName = roleName;
	}
}
