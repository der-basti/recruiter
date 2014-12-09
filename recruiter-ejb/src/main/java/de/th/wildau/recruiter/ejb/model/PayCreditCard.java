package de.th.wildau.recruiter.ejb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import de.th.wildau.recruiter.ejb.PayCreditCardType;

/**
 * Represent a bank card.
 * 
 * @author s7n
 *
 */
@Entity
@Table(name = BaseEntity.DB_PREFIX + "payCreditCard")
@Data
@EqualsAndHashCode(callSuper = false)
public class PayCreditCard extends PayAbstract {

	private static final long serialVersionUID = -2260660952348935786L;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(length = 31, nullable = false, updatable = false)
	private PayCreditCardType cardType;

	@NotBlank
	@Length(min = 1, max = 30)
	@Pattern(regexp = "[0-9]{13,16}")
	@Column(length = 31, nullable = false, updatable = false)
	private String number;

	@NotBlank
	@Length(min = 3, max = 255)
	@Column(nullable = false, updatable = false)
	private String name;

	// exparation
	@NotEmpty
	@Pattern(regexp = "[0-9]{2}")
	@Column(length = 2, nullable = false, updatable = false)
	private String exMonth;

	// exparation
	@NotEmpty
	@Pattern(regexp = "[0-9]{4}")
	@Column(length = 4, nullable = false, updatable = false)
	private String exYear;

}
