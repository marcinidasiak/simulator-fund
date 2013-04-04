package model;

import interfaces.IUnitType;

import java.math.BigInteger;

import enums.InvestmentFund;

public abstract class Fund {

	IUnitType type;	
	
	public Fund(IUnitType type) {
		super();
		this.type = type;
	}
	
	public abstract void buy(BigInteger units);
	public abstract void sell(BigInteger units);
	
}
