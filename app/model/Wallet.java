package model;

import static support.SUPPLIER.CASH_GET;
import static support.SUPPLIER.CASH_SET;
import static support.SUPPLIER.CURRENCYFORMAT_SET;
import static support.SUPPLIER.CURRENCY_GET;
import static support.SUPPLIER.CURRENCY_SET;
import static support.SUPPLIER.LISTFUND_SET;
import static support.SUPPLIER.TOTALVALUEFUNDS_GET;
import static support.SUPPLIER.TOTALVALUEFUNDS_SET;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlRootElement;

import exception.CashException;
import exception.CurrencyException;
import exception.CurrencyFormatException;
import exception.ListFundInWalletException;
import exception.TotalValueFundsException;

/**
 * A class that represents a client's investment portfolio.
 * 
 * @author Marcin Idasiak
 * 
 */

@XmlRootElement
public class Wallet implements Serializable {

	private static final long serialVersionUID = 2847942425181374174L;
	private BigDecimal cash;
	private List<FundInWallet> funds;
	private BigDecimal totalValueFunds;
	private String currency;

	/**
	 * Constructor used when converting to XML
	 * 
	 */

	public Wallet() {
		super();
		this.cash = null;
		this.funds = new LinkedList<FundInWallet>();
		this.totalValueFunds = null;
		this.currency = null;
	}

	
	/**
	 * 
	 * Default constructor with parameters
	 * 
	 * @param cash cash klient
	 * @param funds Funds that have a client
	 * @param totalValueFunds Valuation of fund units, which have a client
	 * @param currency currency in which the money is given.
	 */
	public Wallet(BigDecimal cash, List<FundInWallet> funds,
			BigDecimal totalValueFunds, String currency) {
		super();
		setCash(cash);
		setFunds(funds);
		setTotalValueFunds(totalValueFunds);
		setCurrency(currency);
	}

	/**
	 * Returns cash
	 * 
	 * @return
	 * BigDecimal (CashException if create Wallet() without parameters)
	 */
	
	public BigDecimal getCash() {
		if(cash==null){
			throw new CashException(CASH_GET);
		} 
		return cash;			
	}
	
	/**
	 * Sets cash
	 * @param cash can not be null (CashException)
	 */

	public void setCash(BigDecimal cash) {
		if (cash == null) {
			throw new CashException(CASH_SET);
		}
		this.cash = cash.setScale(2, BigDecimal.ROUND_FLOOR);
	}
	
	/**
	 * Get list of funds from our wallet
	 * @return
	 * List<FundInWallet> 
	 */

	public List<FundInWallet> getFunds() {
		return funds;			
	}

	/**
	 * Set lists of funds in wallet
	 * @param funds can not be null (ListFundInWalletException)
	 */
	
	public void setFunds(List<FundInWallet> funds) {
		if (funds == null) {
			throw new ListFundInWalletException(LISTFUND_SET);
		}
		this.funds = funds;			
	}
	
	/**
	 * Return total value funds;
	 * @return totalValueFunds (ListFundInWalletException if create Wallet() without parameters)
	 */

	public BigDecimal getTotalValueFunds() {
		if(totalValueFunds == null){
			throw new TotalValueFundsException(TOTALVALUEFUNDS_GET);
		}
		return totalValueFunds;			
	}
	
	
	/** 
	 *  Sets new total value funds 
	 * @param totalValueFunds can not be null (TotalValueFundsException)
	 * 
	 */

	public void setTotalValueFunds(BigDecimal totalValueFunds) {
		if (totalValueFunds == null) {
			throw new TotalValueFundsException(TOTALVALUEFUNDS_SET);
		}
		this.totalValueFunds = totalValueFunds.setScale(2,
				BigDecimal.ROUND_FLOOR);
	}

	/**
	 * Returns the type of currency.
	 * @return currency (CurrencyException if create Wallet() without parameters) 
	 */
	
	public String getCurrency() {
		if(currency==null){
			throw new CurrencyException(CURRENCY_GET);
		}
		return currency;
	}

	/**
	 * Sets the type of currency
	 * @param currency can not be null (CurrencyException)
	 */
	
	public void setCurrency(String currency) {
		if(currency==null){
			throw new CurrencyException(CURRENCY_SET);
		} else if(!Pattern.matches("[A-Z]{2,4}", currency)){
			throw new CurrencyFormatException(CURRENCYFORMAT_SET);
		}
		this.currency = currency;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cash == null) ? 0 : cash.hashCode());
		result = prime * result
				+ ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((funds == null) ? 0 : funds.hashCode());
		result = prime * result
				+ ((totalValueFunds == null) ? 0 : totalValueFunds.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Wallet other = (Wallet) obj;
		if (cash == null) {
			if (other.cash != null)
				return false;
		} else if (!cash.equals(other.cash))
			return false;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (funds == null) {
			if (other.funds != null)
				return false;
		} else if (!funds.equals(other.funds))
			return false;
		if (totalValueFunds == null) {
			if (other.totalValueFunds != null)
				return false;
		} else if (!totalValueFunds.equals(other.totalValueFunds))
			return false;
		return true;
	}

}
