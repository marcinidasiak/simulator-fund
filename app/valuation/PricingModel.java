package valuation;

import interfaces.IConstraint;
import interfaces.IValuationUnitFund;

import java.math.BigDecimal;
import java.util.EnumMap;

import support.Constraint;
import enums.InvestmentFund;

/**
 * 
 * Interface to perform the operation a new determination of the price difference.
 * @author Marcin Idasiak
 *
 */
public abstract class PricingModel implements IValuationUnitFund {
	
	public abstract BigDecimal calculateNewPrice(IConstraint con);
	public abstract void setConstraints(EnumMap<InvestmentFund, Constraint> limits);
}