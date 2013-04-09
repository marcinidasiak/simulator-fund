package exception;

public class CurrencyFormatException extends ModelException {


	/**
	 *  Exception indicating irregularities related to the number of unit of the funds.
	 */
	private static final long serialVersionUID = -7659629582057830998L;

	public CurrencyFormatException(String message) {
		super(message);
	}

	
}
