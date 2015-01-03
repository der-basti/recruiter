package de.th.wildau.recruiter.ejb.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/**
 * Provides basic entity functionality.
 *
 * @param <T>
 * @author s7n
 */
@MappedSuperclass
public abstract class BaseEntity<T extends BaseEntity<?>> implements
		Serializable, Comparable<T> {

	private static final long serialVersionUID = 1460129319513800670L;

	protected static final String DB_PREFIX = "was_";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter(AccessLevel.NONE)
	private Long id;

	@Override
	public int compareTo(final T item) {
		return this.id.compareTo(item.getId());
	}

	public boolean isTransient() {
		return this.id == null;
	}

	/**
	 * Handle string (trim, clean and escape).
	 * 
	 * @param value
	 * @return String clean
	 */
	protected String clean(final String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		final String trim = value.trim();
		final String clean = Jsoup.clean(trim, Whitelist.none());
		final String escapeSeq = StringEscapeUtils.escapeEcmaScript(clean);
		return escapeSeq;
	}
}
