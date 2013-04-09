package exception;

public class CurrencyException extends ModelException {


	/**
	 *  Exception indicating irregularities related to the number of unit of the funds.
	 */
	private static final long serialVersionUID = -7659629582057830998L;

	public CurrencyException(String message) {
		super(message);
	}

	
}
