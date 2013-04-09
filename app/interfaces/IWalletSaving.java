package interfaces;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.codehaus.jackson.node.ObjectNode;

import enums.InvestmentFund;
import enums.UnitType;

/**
 * The interface defines the operations of wallet.
 * @author Marcin Idasiak
 *
 */
public interface IWalletSaving {
	public BigDecimal valuationWallet();
	public  String buy(BigInteger amount, UnitType type, InvestmentFund fund,
			BigDecimal price);
	public String sell(BigInteger amount, UnitType type, InvestmentFund fund,
			BigDecimal price);
	public String read(String location);
	public String write(String location);
	public ObjectNode createJson();
}
