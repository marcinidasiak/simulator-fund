package valuation;

import interfaces.IConstraint;
import interfaces.IValuationUnitFund;

import java.math.BigDecimal;

public abstract class PricingModel implements IValuationUnitFund {
	
	public abstract BigDecimal calculateNewPrice(IConstraint con);

}