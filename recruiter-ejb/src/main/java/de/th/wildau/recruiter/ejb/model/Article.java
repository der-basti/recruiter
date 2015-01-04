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

import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Represent the main content (job offer, curriculum vitae) of the application.
 *
 * @author s7n
 */
@Getter
@Setter
@Entity
@Table(name = BaseEntity.DB_PREFIX + "article")
public class Article extends BaseEntity<Article> {

	private static final long serialVersionUID = 9221964675991419657L;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "article", orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Comment> comments;

	@NotBlank
	@Size(min = 2, max = 1024)
	@Column(length = 1024, nullable = false)
	private String content;

	@Column(updatable = false, columnDefinition = "timestamp default current_timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@ManyToOne(cascade = CascadeType.MERGE, optional = false, fetch = FetchType.LAZY)
	private Purchase purchase;

	@NotBlank
	@Size(min = 2, max = 255)
	@Column(nullable = false)
	private String title;

	public Article() {
		// inizializer
		this.purchase = new Purchase();
		this.comments = new ArrayList<>();
	}

	public final void setContent(final String content) {
		this.content = clean(content);
	}

	public final void setTitle(final String title) {
		this.title = clean(title);
	}
}
