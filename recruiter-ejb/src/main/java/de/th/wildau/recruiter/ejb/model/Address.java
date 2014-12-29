package de.th.wildau.recruiter.ejb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Represent user address informations.
 *
 * @author s7n
 */
@Getter
@Setter
@Entity
@Table(name = BaseEntity.DB_PREFIX + "address")
public class Address extends BaseEntity<Address> {

	private static final long serialVersionUID = 8643372150637522106L;

	@NotBlank
	@Size(min = 2, max = 31)
	@Column(length = 31, nullable = false)
	private String city;

	@NotBlank
	@Size(min = 2, max = 63)
	@Column(length = 63, nullable = false)
	private String name;

	@Size(max = 31)
	@Column(length = 31)
	private String phone;

	@NotBlank
	@Size(min = 2, max = 31)
	@Column(length = 31, nullable = false)
	private String street;

	@NotBlank
	@Size(min = 1, max = 11)
	@Column(length = 11, nullable = false)
	private String streetNumber;

	@Size(max = 31)
	@Column(length = 31)
	private String title;

	@NotBlank
	@Size(min = 2, max = 11)
	@Pattern(regexp = "^([0-9]{5})*")
	@Column(length = 11, nullable = false)
	private String zipCode;

	public final void setCity(final String city) {
		this.city = clean(city);
	}

	public final void setName(final String name) {
		this.name = clean(name);
	}

	public final void setPhone(final String phone) {
		this.phone = phone;
	}

	public final void setStreet(final String street) {
		this.street = clean(street);
	}

	public final void setStreetNumber(final String streetNumber) {
		this.streetNumber = clean(streetNumber);
	}

	public final void setTitle(final String title) {
		this.title = clean(title);
	}

	public final void setZipCode(final String zipCode) {
		this.zipCode = clean(zipCode);
	}

}
