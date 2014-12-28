package de.th.wildau.recruiter.ejb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Represent user address informations.
 *
 * @author s7n
 */
@Entity
@Table(name = BaseEntity.DB_PREFIX + "address")
@Data
@EqualsAndHashCode(callSuper = false)
public class Address extends BaseEntity<Address> {

	private static final long serialVersionUID = 8643372150637522106L;

	@Setter(value = AccessLevel.NONE)
	@Size(max = 31)
	@Column(length = 31)
	private String title;

	@Setter(value = AccessLevel.NONE)
	@NotBlank
	@Size(min = 2, max = 63)
	@Column(length = 63, nullable = false)
	private String name;

	@Setter(value = AccessLevel.NONE)
	@NotBlank
	@Size(min = 2, max = 31)
	@Column(length = 31, nullable = false)
	private String street;

	@Setter(value = AccessLevel.NONE)
	@NotBlank
	@Size(min = 1, max = 11)
	@Column(length = 11, nullable = false)
	private String streetNumber;

	@Setter(value = AccessLevel.NONE)
	@NotBlank
	@Size(min = 2, max = 11)
	@Pattern(regexp = "^([0-9]{5})*")
	@Column(length = 11, nullable = false)
	private String zipCode;

	@Setter(value = AccessLevel.NONE)
	@NotBlank
	@Size(min = 2, max = 31)
	@Column(length = 31, nullable = false)
	private String city;

	public final void setTitle(final String title) {
		this.title = clean(title);
	}

	public final void setName(final String name) {
		this.name = clean(name);
	}

	public final void setStreet(final String street) {
		this.street = clean(street);
	}

	public final void setStreetNumber(final String streetNumber) {
		this.streetNumber = clean(streetNumber);
	}

	public final void setZipCode(final String zipCode) {
		this.zipCode = clean(zipCode);
	}

	public final void setCity(final String city) {
		this.city = clean(city);
	}

}
