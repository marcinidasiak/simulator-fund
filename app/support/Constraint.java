package support;

import interfaces.IConstraint;

import java.math.BigDecimal;

import scala.Int;

import exception.PriceException;
import exception.RandomScopeException;

public class Constraint implements IConstraint {
	private BigDecimal down;
	private BigDecimal up;
	private int scope;

	public Constraint(BigDecimal down, BigDecimal up) {
		super();
		if (up.compareTo(down) < 0) {
			throw new PriceException("Calculate new price");
		}
		this.down = down.setScale(2, BigDecimal.ROUND_FLOOR);
		;
		this.up = up.setScale(2, BigDecimal.ROUND_FLOOR);
		;
		this.range();
	}

	public BigDecimal getDown() {
		return down;
	}

	public BigDecimal getUp() {
		return up;
	}

	public int getScope() {
		return scope;
	}

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
					"The scope is too large for a random value, down =" + down + "| up = " + up + " | scope = " + this.scope);
		}
		this.scope = result.intValue();
		
		if (this.scope < 0) {
			throw new RandomScopeException(
					"Range is less than zero, down =" + down + " - scope_down= " + scope_down + "| up = " + up + " - scope_up= " + scope_up + " | scope = " + this.scope );
		}
	}

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