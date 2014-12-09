package de.th.wildau.recruiter.ejb.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.validator.constraints.NotBlank;

import de.th.wildau.recruiter.ejb.RoleName;

/**
 * Represent possible user roles.
 *
 * @author s7n
 */
@Entity
@Table(name = BaseEntity.DB_PREFIX + "role")
@Data
@EqualsAndHashCode(callSuper = false)
public class Role extends BaseEntity<Role> {

	private static final long serialVersionUID = 9187033441602277971L;

	@Enumerated(EnumType.STRING)
	@NotBlank
	@Size(min = 1, max = 63)
	@Column(unique = true, nullable = false, length = 63)
	private RoleName name;

	@ManyToMany(mappedBy = "roles")
	private Set<User> users;

	public Role() {
		// inizializer
		this.users = new HashSet<>();
	}

	public Role(final RoleName name) {
		this();
		this.name = name;

	}
}
