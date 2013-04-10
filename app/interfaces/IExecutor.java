package interfaces;

import java.math.BigDecimal;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

import enums.InvestmentFund;

/**
 * Interface declares operations necessary to carry out transactions with
 * @author Marcin Idasiak
 *
 */
public interface IExecutor {
	public ObjectNode transaction(JsonNode command, IWalletSaving wallet, BigDecimal price);
	public InvestmentFund decode(JsonNode command);
}
