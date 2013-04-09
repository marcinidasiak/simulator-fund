package valuation;

import interfaces.IConstraint;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Random;

import support.Constraint;
import support.FundIteam;
import enums.InvestmentFund;
import exception.RandomScopeException;

/**
 * Implementation class to set new valuations.
 * 
 * @author Marcin Idasiak
 * 
 */
public class RandomPricingModel extends PricingModel {

	EnumMap<InvestmentFund, Constraint> limit;
	Random rand;

	public RandomPricingModel() {
		super();
		setConstraints(new Random());
	}

	public RandomPricingModel(Random rand) {
		super();
		setConstraints(rand);
	}

	/**
	 * Appointment of new valuations for all funds.
	 */
	@Override
	public FundIteam newPrice() {
		FundIteam items = new FundIteam();
		for (InvestmentFund fund : InvestmentFund.values()) {
			items.put(fund.toString(), calculateNewPrice(limit.get(fund)));
		}
		return items;
	}

	/**
	 * Creating a price on the basis of limitations.
	 */
	@Override
	public BigDecimal calculateNewPrice(IConstraint con) {
		if (con == null) {
			throw new RandomScopeException(
					"The requirement to calculate the new price is null.");
		}
		int scope = con.getScope();
		int number = rand.nextInt(scope);
		BigDecimal result = new BigDecimal(number)
				.divideToIntegralValue(new BigDecimal("100"))
				.setScale(2, BigDecimal.ROUND_FLOOR)
				.add(((Constraint) con).getDown());
		if (con.complyWithLimits(result)) {
			return result;
		}
		throw new RandomScopeException(
				"random number does not meet the requirements");
	}

	/**
	 * Defining restrictions.
	 * @param rand
	 */
	private void setConstraints(Random rand) {
		limit = new EnumMap<InvestmentFund, Constraint>(InvestmentFund.class);
		limit.put(InvestmentFund.FRP, new Constraint(new BigDecimal("-0.05"),
				new BigDecimal("0.40")));
		limit.put(InvestmentFund.FO, new Constraint(new BigDecimal("-0.15"),
				new BigDecimal("0.60")));
		limit.put(InvestmentFund.FZ, new Constraint(new BigDecimal("-0.65"),
				new BigDecimal("0.85")));
		limit.put(InvestmentFund.FSW, new Constraint(new BigDecimal("0.80"),
				new BigDecimal("0.96")));
		limit.put(InvestmentFund.FA, new Constraint(new BigDecimal("1.0"),
				new BigDecimal("1.10")));
		this.rand = rand;
	}

	/**
	 * Setting limits
	 */
	@Override
	public void setConstraints(EnumMap<InvestmentFund, Constraint> limits) {
		this.limit=limits;
	}
}
