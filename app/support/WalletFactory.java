package support;

import exception.LocationException;
import transactions.Transaction;
import interfaces.IReadWriteWallet;
import interfaces.ITransaction;
import interfaces.IWalletSaving;
import io.ReadWriteWallet;

public class WalletFactory {
	
	private static ITransaction transaction = new Transaction();
	private static IReadWriteWallet readwrite = new ReadWriteWallet();
	
	public static IWalletSaving product(){
		return new WalletSaving(transaction, readwrite);
	}
}
