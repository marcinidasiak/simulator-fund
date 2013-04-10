package test.transactions;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.LinkedList;

import model.FundInWallet;
import model.Wallet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import enums.InvestmentFund;
import enums.UnitType;

import transactions.Transaction;

public class TransactionTest {
	private Wallet wallet;
	private Transaction trans;
	private LinkedList<FundInWallet> funds;
	private BigDecimal cash;
	private BigDecimal total;
	private String currency;

	@Before
	public void setUp() throws Exception {
		trans = new Transaction();
		cash = new BigDecimal("100.00");
		total = new BigDecimal("34234.92");
		currency = "PLN";
		funds=new LinkedList<FundInWallet>();
		funds.add(new FundInWallet(new BigInteger("100"), UnitType.A, InvestmentFund.FA));
		funds.add(new FundInWallet(new BigInteger("120"), UnitType.B, InvestmentFund.FRP));
		funds.add(new FundInWallet(new BigInteger("20"), UnitType.B, InvestmentFund.FSW));
		funds.add(new FundInWallet(new BigInteger("0"), UnitType.A, InvestmentFund.FSW));
		wallet = new Wallet(cash, funds, total, currency);
	}

	@After
	public void tearDown() throws Exception {
		wallet=null;
		trans=null;
		funds=null;
		cash=null;
		total=null;
		currency=null;
	}

	@Test
	public void testSellValidB() {
		assertTrue(trans.sell(new BigInteger("15"), new BigDecimal("3.41"), wallet, UnitType.B, InvestmentFund.FSW));
		assertEquals(cash.add(new BigDecimal("50.13")),wallet.getCash());
		for(FundInWallet f: wallet.getFunds()){
			if(f.equals(new FundInWallet(BigInteger.ZERO, UnitType.B, InvestmentFund.FSW))){
				assertEquals(new BigInteger("5"),f.getNumberUnit());
			}
		}
	}
	
	@Test
	public void testSellInValid() {
		assertFalse(trans.sell(new BigInteger("15"), new BigDecimal("3.41"), wallet, UnitType.A, InvestmentFund.FSW));
		assertFalse(trans.sell(new BigInteger("21"), new BigDecimal("3.41"), wallet, UnitType.B, InvestmentFund.FSW));
	}
	
	@Test
	public void testSellValidA() {
		assertTrue(trans.sell(new BigInteger("43"), new BigDecimal("3.41"), wallet, UnitType.A, InvestmentFund.FA));
		assertEquals(cash.add(new BigDecimal("146.63")),wallet.getCash());
		for(FundInWallet f: wallet.getFunds()){
			if(f.equals(new FundInWallet(BigInteger.ONE, UnitType.A, InvestmentFund.FA))){
				assertEquals(new BigInteger("57"),f.getNumberUnit());
			}
		}
	}

	@Test
	public void testBuyNotCash() {
		assertFalse(trans.buy(new BigInteger("30"), new BigDecimal("3.41"), wallet, UnitType.B, InvestmentFund.FSW));
		assertFalse(trans.buy(new BigInteger("35"), new BigDecimal("3.41"), wallet, UnitType.A, InvestmentFund.FSW));
		assertEquals(cash,wallet.getCash());
		
	}
	
	@Test
	public void testBuyValidB() {
		assertTrue(trans.buy(new BigInteger("29"), new BigDecimal("3.41"), wallet, UnitType.B, InvestmentFund.FSW));
		assertEquals(new BigDecimal("1.11"),wallet.getCash());
		for(FundInWallet f: wallet.getFunds()){
			if(f.equals(new FundInWallet(BigInteger.ZERO, UnitType.B, InvestmentFund.FSW))){
				assertEquals(new BigInteger("49"),f.getNumberUnit());
				break;
			}
		}
	}

	@Test
	public void testBuyInValidA() {
		assertFalse(trans.buy(new BigInteger("29"), new BigDecimal("3.41"), wallet, UnitType.A, InvestmentFund.FA));
	}
	
	@Test
	public void testBuyValidA() {
		assertTrue(trans.buy(new BigInteger("25"), new BigDecimal("3.41"), wallet, UnitType.A, InvestmentFund.FA));
		assertEquals(new BigDecimal("13.04"),wallet.getCash());
		for(FundInWallet f: wallet.getFunds()){
			if(f.equals(new FundInWallet(BigInteger.ZERO, UnitType.A, InvestmentFund.FA))){
				assertEquals(new BigInteger("125"),f.getNumberUnit());
				break;
			}
		}
	}
	
	@Test
	public void testBuyAddNewA() {
		assertTrue(trans.buy(new BigInteger("25"), new BigDecimal("3.41"), wallet, UnitType.A, InvestmentFund.FO));
		assertEquals(new BigDecimal("13.04"),wallet.getCash());
		for(FundInWallet f: wallet.getFunds()){
			if(f.equals(new FundInWallet(BigInteger.ZERO, UnitType.A, InvestmentFund.FO))){
				assertEquals(new BigInteger("25"),f.getNumberUnit());
				break;
			}
		}
	}
	
	@Test
	public void testBuyAddNewB() {
		assertTrue(trans.buy(new BigInteger("25"), new BigDecimal("3.41"), wallet, UnitType.B, InvestmentFund.FO));
		assertEquals(new BigDecimal("14.75"),wallet.getCash());
		for(FundInWallet f: wallet.getFunds()){
			if(f.equals(new FundInWallet(BigInteger.ZERO, UnitType.B, InvestmentFund.FO))){
				assertEquals(new BigInteger("25"),f.getNumberUnit());
				break;
			}
		}
	}
}

