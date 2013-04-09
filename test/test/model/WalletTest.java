package test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static support.SUPPLIER.CASH_GET;
import static support.SUPPLIER.CASH_SET;
import static support.SUPPLIER.CURRENCYFORMAT_SET;
import static support.SUPPLIER.CURRENCY_SET;
import static support.SUPPLIER.LISTFUND_SET;
import static support.SUPPLIER.TOTALVALUEFUNDS_GET;
import static support.SUPPLIER.TOTALVALUEFUNDS_SET;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import model.FundInWallet;
import model.Wallet;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import exception.CashException;
import exception.CurrencyException;
import exception.CurrencyFormatException;
import exception.ListFundInWalletException;
import exception.TotalValueFundsException;

@RunWith(Parameterized.class)
public class WalletTest {

	private BigDecimal cash;
	private BigDecimal totalValueFunds;
	private List<FundInWallet> funds;
	private String currency;
	private Wallet wallet;

	public WalletTest(String cash, List<FundInWallet> funds,
			String totalValueFunds, String currency) {
		super();
		this.cash = new BigDecimal(cash);
		this.funds = funds;
		this.totalValueFunds = new BigDecimal(totalValueFunds);
		this.currency = currency;
		this.cash = this.cash.setScale(2, BigDecimal.ROUND_FLOOR);
		this.totalValueFunds = this.totalValueFunds.setScale(2,
				BigDecimal.ROUND_FLOOR);
	}

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Parameters
	public static Collection<Object[]> data() {
		Object[][] data = new Object[][] {
				{ "1234.45", new LinkedList<FundInWallet>(), "1.00", "PLN" },
				{ "1000.00", new LinkedList<FundInWallet>(), "0.99", "PLN" },
				{ "1.01", new LinkedList<FundInWallet>(),
						Long.MAX_VALUE + ".00", "PLN" },
				{ Long.MAX_VALUE + ".46", new LinkedList<FundInWallet>(),
						"0.00", "PLN" },
				{ "0.99", new LinkedList<FundInWallet>(), "1000.00", "PLN" },
				{ "1.00", new LinkedList<FundInWallet>(), "1234.45", "PLN" } };
		return Arrays.asList(data);
	}

	@Before
	public void setUp() throws Exception {
		wallet = new Wallet(cash, funds, totalValueFunds, currency);
	}

	@After
	public void tearDown() throws Exception {
		wallet = null;
	}

	@Test
	public void testHashCode() {
		int hash_1 = wallet.hashCode();
		wallet.setCash(wallet.getCash().add(BigDecimal.ONE));
		int hash_2 = wallet.hashCode();
		assertTrue(hash_1 != hash_2);
	}

	@Test
	public void testWallet() {
		wallet = null;
		assertNull(wallet);
		wallet = new Wallet();
		assertNotNull(wallet);

	}

	@Test
	public void testWalletBigDecimalListOfFundInWalletBigDecimalString() {
		wallet = null;
		assertNull(wallet);
		wallet = new Wallet(cash, funds, totalValueFunds, currency);
		assertNotNull(wallet);
	}

	@Test
	public void testGetCash() {
		assertEquals(this.cash, wallet.getCash());
	}

	@Test
	public void testGetCashInvalid() {
		wallet = new Wallet();
		exception.expect(CashException.class);
		exception.expectMessage(CASH_GET);
		wallet.getCash();
	}

	@Test
	public void testSetCash() {
		assertEquals(this.cash, wallet.getCash());
		BigDecimal val = this.cash.add(new BigDecimal("100.00"));
		wallet.setCash(val);
		assertFalse(this.cash.equals(val));
		assertEquals(val, wallet.getCash());
	}

	@Test
	public void testSetCashInvalid() {
		exception.expect(CashException.class);
		exception.expectMessage(CASH_SET);
		wallet.setCash(null);
	}

	@Test
	public void testGetFunds() {
		assertEquals(this.funds, wallet.getFunds());
	}

	@Test
	public void testSetFunds() {
		LinkedList<FundInWallet> fiw = new LinkedList<>();
		wallet.setFunds(fiw);
		assertEquals(fiw, wallet.getFunds());
	}

	@Test
	public void testSetFundsInvalid() {
		exception.expect(ListFundInWalletException.class);
		exception.expectMessage(LISTFUND_SET);
		wallet.setFunds(null);
	}

	@Test
	public void testGetTotalValueFunds() {
		assertEquals(this.totalValueFunds, wallet.getTotalValueFunds());
	}

	@Test
	public void testGetTotalValueFundsInvalid() {
		wallet = new Wallet();
		exception.expect(TotalValueFundsException.class);
		exception.expectMessage(TOTALVALUEFUNDS_GET);
		wallet.getTotalValueFunds();
	}

	@Test
	public void testSetTotalValueFunds() {
		assertEquals(this.totalValueFunds, wallet.getTotalValueFunds());
		BigDecimal val = totalValueFunds.add(new BigDecimal("100.00"));
		wallet.setTotalValueFunds(val);
		assertFalse(this.totalValueFunds.equals(val));
		assertEquals(val, wallet.getTotalValueFunds());
	}

	@Test
	public void testSetTotalValueFundsInvalid() {
		exception.expect(TotalValueFundsException.class);
		exception.expectMessage(TOTALVALUEFUNDS_SET);
		wallet.setTotalValueFunds(null);
	}

	@Test
	public void testGetCurrency() {
		assertEquals(this.totalValueFunds, wallet.getTotalValueFunds());
	}

	@Test
	public void testGetCurrencyInvalid() {
		wallet = new Wallet();
		exception.expect(CurrencyException.class);
		exception.expectMessage(CURRENCY_SET);
		wallet.setCurrency(null);
	}

	@Test
	public void testSetCurrency() {
		assertEquals(this.currency, wallet.getCurrency());
		String val = this.currency.equals("PLN") ? "USD" : "PLN";
		wallet.setCurrency(val);
		assertEquals(val, wallet.getCurrency());
	}

	@Test
	public void testSetCurrencyInvalid() {
		exception.expect(CurrencyException.class);
		exception.expectMessage(CURRENCY_SET);
		wallet.setCurrency(null);
	}

	@Test
	public void testSetCurrencyFormat1() {
		exception.expect(CurrencyFormatException.class);
		exception.expectMessage(CURRENCYFORMAT_SET);
		String val = this.currency.substring(1);
		wallet.setCurrency("0" + val);
	}

	@Test
	public void testSetCurrencyFormat2() {
		exception.expect(CurrencyFormatException.class);
		exception.expectMessage(CURRENCYFORMAT_SET);
		String val = this.currency.substring(1);
		wallet.setCurrency("a" + val);
	}

	@Test
	public void testSetCurrencyFormat3() {
		exception.expect(CurrencyFormatException.class);
		exception.expectMessage(CURRENCYFORMAT_SET);
		String val = this.currency;
		wallet.setCurrency(val + "" + val + "" + val);
	}

	@Test
	public void testEqualsObject() {
		Wallet walletTest = new Wallet(cash, funds, totalValueFunds, currency);
		assertEquals(walletTest, wallet);
	}

}
