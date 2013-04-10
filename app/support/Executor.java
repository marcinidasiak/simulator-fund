package support;

import interfaces.IExecutor;
import interfaces.IWalletSaving;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.regex.Pattern;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

import play.libs.Json;
import enums.InvestmentFund;
import enums.Operation;
import enums.UnitType;

/**
 * Class is responsible for carrying out transactions with the outside world.
 * 
 * @author Marcin Idasiak
 * 
 */
public class Executor implements IExecutor {

	/**
	 * Main method available to carry out external transactions in the wallet.
	 */
	public ObjectNode transaction(JsonNode command, IWalletSaving wallet,
			BigDecimal price) {
		String message = null;
		String exmessage = null;
		try {
			exmessage = execute(command, wallet, price);
		} catch (Exception e) {
			message = e.getMessage();
		}
		ObjectNode result = Json.newObject();
		if (message == null) {
			if (SUPPLIER.SUCCESS.equals(exmessage)) {
				result.put("success", SUPPLIER.SUCCESS);
			} else {
				result.put("error", exmessage);
			}
		} else {
			result.put("error", SUPPLIER.ERROR + ": " + message);
		}
		return result;
	}

	/**
	 * the transaction.
	 * 
	 * @param command
	 *            eg {"code":"FA", "typefund":"A", "numbnerUnit":"1",
	 *            "type":"BUY"}
	 * @param wallet
	 *            Client wallet.
	 * @param price
	 *            Current price of a fund unit
	 * @return
	 * @throws Exception
	 */
	private String execute(JsonNode command, IWalletSaving wallet,
			BigDecimal price) throws Exception {
		Operation op = validOperation(command);
		if (Operation.BUY.equals(op)) {
			return wallet.buy(validAmount(command), validUniType(command),
					validFund(command), price);
		} else if (Operation.SELL.equals(op)) {
			return wallet.sell(validAmount(command), validUniType(command),
					validFund(command), price);
		} else {
			throw new Exception(SUPPLIER.PARAMETR_TYPE_ERROR);
		}
	}

	/**
	 * Validation of the fund code.
	 * 
	 * @param command
	 * @return
	 * @throws Exception
	 */
	private InvestmentFund validFund(JsonNode command) throws Exception {
		InvestmentFund invest = null;
		try {
			String code = command.findPath("code").getTextValue();
			invest = InvestmentFund.valueOf(code);
		} catch (IllegalArgumentException e) {
			throw new Exception(SUPPLIER.PARAMETR_FUND_ERROR);
		}
		return invest;
	}

	/**
	 * Validation of the amount unit
	 * 
	 * @param command
	 * @return
	 * @throws Exception
	 */
	private BigInteger validAmount(JsonNode command) throws Exception {
		String amount = command.findPath("numberUnit").getTextValue();
		if (Pattern.matches("\\d+", amount)) {
			return new BigInteger(amount);
		}
		throw new Exception(SUPPLIER.PARAMETR_AMOUNT_ERROR);
	}

	/**
	 * Validation of the unit type.
	 * 
	 * @param command
	 * @return
	 * @throws Exception
	 */
	private UnitType validUniType(JsonNode command) throws Exception {
		UnitType type = null;
		try {
			String typefund = command.findPath("typefund").getTextValue();
			type = UnitType.valueOf(typefund);
		} catch (IllegalArgumentException e) {
			throw new Exception(SUPPLIER.PARAMETR_TYPE_ERROR);
		}
		return type;
	}

	/**
	 * Validation of the operation (BUY or SELL).
	 * 
	 * @param command
	 * @return
	 * @throws Exception
	 */
	private Operation validOperation(JsonNode command) throws Exception {
		Operation type = null;
		try {
			String operation = command.findPath("type").getTextValue();
			type = Operation.valueOf(operation);
		} catch (IllegalArgumentException e) {
			throw new Exception(SUPPLIER.PARAMETR_TYPE_ERROR);
		}
		return type;
	}

	/**
	 * Decompilation of Json format enum defines the fund.
	 */
	@Override
	public InvestmentFund decode(JsonNode command) {
		InvestmentFund fund = null;
		try {
			fund = validFund(command);
		} catch (Exception e) {
		}
		return fund;
	}
}
