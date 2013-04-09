package exception;

/**
 * Exception defining event-related setting, or download a cmodel null
 * @author Marcin Idasiak
 *
 */
public class ModelException extends RuntimeException {
	
	private static final long serialVersionUID = 4987568065543792039L;

	public ModelException(String message) {
		super(message);
	}
}
