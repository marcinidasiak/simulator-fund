package exception;

public class PriceException extends RuntimeException {

	/**
	 *  Exception indicating irregularities related to the price of the funds.
	 */
	private static final long serialVersionUID = -6603761543928006724L;

	public PriceException(String message) {
		super(message);
	}

	
}
