package io;

import interfaces.IReadWriteWallet;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.LinkedList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import support.SUPPLIER;

import model.FundInWallet;
import model.Wallet;

/**
 * Class for writing and reading from an xml file the client's portfolio, using JAXB
 * @author Marcin Idasiak
 *
 */
public class ReadWriteWallet implements IReadWriteWallet {

	/**
	 * Reading the client's portfolio from a specific location
	 */
	@Override
	public Wallet readWallet(String location) throws Exception {
		Wallet wallet = null;

		final File walletXml = new File(location);
		if (walletXml.exists()) {
			JAXBContext context = JAXBContext
					.newInstance(new Class[] { model.Wallet.class });
			Unmarshaller um = context.createUnmarshaller();
			wallet = (Wallet) um.unmarshal(walletXml);
		}
		return (wallet==null)?new Wallet(SUPPLIER.CASH, new LinkedList<FundInWallet>(), BigDecimal.ZERO, SUPPLIER.CURRENCY):wallet;
	}

	/**
	 * Writing the client's portfolio to xml file specified location.
	 */
	@Override
	public void saveWallet(String location, Wallet wallet) throws Exception {
		final File memberXml = new File(location);
		JAXBContext context = JAXBContext
				.newInstance(new Class[] { model.Wallet.class });
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(wallet, new FileOutputStream(memberXml));
	}

}
