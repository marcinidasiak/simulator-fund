package support;

import interfaces.IConstraint;

import java.math.BigDecimal;

import scala.Int;

import exception.PriceException;
import exception.RandomScopeException;

/**
 * Class for specifying constraints on the values ​​determined.
 * 
 * @author Marcin Idasiak
 * 
 */
public class Constraint implements IConstraint {
	private BigDecimal down;
	private BigDecimal up;
	private int scope;

	/**
	 * Creating constraints
	 * 
	 * @param down
	 * @param up
	 */
	public Constraint(BigDecimal down, BigDecimal up) {
		super();
		if (up.compareTo(down) < 0) {
			throw new PriceException("Calculate new price");
		}
		this.down = down.setScale(2, BigDecimal.ROUND_FLOOR);

		this.up = up.setScale(2, BigDecimal.ROUND_FLOOR);

		this.range();
	}

	/**
	 * Get the value of the upper limit
	 */
	public BigDecimal getDown() {
		return down;
	}

	/**
	 * Get the value of the lower limit
	 */
	public BigDecimal getUp() {
		return up;
	}

	/**
	 * Get the size range between the lower and upper bound,
	 */
	public int getScope() {
		return scope;
	}

	/**
	 * Calculation of the range
	 */
	private void range() {
		BigDecimal scope_down = down.multiply(new BigDecimal("100.00"));
		BigDecimal scope_up = up.multiply(new BigDecimal("100.00"));

		int comp_down = scope_down.compareTo(BigDecimal.ZERO);
		int comp_up = scope_up.compareTo(BigDecimal.ZERO);

		BigDecimal result;
		if (comp_down < 0.0 && comp_up < 0) {
			result = scope_down.abs().subtract(scope_up.abs());
		} else if (comp_down <= 0 && comp_up >= 0) {
			result = scope_down.abs().add(scope_up.abs());
		} else {
			result = scope_up.subtract(scope_down);
		}
		if (result.compareTo(new BigDecimal(Int.MaxValue())) >= 0) {
			throw new RandomScopeException(
					"The scope is too large for a random value, down =" + down
							+ "| up = " + up + " | scope = " + this.scope);
		}
		this.scope = result.intValue();

		if (this.scope < 0) {
			throw new RandomScopeException("Range is less than zero, down ="
					+ down + " - scope_down= " + scope_down + "| up = " + up
					+ " - scope_up= " + scope_up + " | scope = " + this.scope);
		}
	}

	/**
	 * The method checks whether the object is in the specified range.
	 */
	@Override
	public boolean complyWithLimits(Object con) {
		if (con instanceof BigDecimal) {
			BigDecimal ob = ((BigDecimal) con);
			int comp_down = ob.compareTo(down);
			int comp_up = ob.compareTo(up);
			return comp_down >= 0 && comp_up <= 0;
		}

		return false;
	}
}