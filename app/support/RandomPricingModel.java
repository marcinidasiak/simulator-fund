package support;

import interfaces.IConstraint;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Random;

import enums.InvestmentFund;
import exception.RandomScopeException;

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

	@Override
	public FundIteam newPrice() {
		FundIteam items = new FundIteam();
		for (InvestmentFund fund : InvestmentFund.values()) {
			items.put(fund.toString(), calculateNewPrice(limit.get(fund)));
		}
		return items;
	}

	@Override
	public BigDecimal calculateNewPrice(IConstraint con) {
			if (con == null){
				throw new RandomScopeException("The requirement to calculate the new price is null.");
			}
			int scope = con.getScope();
			int number = rand.nextInt(scope);
			BigDecimal result = new BigDecimal(number).divideToIntegralValue(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_FLOOR).add(((Constraint) con).getDown());
			if(con.complyWithLimits(result)){
				return result;
			}
			throw new RandomScopeException("random number does not meet the requirements");
	}

	private void setConstraints(Random rand) {
		limit = new EnumMap<InvestmentFund, Constraint>(
				InvestmentFund.class);
		limit.put(InvestmentFund.FRP, new Constraint(new BigDecimal(-0.05d),
				new BigDecimal(0.40d)));
		limit.put(InvestmentFund.FO, new Constraint(new BigDecimal(-0.15d),
				new BigDecimal(0.60d)));
		limit.put(InvestmentFund.FZ, new Constraint(new BigDecimal(-0.65d),
				new BigDecimal(0.85d)));
		limit.put(InvestmentFund.FSW, new Constraint(new BigDecimal(-0.80d),
				new BigDecimal(0.96d)));
		limit.put(InvestmentFund.FA, new Constraint(new BigDecimal(-1.05d),
				new BigDecimal(1.10d)));
		this.rand = rand;
	}
}
