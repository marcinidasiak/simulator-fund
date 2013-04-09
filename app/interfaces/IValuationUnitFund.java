package interfaces;

import support.FundIteam;

/**
 * Interface for every source which will be generate new valuation for simulation.
 * @author Marcin Idasiak
 *
 */
public interface IValuationUnitFund {
	public FundIteam newPrice();
}