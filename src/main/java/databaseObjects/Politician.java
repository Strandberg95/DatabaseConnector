package databaseObjects;

public class Politician {
	private String firstname;
	private String surname;
	private Party party;
	
	public Politician(String firstname, String surname, String partyAbbreviation){
		this.firstname = firstname;
		this.surname = surname;
		party = new Party(partyAbbreviation);
	}
	
	public String getFirstname(){
		return firstname;
	}
	
	public String getSurname(){
		return surname;
	}
	
	public Party getParty(){
		return party;
	}

}
