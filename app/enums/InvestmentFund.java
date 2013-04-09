package enums;

import java.math.BigDecimal;

import exception.PriceException;


/**
 * Enum which is the heart of the application. Defines the investment funds and their default values.
 * @author Marcin Idasiak
 *
 */
public enum InvestmentFund {
	FRP("Fundusz Rynku Pieniężnego"), FO("Fundusz Obligacji"), FSW(
			"Fundusz Stabilnego Wzrostu"), FZ("Fundusz Zrównoważony"), FA(
			"FunduszAkcyjny");

	InvestmentFund(String nameFund) {
		this.name = nameFund;
		setPrice(new BigDecimal("100.00"));
	}

	InvestmentFund(String nameFund, BigDecimal price) {
		this.name = nameFund;
		setPrice(price);
	}

	private final String name;
	private BigDecimal price;
	private final String currency = "PLN";

	public synchronized BigDecimal getPrice() {
		return price;
	}

	public synchronized void  setPrice(BigDecimal price) {
		int comp = price.compareTo(BigDecimal.ZERO);
		if (comp < 0) {
			throw new PriceException("Fund price may not be lower than 0.00 | "
					+ this.getName() + ".setPrice: " + price);
		} else if (comp == 0) {
			this.price = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_FLOOR);
		} else {
			this.price = format(price);
		}
	}

	public synchronized void addPrice(BigDecimal price) {
		this.price = this.price.add(format(price));
		if (this.price.compareTo(BigDecimal.ZERO) < 0) {
			this.price = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_FLOOR);
		}
	}

	public String getCurrency() {
		return currency;
	}

	public String getName() {
		return name;
	}

	private BigDecimal format(BigDecimal price) {
		BigDecimal decimal = price.setScale(2, BigDecimal.ROUND_FLOOR);
		if (decimal.compareTo(price) != 0) {
			throw new PriceException(
					"Price fund has a bad format, should be xxx.dd  | "
							+ this.getName() + ".format: " + price);
		}
		return decimal;
	}

}