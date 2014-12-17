package de.th.wildau.recruiter.ejb;

/**
 * Specific business application exception.
 * 
 * @author s7n
 *
 */
public class BusinessException extends Throwable {

	private static final long serialVersionUID = 218234576192691666L;

	public BusinessException(final String message) {
		super(message);
	}
}
