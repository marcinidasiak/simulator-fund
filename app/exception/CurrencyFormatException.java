package exception;

/**
 * Exception defining event-related setting are opowiedniej name of the currency (from 2 to 3 marks consisting exclusively of AZ eg PLN).
 * @author Marcin Idasiak
 *
 */
public class CurrencyFormatException extends ModelException {

	private static final long serialVersionUID = -7659629582057830998L;

	public CurrencyFormatException(String message) {
		super(message);
	}

	
}
