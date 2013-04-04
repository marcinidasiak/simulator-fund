package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Wallet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2847942425181374174L;
	private BigDecimal cash;
	private List<FundInWallet> funds;
	private BigDecimal totalValueFunds;
	private String currency;
	
	public Wallet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Wallet(BigDecimal cash, List<FundInWallet> funds,
			BigDecimal totalValueFunds) {
		super();
		this.cash = cash;
		this.funds = funds;
		this.totalValueFunds = totalValueFunds;
	}

	public BigDecimal getCash() {
		return cash;
	}

	public void setCash(BigDecimal cash) {
		this.cash = cash;
	}

	public List<FundInWallet> getFunds() {
		return funds;
	}

	public void setFunds(List<FundInWallet> funds) {
		this.funds = funds;
	}

	public BigDecimal getTotalValueFunds() {
		return totalValueFunds;
	}

	public void setTotalValueFunds(BigDecimal totalValueFunds) {
		this.totalValueFunds = totalValueFunds;
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
