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

import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Represent comments of a article.
 *
 * @author s7n
 */
@Getter
@Setter
@Entity
@Table(name = BaseEntity.DB_PREFIX + "comment")
public class Comment extends BaseEntity<Comment> {

	private static final long serialVersionUID = 3437227935356832630L;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "article_id", nullable = false)
	private Article article;

	@NotEmpty
	@Size(min = 1, max = 250)
	@Column(nullable = false)
	private String content;

	@Column(updatable = false, columnDefinition = "timestamp default current_timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@OneToOne(optional = true, fetch = FetchType.EAGER)
	private User user;

	public final void setContent(final String content) {
		this.content = clean(content);
	}
}
