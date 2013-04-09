package model;

import java.io.Serializable;
import java.math.BigInteger;

import static support.SUPPLIER.*;

import enums.InvestmentFund;
import enums.UnitType;
import exception.InvestmentFundException;
import exception.NumberUnitException;
import exception.UnitTypeException;

/**
 * 
 * Class represents the number of units of a particular fund and its type. The
 * class is a POJO
 * 
 * @author Marcin idasiak
 * 
 */

public class FundInWallet implements Serializable {

	private static final long serialVersionUID = -9034966118886883918L;
	private BigInteger numberUnit;
	private UnitType type;
	private InvestmentFund fund;

	/**
	 * Constructor used when converting to XML
	 * 
	 */

	public FundInWallet() {
		super();
		numberUnit = null;
		type = null;
		fund = null;
	}

	/**
	 * Default constructor with parameters
	 * 
	 * @param numberUnit
	 *            BigInteger can not be null or less than zero.
	 * @param type
	 *            type of Fund
	 * @param fund
	 *            Fund
	 */
	public FundInWallet(BigInteger numberUnit, UnitType type,
			InvestmentFund fund) {
		super();
		setNumberUnit(numberUnit);
		setType(type);
		setFund(fund);
	}

	/**
	 * Returns the number of units
	 * 
	 * @return BigInteger (NumberUnitException if create FundInWallet() without parameters)
	 */
	public BigInteger getNumberUnit() {
		if(numberUnit==null){
			throw new NumberUnitException(NUMBERUNIT_GET);
		}
		return numberUnit;
	}

	/**
	 * Sets the number of units
	 * 
	 * @param numberUnit
	 *            BigInteger can not be null or less than zero
	 *            (NumberUnitException).
	 */

	public void setNumberUnit(BigInteger numberUnit) {
		if (numberUnit==null || BigInteger.ZERO.compareTo(numberUnit) > 0) {
			throw new NumberUnitException(NUMBERUNIT_SET);
		} else {
			this.numberUnit = numberUnit;
		}
	}

	/**
	 * Returns the type of fund.
	 * 
	 * @return UniType =[ "A", "B" ]
	 */

	public UnitType getType() {
		if(type==null){
			throw new UnitTypeException(UNITTYPE_GET);
		} else {			
			return type;
		}
	}

	/**
	 * Sets the type of fund
	 * 
	 * @param type
	 *            UniType can not be null (UnitTypeException)
	 */

	public void setType(UnitType type) {
		if (type == null) {
			throw new UnitTypeException(UNITTYPE_SET);
		} else {
			this.type = type;
		}
	}

	/**
	 * Return fund.
	 * 
	 * @return InvestmentFund
	 */

	public InvestmentFund getFund() {
		if(fund==null){
			throw new InvestmentFundException(INVESTMENTFUND_GET);
		}
		return fund;
	}

	/**
	 * Sets fund
	 * @param fund
	 * InvestmentFund can not be null (InvestmentFundException)
	 */

	public void setFund(InvestmentFund fund) {
		if (fund == null) {
			throw new InvestmentFundException(INVESTMENTFUND_SET);
		} else {
			this.fund = fund;			
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fund == null) ? 0 : fund.hashCode());
		result = prime * result
				+ ((numberUnit == null) ? 0 : numberUnit.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		if (fund != other.fund)
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	
	
}
