package de.th.wildau.recruiter.ejb.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Represent the main content (job offer, curriculum vitae) of the application.
 *
 * @author s7n
 */
@Entity
@Table(name = BaseEntity.DB_PREFIX + "article")
@Data
@EqualsAndHashCode(callSuper = false)
public class Article extends BaseEntity<Article> {

	private static final long serialVersionUID = 9221964675991419657L;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Purchase purchase;

	@Setter(AccessLevel.NONE)
	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@NotBlank
	@Size(min = 2, max = 255)
	@Column(nullable = false)
	private String title;

	@NotBlank
	@Size(min = 2, max = 1024)
	@Column(length = 1024, nullable = false)
	private String content;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "article", orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Comment> comments;

	public Article() {
		// inizializer
		this.purchase = new Purchase();
		this.createDate = new Date();
		this.comments = new ArrayList<>();
	}
}
