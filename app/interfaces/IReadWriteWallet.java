package interfaces;

import model.Wallet;

public interface IReadWriteWallet {
	public Wallet readWallet(String location) throws Exception ;
	public void saveWallet(String location, Wallet wallet) throws Exception ;
}
