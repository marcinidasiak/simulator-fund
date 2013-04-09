package support;

import java.math.BigDecimal;
import java.util.HashMap;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class FundIteam {
	HashMap<String, BigDecimal> item;
	DateTime time;

	public FundIteam() {
		item = new HashMap<String, BigDecimal>();
		time = DateTime.now(DateTimeZone.forID("Europe/Warsaw"));
	}
	
	public FundIteam(DateTimeZone zone) {
		item = new HashMap<String,BigDecimal>();
		time = DateTime.now(zone);
	}
	
	public void put(String key, BigDecimal value){
		item.put(key, value);
	}
	
	public BigDecimal get(String key){
		return item.get(key);
	}
	
	public String[] getKeys(){
		String[] array=new String[item.size()];
		return (String[])item.keySet().toArray(array);
	}

}
