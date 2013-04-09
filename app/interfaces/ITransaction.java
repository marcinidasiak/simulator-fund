package interfaces;

import java.math.BigDecimal;
import java.math.BigInteger;

import model.Wallet;
import enums.InvestmentFund;
import enums.UnitType;

public interface ITransaction {
	
	public boolean sell(BigInteger amount, BigDecimal price, Wallet wallet, UnitType type, InvestmentFund fund);
	public boolean buy(BigInteger amount, BigDecimal price, Wallet wallet, UnitType type, InvestmentFund fund);
}
