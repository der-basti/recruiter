package de.th.wildau.recruiter.ejb.model;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

/**
 * Represent a user of the application.
 *
 * @author s7n
 */
@Getter
@Setter
@Entity
@Table(name = BaseEntity.DB_PREFIX + "user")
public class User extends BaseEntity<User> {

	private static final long serialVersionUID = 8490082221406415512L;

	@NotEmpty
	@Email
	@Size(max = 63)
	@Column(length = 63, nullable = false, unique = true)
	private String email;

	/**
	 * Requirements: minimum 8 chars in total, at least two letters, at least
	 * two digits or symbols
	 * 
	 */
	@Getter(AccessLevel.NONE)
	@NotBlank
	@Size(min = 8, max = 255)
	@Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,255})")
	@Column(nullable = false)
	private String password;

	/**
	 * Automatically generated.
	 */
	// @Getter(AccessLevel.MODULE)
	@Setter(AccessLevel.NONE)
	@Column(length = 32, nullable = false, updatable = false)
	private String passwordSalt;

	/**
	 * Required to activate a account.
	 */
	// @Getter(AccessLevel.MODULE)
	@Setter(AccessLevel.NONE)
	private String activationKey;

	/**
	 * Default 0.
	 */
	@Column(nullable = false, columnDefinition = "integer default 0")
	private Integer signinAttempts;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = BaseEntity.DB_PREFIX + "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Set<Role> roles;

	@OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true, fetch = FetchType.LAZY)
	private Address address;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user", fetch = FetchType.LAZY)
	private List<Purchase> purchases;

	public User() {
		// inizializer
		this.passwordSalt = RandomStringUtils.randomAlphanumeric(32);
		this.signinAttempts = 0;
		this.roles = new HashSet<>();
		this.address = new Address();
		this.purchases = new ArrayList<>();
	}

	/**
	 * Generate automatically salted password and the activation key.
	 *
	 * @param email
	 * @param password
	 */
	public User(final @Email String email, final String password) {
		this();
		this.email = email.toLowerCase().trim();
		this.password = password + this.passwordSalt;
		this.activationKey = Base64.getEncoder().encodeToString(
				Hashing.sha512()
						.hashString(this.email + this.passwordSalt,
								Charsets.UTF_8).asBytes());
	}
}
