package de.th.wildau.recruiter.ejb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
@Entity
@Table(name = BaseEntity.DB_PREFIX + "payCreditCard")
public class PayCreditCard extends BaseEntity<PayCreditCard> {

	private static final long serialVersionUID = -2260660952348935786L;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(length = 31, nullable = false, updatable = false)
	private PayCreditCardType cardType;

	// exparation
	@NotEmpty
	@Pattern(regexp = "[0-9]{1,2}")
	@Column(length = 2, nullable = false, updatable = false)
	private String exMonth;

	// exparation
	@NotEmpty
	@Pattern(regexp = "[0-9]{4}")
	@Column(length = 4, nullable = false, updatable = false)
	private String exYear;

	@NotBlank
	@Length(min = 3, max = 255)
	@Column(nullable = false, updatable = false)
	private String name;

	@NotBlank
	@Length(min = 1, max = 30)
	// @CreditCardNumber(message = "Ungültige Kreditkartennummer.")
	@Pattern(regexp = "[0-9]{13,16}", message = "Die Nummer muss 13-16 Zeichen lang sein.")
	@Column(length = 31, nullable = false, updatable = false)
	private String number;

	@NotBlank
	@Length(min = 3, max = 4)
	@Pattern(regexp = "[0-9]{3,4}", message = "Die Nummer muss 3-4 Zeichen lang sein.")
	@Column(length = 4, nullable = false, updatable = false)
	private String numberCheck;

	public final void setName(final String name) {
		this.name = clean(name);
	}

}
