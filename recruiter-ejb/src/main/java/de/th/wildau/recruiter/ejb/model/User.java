package de.th.wildau.recruiter.ejb.model;

import java.util.ArrayList;
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

import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

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

	/**
	 * Required to activate a account.
	 */
	private String activationKey;

	@OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true, fetch = FetchType.LAZY)
	private Address address;

	@NotEmpty
	@Email
	@Size(max = 63)
	@Column(length = 63, nullable = false, unique = true)
	private String email;

	@NotBlank
	@Size(min = 6, max = 255)
	@Pattern(regexp = "((?=.*\\d)(?=.*[a-z]).{6,255})", message = "Das Passwort muss mindestens 6 Zeichen lang sein und Sonderzeichen, Ziffern und Buchstaben enthalten")
	// FIXME re - (?=.*[A-Z])(?=.*[-_:;,@#$%])
	@Column(nullable = false)
	private String password;

	@Column(length = 32, nullable = false, updatable = false)
	private String passwordSalt;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user", fetch = FetchType.LAZY)
	private List<Purchase> purchases;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = BaseEntity.DB_PREFIX + "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Set<Role> roles;

	/**
	 * Default 0.
	 */
	@Column(nullable = false, columnDefinition = "integer default 0")
	private Integer signinAttempts;

	public User() {
		// inizializer
		this.signinAttempts = 0;
		this.roles = new HashSet<>();
		this.address = new Address();
		this.purchases = new ArrayList<>();
	}

	public final void setEmail(final String email) {
		this.email = email.toLowerCase();
	}
}
