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

public class ReadWriteWallet implements IReadWriteWallet {

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
