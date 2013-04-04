package enums;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import scala.Int;

import exception.PriceException;

@RunWith(Parameterized.class)
public class InvestmentFundTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	private InvestmentFund fund;
	private String name;
	private String currency;
	private BigDecimal price;

	public InvestmentFundTest(InvestmentFund fund, String name, String currency, BigDecimal price) {
		this.fund = fund;
		this.name = name;
		this.currency = currency;
		this.price=price;
	}

	@Parameters
	public static Collection<Object[]> data() {
		Object[][] data = new Object[][] {
				{ InvestmentFund.FA, "FunduszAkcyjny", "zł", new BigDecimal("100.00")},
				{ InvestmentFund.FO, "Fundusz Obligacji", "zł", new BigDecimal("100.00")},
				{ InvestmentFund.FRP, "Fundusz Rynku Pieniężnego", "zł", new BigDecimal("100.00")},
				{ InvestmentFund.FSW, "Fundusz Stabilnego Wzrostu", "zł", new BigDecimal("100.00")},
				{ InvestmentFund.FZ, "Fundusz Zrównoważony", "zł", new BigDecimal("100.00") } 
		};
		return Arrays.asList(data);
	}
	
	@Test
	public void testInvestmentFundGet() {
		assertEquals(this.name, fund.getName());
		assertEquals(this.price, fund.getPrice());
		assertEquals(this.currency, fund.getCurrency());
	}

	@Test
	public void testPrice() {

		fund.setPrice(new BigDecimal("0.00"));
		assertEquals("0.00", fund.getPrice().toString());

		fund.setPrice(new BigDecimal("100.00"));
		assertEquals("100.00", fund.getPrice().toString());
		
		fund.setPrice(new BigDecimal("-0.00"));
		assertEquals("0.00", fund.getPrice().toString());
		
		fund.setPrice(new BigDecimal("100"));
		assertEquals("100.00", fund.getPrice().toString());
		
		fund.setPrice(new BigDecimal(Int.MaxValue()));
		assertEquals(Int.MaxValue()+".00", fund.getPrice().toString());
	}

	@Test
	public void testSetInvalidPrice1() {
		BigDecimal decimal=new BigDecimal("-100.00");
		exception.expect(PriceException.class);
		exception.expectMessage("Fund price may not be lower than 0.00 | "+ fund.getName() + ".setPrice: " + decimal);
		fund.setPrice(decimal);
	}
	
	@Test
	public void testSetInvalidPrice2() {
		BigDecimal decimal=new BigDecimal("-0.0001");
		exception.expect(PriceException.class);
		exception.expectMessage("Fund price may not be lower than 0.00 | "+ fund.getName() + ".setPrice: " + decimal);
		fund.setPrice(decimal);
	}

	@Test
	public void testSetInvalidPrice3() {
		BigDecimal decimal=new BigDecimal(123456789.123456789d);
		exception.expect(PriceException.class);
		exception.expectMessage("Price fund has a bad format, should be xxx.dd  | " + fund.getName() + ".format: " + decimal);
		fund.setPrice(decimal);
	}
	
	@Test
	public void testSetInvalidPriceFloatMin() {
		BigDecimal decimal=new BigDecimal(Float.MIN_EXPONENT);
		exception.expect(PriceException.class);
		exception.expectMessage("Fund price may not be lower than 0.00 | "+ fund.getName() + ".setPrice: " + decimal);
		fund.setPrice(decimal);
	}

	@Test
	public void testAddPrice() {
		fund.setPrice(new BigDecimal("100.00"));
		assertEquals("100.00", fund.getPrice().toString());

		fund.addPrice(new BigDecimal("1.12"));
		assertEquals("101.12", fund.getPrice().toString());
		
		fund.addPrice(new BigDecimal("-0.23"));
		assertEquals("100.89", fund.getPrice().toString());
	}

}
