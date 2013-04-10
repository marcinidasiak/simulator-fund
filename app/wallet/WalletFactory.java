package wallet;

import interfaces.IWalletSaving;

/**
 * Abstract class to create a client's wallet
 * 
 * @author Marcin Idasiak
 * 
 */
public abstract class WalletFactory {
	public static WalletFactory getFactory() {
		return new WalletXMLFactory();
	}

	public abstract IWalletSaving product();

}
