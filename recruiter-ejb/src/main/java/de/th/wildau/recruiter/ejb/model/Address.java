package de.th.wildau.recruiter.ejb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

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

    @Size(max = 31)
    @Column(length = 31)
    private String title;

    @NotBlank
    @Size(min = 2, max = 63)
    @Column(length = 63, nullable = false)
    private String name;

    @NotBlank
    @Size(min = 2, max = 31)
    @Column(length = 31, nullable = false)
    private String street;

    @NotBlank
    @Size(min = 1, max = 11)
    @Column(length = 11, nullable = false)
    private String streetNumber;

    @NotBlank
    @Size(min = 2, max = 11)
    @Pattern(regexp = "^([0-9]{5})*")
    @Column(length = 11, nullable = false)
    private String zipCode;

    @NotBlank
    @Size(min = 2, max = 31)
    @Column(length = 31, nullable = false)
    private String city;

}
