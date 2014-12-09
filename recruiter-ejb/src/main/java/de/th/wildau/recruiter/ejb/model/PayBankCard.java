package de.th.wildau.recruiter.ejb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = BaseEntity.DB_PREFIX + "paycc")
@Data
@EqualsAndHashCode(callSuper = false)
public class PayBankCard extends PayAbstract {

	private static final long serialVersionUID = 2791988698704556872L;

	@NotBlank
	@Pattern(regexp = "[a-zA-Z]{2}[0-9]{2}[a-zA-Z0-9]{4}[0-9]{7}([a-zA-Z0-9]?){0,16}")
	@Column(length = 31, nullable = false, updatable = false)
	private String iban;

	@NotBlank
	@Pattern(regexp = "'([a-zA-Z]{4}[a-zA-Z]{2}[a-zA-Z0-9]{2}([a-zA-Z0-9]{3})?)")
	@Column(length = 11, nullable = false, updatable = false)
	private String bic;

}
