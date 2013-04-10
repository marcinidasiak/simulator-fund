package test.valuation;

import static org.junit.Assert.*;

import interfaces.IConstraint;

import java.math.BigDecimal;
import java.util.EnumMap;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import support.Constraint;
import support.FundIteam;
import valuation.RandomPricingModel;
import enums.InvestmentFund;

public class RandomPricingModelTest {

	private EnumMap<InvestmentFund, Constraint> limit;
	private RandomPricingModel rand;
	

	@Before
	public void setUp() throws Exception {
		rand = new RandomPricingModel();
		limit = new EnumMap<InvestmentFund, Constraint>(
				InvestmentFund.class);
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
	}

	@After
	public void tearDown() throws Exception {
		limit=null;
		rand=null;
	}

	@Test
	public void testCalculateNewPrice() {
		for(InvestmentFund f: InvestmentFund.values()){
			IConstraint con=limit.get(f);
			for(int i=0;i<100;i++){
				assertTrue(con.complyWithLimits(rand.calculateNewPrice(con)));
			}
		}
	}

	@Test
	public void testNewPrice() {
		for(int i=0; i<100; i++){
			FundIteam data = rand.newPrice();
			for(InvestmentFund f: InvestmentFund.values()){				
				assertTrue(limit.get(f).complyWithLimits(data.get(f.toString())));
			}
		}
	}

}
