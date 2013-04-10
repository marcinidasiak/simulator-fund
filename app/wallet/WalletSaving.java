package wallet;

import static support.SUPPLIER.SERRIOUS_ERROR;
import interfaces.IReadWriteWallet;
import interfaces.ITransaction;
import interfaces.IWalletSaving;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

import play.libs.Json;
import support.SUPPLIER;

import model.FundInWallet;
import model.Wallet;
import enums.InvestmentFund;
import enums.UnitType;
import exception.ModelException;

/**
 * Wrapper class business model.
 * 
 * @author Marcin Idasiak
 * 
 */

public class WalletSaving implements IWalletSaving {

	private Wallet wallet;
	private ITransaction transaction;
	private IReadWriteWallet readwrite;

	/**
	 * 
	 * Default constructor with parameters
	 * 
	 * @param wallet
	 */
	public WalletSaving(ITransaction transaction, IReadWriteWallet readwrite) {
		super();
		this.wallet = new Wallet();
		this.transaction = transaction;
		this.readwrite = readwrite;
	}

	/**
	 * The valuation of wallet on the basis of the fund units.
	 * 
	 * @return monetary value
	 */
	public synchronized BigDecimal valuationWallet() {
		BigDecimal sum = BigDecimal.ZERO;
		for (FundInWallet fund : wallet.getFunds()) {
			sum = sum.add(new BigDecimal(fund.getNumberUnit())
					.multiply(InvestmentFund.valueOf(fund.getFund().toString())
							.getPrice()));
		}

		wallet.setTotalValueFunds(sum);
		return sum;
	}

	/**
	 * Buying of new units to the wallet
	 * 
	 * @param amount
	 * @param type
	 * @param fund
	 * @param price
	 * @return
	 */

	public String buy(BigInteger amount, UnitType type, InvestmentFund fund,
			BigDecimal price) {
		String message = null;
		boolean bool = false;
		try {
			bool = transaction.buy(amount, price, wallet, type, fund);
		} catch (ModelException e) {
			message = e.getMessage();
		} catch (RuntimeException e) {
			message = "!!! " + SERRIOUS_ERROR + " [" + e.getMessage() + "]";
		}
		if (bool) {
			message = SUPPLIER.SUCCESS;
		} else if (message == null) {
			message = SUPPLIER.PARAMETR_NUMBERUNIT_ERROR_BUY;
		}
		return message;
	}

	/**
	 * Sale of fund shares held in wallet
	 * 
	 * @param amount
	 * @param type
	 * @param fund
	 * @param price
	 * @return
	 */

	public String sell(BigInteger amount, UnitType type, InvestmentFund fund,
			BigDecimal price) {
		String message = null;
		boolean bool = false;
		try {
			bool = transaction.sell(amount, price, wallet, type, fund);
		} catch (ModelException e) {
			message = e.getMessage();
		} catch (RuntimeException e) {
			message = "!!! " + SERRIOUS_ERROR + " [" + e.getMessage() + "]";
		}
		if (bool) {
			message = SUPPLIER.SUCCESS;
		} else if (message == null) {
			message = SUPPLIER.PARAMETR_NUMBERUNIT_ERROR_SELL;
		}

		return message;
	}

	/**
	 * The readings wallet from the source
	 * 
	 * @param location
	 * @return
	 */

	public String read(String location) {
		String message = null;
		try {
			wallet = readwrite.readWallet(location);
		} catch (ModelException e) {
			message = e.getMessage();
		} catch (RuntimeException e) {
			message = "!!! " + SERRIOUS_ERROR + " [" + e.getMessage() + "]";
		} catch (Exception e) {
			message = "!!! " + SERRIOUS_ERROR + " [" + e.getMessage() + "]";
		}
		return message;
	}

	/**
	 * Save the wallet in the indicated location
	 * 
	 * @param location
	 * @return
	 */

	public String write(String location) {
		String message = null;
		try {
			readwrite.saveWallet(location, wallet);
		} catch (ModelException e) {
			message = e.getMessage();
		} catch (RuntimeException e) {
			message = "!!! " + SERRIOUS_ERROR + " [" + e.getMessage() + "]";
		} catch (Exception e) {
			message = "!!! " + SERRIOUS_ERROR + " [" + e.getMessage() + "]";
		}
		return message;
	}

	/**
	 * Create Json representation on the wallet.
	 */
	@Override
	public ObjectNode createJson() {
		ObjectNode event = Json.newObject();
		JsonNode w = Json.toJson(wallet);
		event.put("wallet", w);
		return event;
	}

}
