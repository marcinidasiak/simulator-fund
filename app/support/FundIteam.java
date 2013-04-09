package support;

import java.math.BigDecimal;
import java.util.HashMap;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 * Storing the new values ​​generated funds.
 * 
 * @author Marcin Idasiak
 * 
 */
public class FundIteam {
	HashMap<String, BigDecimal> item;
	DateTime time;

	/**
	 * Simple create for Poland
	 */
	public FundIteam() {
		item = new HashMap<String, BigDecimal>();
		time = DateTime.now(DateTimeZone.forID("Europe/Warsaw"));
	}

	/**
	 * Create an object based on time zone.
	 * 
	 * @param zone
	 */
	public FundIteam(DateTimeZone zone) {
		item = new HashMap<String, BigDecimal>();
		time = DateTime.now(zone);
	}

	/**
	 * Add a new valuation.
	 * 
	 * @param key
	 * @param value
	 */
	public void put(String key, BigDecimal value) {
		item.put(key, value);
	}

	/**
	 * Get valuation
	 * 
	 * @param key
	 * @return
	 */
	public BigDecimal get(String key) {
		return item.get(key);
	}

	/**
	 * Get all keys.
	 * 
	 * @return
	 */
	public String[] getKeys() {
		String[] array = new String[item.size()];
		return (String[]) item.keySet().toArray(array);
	}

}
