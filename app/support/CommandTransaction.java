package support;

public class CommandTransaction {
	private String code;
	private String type;
	private String typefund;
	private String numberUnit;
	
	public CommandTransaction(String code, String type, String typefund,
			String numberUnit) {
		super();
		this.code = code;
		this.type = type;
		this.typefund = typefund;
		this.numberUnit = numberUnit;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTypefund() {
		return typefund;
	}
	public void setTypefund(String typefund) {
		this.typefund = typefund;
	}
	public String getNumberUnit() {
		return numberUnit;
	}
	public void setNumberUnit(String numberUnit) {
		this.numberUnit = numberUnit;
	}
	
	
}
