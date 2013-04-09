package exception;

/**
 * Exception defining event-related setting, or download a price null
 * @author Marcin Idasiak
 *
 */
public class PriceException extends RuntimeException {

	private static final long serialVersionUID = -6603761543928006724L;

	public PriceException(String message) {
		super(message);
	}

	
}
