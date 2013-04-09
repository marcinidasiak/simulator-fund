package exception;

public class CashException extends ModelException {


	/**
	 *  Exception indicating irregularities related to the number of unit of the funds.
	 */
	private static final long serialVersionUID = -7659629582057830998L;

	public CashException(String message) {
		super(message);
	}

	
}
