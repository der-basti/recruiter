package de.th.wildau.recruiter.ejb.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

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

	public boolean isTransient() {
		return this.id == null;
	}

	@Override
	public int compareTo(T item) {
		return this.id.compareTo(item.getId());
	}

	/**
	 * Handle string (trim, clean and escape).
	 * 
	 * @param s
	 * @return String clean
	 */
	protected String clean(final String s) {
		if (StringUtils.isEmpty(s))
			return null;
		final String trim = s.trim();
		final String clean = Jsoup.clean(trim, Whitelist.none());
		final String escapeSeq = StringEscapeUtils.escapeEcmaScript(clean);
		return escapeSeq;
	}
}
