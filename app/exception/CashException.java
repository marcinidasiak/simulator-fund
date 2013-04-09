package exception;

/**
 * Exception defining event associated with setting possible cash value below zero.
 * @author Marcin Idasiak
 *
 */
public class CashException extends ModelException {

	private static final long serialVersionUID = -7659629582057830998L;

	public CashException(String message) {
		super(message);
	}

	
}
