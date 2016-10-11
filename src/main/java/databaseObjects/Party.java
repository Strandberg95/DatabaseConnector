package databaseObjects;

public class Party {
	private String abbreviation;
	private String name;
	
	public Party(String abbreviation){
		this.abbreviation = abbreviation;
		AbbreviationTable table = new AbbreviationTable();
		name = table.getNameFromAbbreviation(abbreviation);
	}
	
	public String getPartyAbbreviation(){
		return abbreviation;
	}
	
	public String getPartyName(){
		return name;
	}
}
