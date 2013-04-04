package model;


import java.io.Serializable;
import java.math.BigInteger;

import enums.UnitType;

public class FundInWallet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9034966118886883918L;
	private BigInteger numberUnit;
	private UnitType type;
	private String code;

	
	public FundInWallet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FundInWallet(BigInteger numberUnit, UnitType type, String code) {
		super();
		this.numberUnit = numberUnit;
		this.type = type;
		this.code = code;
	}

	public BigInteger getNumberUnit() {
		return numberUnit;
	}

	public void setNumberUnit(BigInteger numberUnit) {
		this.numberUnit = numberUnit;
	}

	public UnitType getType() {
		return type;
	}

	public void setType(UnitType type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (numberUnit == null) {
			if (other.numberUnit != null)
				return false;
		} else if (!numberUnit.equals(other.numberUnit))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
}
