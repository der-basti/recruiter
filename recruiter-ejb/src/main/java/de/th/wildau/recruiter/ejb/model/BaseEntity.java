package de.th.wildau.recruiter.ejb.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

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
public abstract class BaseEntity<T extends BaseEntity<?>> implements Serializable,
        Comparable<T> {

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

}
