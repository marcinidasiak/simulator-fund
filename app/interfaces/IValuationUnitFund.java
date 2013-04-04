package interfaces;

import model.FundIteam;

/*
 *  Interface for every source which will be generate new valuation for simulation.
 */

public interface IValuationUnitFund {
	public FundIteam newPrice();
}