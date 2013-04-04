package model;

import interfaces.IUnitType;

import java.math.BigInteger;

import enums.InvestmentFund;

public class FundInWallet extends Fund {

	BigInteger numberUnit;

	public FundInWallet(IUnitType type) {
		super(type);
		this.numberUnit=new BigInteger("0");
	}

	@Override
	public void buy(BigInteger units) {

	}

	@Override
	public void sell(BigInteger units) {
	
	}
	
	public BigInteger getNumberUnit(){
		return numberUnit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((numberUnit == null) ? 0 : numberUnit.hashCode());
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
		FundInWallet other = (FundInWallet) obj;
		if (numberUnit == null) {
			if (other.numberUnit != null)
				return false;
		} else if (!numberUnit.equals(other.numberUnit))
			return false;
		return true;
	}

	
}
