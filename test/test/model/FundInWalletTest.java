package test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;

import model.FundInWallet;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import support.SUPPLIER;
import enums.InvestmentFund;
import enums.UnitType;
import exception.InvestmentFundException;
import exception.NumberUnitException;
import exception.UnitTypeException;

@RunWith(Parameterized.class)
public class FundInWalletTest {

	private BigInteger numberUnit;
	private UnitType type;
	private InvestmentFund code;
	private FundInWallet fund;

	public FundInWalletTest(BigInteger numberUnit, UnitType type,
			InvestmentFund code) {
		super();
		this.numberUnit = numberUnit;
		this.type = type;
		this.code = code;
	}

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Parameters
	public static Collection<Object[]> data() {
		Object[][] data = new Object[][] {
				{ new BigInteger("2"), UnitType.A, InvestmentFund.FA },
				{ new BigInteger("2"), UnitType.A, InvestmentFund.FO },
				{ new BigInteger("2"), UnitType.B, InvestmentFund.FO },
				{ new BigInteger("12"), UnitType.B, InvestmentFund.FO },
				{ new BigInteger("2"), UnitType.B, InvestmentFund.FO } };
		return Arrays.asList(data);
	}

	@Before
	public void setUp() throws Exception {
		fund = new FundInWallet(this.numberUnit, this.type, this.code);
	}

	@After
	public void tearDown() throws Exception {
		fund = null;
	}

	@Test
	public void testHashCode() {
		int hash_1=fund.hashCode();
		fund.setNumberUnit(fund.getNumberUnit().add(BigInteger.ONE));
		int hash_2=fund.hashCode();
		assertTrue(hash_1!=hash_2);
	}

	@Test
	public void testFundInWalletBigIntegerUnitTypeString() {
		FundInWallet fund = new FundInWallet(this.numberUnit, this.type,
				this.code);
		assertNotNull(fund);
	}

	@Test
	public void testGetNumberUnit() {
		assertEquals(this.numberUnit, fund.getNumberUnit());
		assertFalse(!this.numberUnit.equals(fund.getNumberUnit()));
	}

	@Test
	public void testGetNumberUnitInvalid() {
		fund = new FundInWallet();
		exception.expect(NumberUnitException.class);
		exception.expectMessage(SUPPLIER.NUMBERUNIT_GET);
		fund.getNumberUnit();
	}

	@Test
	public void testSetNumberUnit() {
		assertEquals(this.numberUnit, fund.getNumberUnit());
		assertFalse(!this.numberUnit.equals(fund.getNumberUnit()));
		BigInteger val = this.numberUnit.add(new BigInteger("100"));
		fund.setNumberUnit(val);
		assertFalse(this.numberUnit.equals(val));
		assertEquals(val, fund.getNumberUnit());
		assertFalse(!val.equals(fund.getNumberUnit()));
	}

	@Test
	public void testSetNumberUnitInvalid() {
		exception.expect(NumberUnitException.class);
		exception.expectMessage(SUPPLIER.NUMBERUNIT_SET);
		fund.setNumberUnit(null);
	}

	@Test
	public void testGetType() {
		assertEquals(this.type, fund.getType());
		for (UnitType typ : UnitType.values()) {
			if (!this.type.equals(typ)) {
				assertFalse(fund.getType().equals(typ));
			}
		}
	}

	@Test
	public void testGetTypeInvalid() {
		fund = new FundInWallet();
		exception.expect(UnitTypeException.class);
		exception.expectMessage(SUPPLIER.UNITTYPE_GET);
		fund.getType();
	}

	@Test
	public void testSetTypeInvalid() {
		fund = new FundInWallet();
		exception.expect(UnitTypeException.class);
		exception.expectMessage(SUPPLIER.UNITTYPE_SET);
		fund.setType(null);
	}

	@Test
	public void testSetType() {
		assertEquals(this.type, fund.getType());
		for (UnitType typ : UnitType.values()) {
			if (!this.type.equals(typ)) {
				fund.setType(typ);
				assertEquals(typ, fund.getType());
			}
		}
	}

	@Test
	public void testGetFund() {
		assertEquals(this.code, fund.getFund());
		for (InvestmentFund f : InvestmentFund.values()) {
			if (!this.code.equals(f)) {
				assertFalse(fund.getFund().equals(f));
			}
		}
	}

	@Test
	public void testGetFundInvalid() {
		fund = new FundInWallet();
		exception.expect(InvestmentFundException.class);
		exception.expectMessage(SUPPLIER.INVESTMENTFUND_GET);
		fund.getFund();
	}

	@Test
	public void testSetFundInvalid() {
		fund = new FundInWallet();
		exception.expect(InvestmentFundException.class);
		exception.expectMessage(SUPPLIER.INVESTMENTFUND_SET);
		fund.setFund(null);
	}

	@Test
	public void testSetFund() {
		assertEquals(this.code, fund.getFund());
		for (InvestmentFund f : InvestmentFund.values()) {
			if (!this.code.equals(f)) {
				fund.setFund(f);
				assertEquals(f, fund.getFund());
			}
		}
	}

	@Test
	public void testEqualsObject() {
		FundInWallet fundtest = new FundInWallet(this.numberUnit, this.type,
				this.code);
		for (InvestmentFund f : InvestmentFund.values()) {
			fundtest.setFund(f);
			if (!this.code.equals(f)) {
				assertFalse(fund.getFund().equals(f));
			}else {
				assertEquals(fundtest, fund);				
			}
		}
	}

}
