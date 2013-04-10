package test.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import io.ReadWriteWallet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.LinkedList;

import model.FundInWallet;
import model.Wallet;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import enums.InvestmentFund;
import enums.UnitType;

public class ReadWriteWalletTest {
	private LinkedList funds;
	private Wallet wallet;
	private ReadWriteWallet testing;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		funds=new LinkedList<FundInWallet>();
		funds.add(new FundInWallet(new BigInteger("100"), UnitType.A, InvestmentFund.FA));
		funds.add(new FundInWallet(new BigInteger("120"), UnitType.B, InvestmentFund.FRP));
		funds.add(new FundInWallet(new BigInteger("20"), UnitType.B, InvestmentFund.FSW));
		funds.add(new FundInWallet(new BigInteger("0"), UnitType.A, InvestmentFund.FSW));
		wallet = new Wallet(new BigDecimal("100.00"), funds, new BigDecimal("34234.92"), "PLN");
		
		testing = new ReadWriteWallet();
	}

	@After
	public void tearDown() throws Exception {
		funds=null;
		wallet=null;		
	}

	@Test
	public void testReadWallet() throws Exception {
		String xml="<wallet><cash>945.70</cash><currency>PLN</currency><funds><fund>FA</fund><numberUnit>9</numberUnit><type>A</type></funds><totalValueFunds>918.00</totalValueFunds></wallet>";
		String location = "read.xml";
		File file=new File(location);
		if(file.exists()){
			file.delete();
		}
		file.createNewFile();
		
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(xml);
		bw.close();
		
		wallet = testing.readWallet(location);
		assertEquals(new BigDecimal("945.70"), wallet.getCash());
		assertEquals("PLN", wallet.getCurrency());
		assertEquals(new BigDecimal("918.00"), wallet.getTotalValueFunds());
		assertTrue(wallet.getFunds().contains(new FundInWallet(new BigInteger("9"), UnitType.A, InvestmentFund.FA)));
		file.delete();
	}
	
	@Test
	public void testReadWallet2() throws Exception {
		String xml="eurobank";
		String location = "read.xml";
		File file=new File(location);
		if(file.exists()){
			file.delete();
		}
		file.createNewFile();
		
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(xml);
		bw.close();
		
		exception.expect(Exception.class);
		wallet = testing.readWallet(location);
		file.delete();
		
	}

	@Test
	public void testSaveWallet() throws Exception {
		String location = "save.xml";
		File file=new File(location);
		if(file.exists()){
			file.delete();
		}
		file.createNewFile();
		
		testing.saveWallet(location, wallet);
		assertTrue(file.exists());
		Wallet w = testing.readWallet(location);
		assertEquals(w, wallet);
	}

}
