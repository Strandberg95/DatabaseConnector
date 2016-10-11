package databaseObjects;

import java.util.Hashtable;

public class AbbreviationTable {
	
	Hashtable table;
	
	public AbbreviationTable(){
		table = new Hashtable();
		
		table.put("C", "Centerpartiet");
		table.put("KD", "Kristdemokraterna");
		table.put("L", "Liberalerna");
		table.put("MP", "Miljöpartiet de gröna");
		table.put("M", "Moderata samlingspartiet");
		table.put("S", "Socialdemokraterna");
		table.put("SD", "Sverigedemokraterna");
		table.put("V", "Vänsterpartiet");
	}
	
	public String getNameFromAbbreviation(String abbreviation){
		return (String)table.get(abbreviation);
	}

}
