package enums;


public enum UnitType {
	A("A"), B("B");
	
	UnitType(String name){
		this.name=name;
	}
	
	private final String name;
}
