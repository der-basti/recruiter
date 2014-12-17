package de.th.wildau.recruiter.ejb.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Represent comments of a article.
 *
 * @author s7n
 */
@Entity
@Table(name = BaseEntity.DB_PREFIX + "comment")
@Data
@EqualsAndHashCode(callSuper = false)
public class Comment extends BaseEntity<Comment> {

	private static final long serialVersionUID = 3437227935356832630L;

	@Setter(AccessLevel.NONE)
	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@NotEmpty
	@Size(min = 1, max = 255)
	@Column(nullable = false)
	private String content;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "article_id", nullable = false)
	private Article article;

	@OneToOne(optional = true, fetch = FetchType.LAZY)
	private User user;

	public Comment() {
		// inizializer
		this.createDate = new Date();
	}

	public Comment(final Article article, final @NotBlank String content) {
		this();
		this.article = article;
		this.content = content.trim();
	}
}
