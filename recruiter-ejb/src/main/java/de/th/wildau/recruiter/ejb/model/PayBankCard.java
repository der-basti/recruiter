package de.th.wildau.recruiter.ejb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Represent a credit card.
 * 
 * @author s7n
 *
 */
@Getter
@Setter
@Entity
@Table(name = BaseEntity.DB_PREFIX + "payBankCard")
public class PayBankCard extends BaseEntity<PayBankCard> {

	private static final long serialVersionUID = 2791988698704556872L;

	@NotBlank
	@Pattern(regexp = "([a-zA-Z]{4}[a-zA-Z]{2}[a-zA-Z0-9]{2}([a-zA-Z0-9]{3})?)", message = "Dies ist keine gültige BIC.")
	@Column(length = 11, nullable = false, updatable = false)
	private String bic;

	@NotBlank
	@Pattern(regexp = "[a-zA-Z]{2}[0-9]{2}[a-zA-Z0-9]{4}[0-9]{7}([a-zA-Z0-9]?){0,16}", message = "Dies ist keine gültige IBAN")
	@Column(length = 31, nullable = false, updatable = false)
	private String iban;

}
