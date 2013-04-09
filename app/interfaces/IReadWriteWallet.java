package interfaces;

import model.Wallet;

/**
 * Interface dekalaruje read and write operations to the source where you want to store your wallet.
 * @author Marcin Idasiak
 *
 */
public interface IReadWriteWallet {
	public Wallet readWallet(String location) throws Exception ;
	public void saveWallet(String location, Wallet wallet) throws Exception ;
}
