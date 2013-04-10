package wallet;

import interfaces.IReadWriteWallet;
import interfaces.ITransaction;
import interfaces.IWalletSaving;
import io.ReadWriteWallet;
import transactions.Transaction;

/**
 * Factory creates wallet of zapisame to XML, and any transaction.
 * @author Marcin Idasiak
 *
 */
public class WalletXMLFactory extends WalletFactory {
	
	private static ITransaction transaction = new Transaction();
	private static IReadWriteWallet readwrite = new ReadWriteWallet();
	
	public IWalletSaving product(){
		return new WalletSaving(transaction, readwrite);
	}
}
