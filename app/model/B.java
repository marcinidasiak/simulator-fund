package model;

import interfaces.IUnitType;

import java.math.BigDecimal;

public class B implements IUnitType {
 	private String type;
 	private BigDecimal commission = new BigDecimal("0.02");
	
	@Override
	public String getTypeName() {
		return type;
	}

	@Override
	public BigDecimal calculateBuyCommission(BigDecimal withoutCommission) {
		return BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_UP);
	}

	@Override
	public BigDecimal calculateSellCommission(BigDecimal withoutCommission) {
		BigDecimal charge= commission.multiply(withoutCommission);
		return charge.setScale(2, BigDecimal.ROUND_UP);
	}

}
