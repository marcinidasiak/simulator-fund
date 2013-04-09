package transactions;

import interfaces.ITransaction;

import java.math.BigDecimal;
import java.math.BigInteger;

import model.FundInWallet;
import model.Wallet;
import enums.InvestmentFund;
import enums.UnitType;

/**
 * Class carrying out operations on the sale or purchase of the wallet
 * 
 * @author Marcin Idasiak
 * 
 */
public class Transaction implements ITransaction {

	/**
	 * Method for carrying out sales operations
	 */
	@Override
	public boolean sell(BigInteger amount, BigDecimal price, Wallet wallet,
			UnitType type, InvestmentFund fund) {
		Wallet wall = wallet;
		FundInWallet changeFund = null;
		for (FundInWallet f : wall.getFunds()) {
			if (f.getFund().equals(fund) && f.getType().equals(type)) {
				changeFund = f;
				break;
			}
		}

		if (changeFund != null
				&& (changeFund.getNumberUnit().compareTo(amount) >= 0)) {
			BigDecimal cost = price.multiply(new BigDecimal(amount));

			if (UnitType.B.equals(type)) {
				BigDecimal tax = cost.multiply(new BigDecimal(UnitType.B
						.getTax()));
				cost = cost.subtract(tax);
			}
			cost = cost.setScale(2, BigDecimal.ROUND_UP);
			wall.setCash(cost.add(wallet.getCash()));
			changeFund.setNumberUnit(changeFund.getNumberUnit()
					.subtract(amount));
			return true;
		}
		return false;
	}

	/**
	 * Method for the operation of purchase.
	 */
	@Override
	public boolean buy(BigInteger amount, BigDecimal price, Wallet wallet,
			UnitType type, InvestmentFund fund) {
		BigDecimal cost = price.multiply(new BigDecimal(amount));
		Wallet wall = wallet;
		if (UnitType.A.equals(type)) {
			BigDecimal tax = cost.multiply(new BigDecimal(UnitType.A.getTax()));
			cost = cost.add(tax);
		}

		if (wallet.getCash().compareTo(cost) >= 0) {
			wall.setCash(wall.getCash().subtract(cost));
			FundInWallet changeFund = null;
			for (FundInWallet f : wall.getFunds()) {
				if (f.getFund().equals(fund) && f.getType().equals(type)) {
					changeFund = f;
					break;
				}
			}
			if (changeFund == null) {
				wall.getFunds().add(new FundInWallet(amount, type, fund));
			} else {
				changeFund
						.setNumberUnit(changeFund.getNumberUnit().add(amount));
			}
			return true;
		}
		return false;
	}

}
