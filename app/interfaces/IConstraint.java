package interfaces;

import java.math.BigDecimal;

public interface IConstraint {
	public boolean complyWithLimits(Object con);
	public BigDecimal getDown();
	public BigDecimal getUp();
	public int getScope();
}
