package enums;


public enum UnitType {
	A("A","0.02"), B("B","0.02");
	
	UnitType(String name, String tax){
		this.name=name;
		this.tax=tax;
	}
	
	private final String name;
	private final String tax;
	
	public String getName() {
		return name;
	}
	public String getTax() {
		return tax;
	}
	
	
	
}
