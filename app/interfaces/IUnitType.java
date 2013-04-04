package interfaces;

import java.math.BigDecimal;

public interface IUnitType {
	public String getTypeName();
	public BigDecimal calculateBuyCommission(BigDecimal withoutCommission);
	public BigDecimal calculateSellCommission(BigDecimal withoutCommission);
}
