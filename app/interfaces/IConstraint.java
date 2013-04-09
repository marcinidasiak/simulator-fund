package interfaces;

import java.math.BigDecimal;

/**
 * The interface defines the constraints that must meet the values ​​when setting new values.
 * @author Marcin Idasiak
 *
 */
public interface IConstraint {
	public boolean complyWithLimits(Object con);
	public BigDecimal getDown();
	public BigDecimal getUp();
	public int getScope();
}
