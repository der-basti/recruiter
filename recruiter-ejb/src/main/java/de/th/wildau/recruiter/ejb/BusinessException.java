package de.th.wildau.recruiter.ejb;

import java.util.Arrays;
import java.util.Collection;

import lombok.Getter;

/**
 * Specific business application exception.
 * 
 * @author s7n
 *
 */
public class BusinessException extends Exception {

	private static final long serialVersionUID = 218234576192691666L;

	@Getter
	private Collection<BusinessError> errors;

	public BusinessException(final BusinessError... errorCodes) {
		super();
		this.errors = Arrays.asList(errorCodes);
	}
}
