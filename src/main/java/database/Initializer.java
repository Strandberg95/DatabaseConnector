package database;

import java.util.LinkedList;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import databaseObjects.AbbreviationTable;
import databaseObjects.Politician;

public class Initializer {
	
	private final String URL = "http://data.riksdagen.se/personlista/?fnamn=&enamn=&parti=&utformat=json&charset=UTF-8";
	
	private Client client;
	private WebTarget target;
	private LinkedList<Politician> politicians;
	private AbbreviationTable table;
	
	public Initializer(){
		client = ClientBuilder.newClient();
		target = client.target(URL);
		politicians = new LinkedList<Politician>();
		table = new AbbreviationTable();
		initialize();
	}
	
	private void initialize(){
		try {
			initializeRiksdagen();
			initializeDB();
		} catch (JSONException e) {
			System.out.println("Could not get response from API");
			e.printStackTrace();
		}
	}
	
	private void initializeRiksdagen()throws JSONException{
		JSONObject jsonResponse = new JSONObject(getResponseFromRiksdagen());
		JSONObject search = (JSONObject) jsonResponse.get("personlista");
		JSONArray entryArray = (JSONArray) search.get("person");
		
		for(int i = 0; i < entryArray.length(); i++){
			JSONObject tempObj = entryArray.getJSONObject(i);
			politicians.add(new Politician((String)tempObj.get("tilltalsnamn"),(String)tempObj.get("efternamn"),(String)tempObj.get("parti")));
		}

		for(int i = 0; i < politicians.size(); i++){
			Politician tempPol = politicians.get(i);
			System.out.println(tempPol.getParty().getPartyName() + ": " + tempPol.getFirstname() + ", " + tempPol.getSurname());
		}
		 
//		politicians.add(new Politician(entryArray.getString(0).))
//		System.out.println(entryArray.toString());

	}
	
	private void initializeDB(){
		
	}
	
	private String getResponseFromRiksdagen(){
		return target.request(MediaType.APPLICATION_JSON).get(String.class).toString();
	}
	
	public static void main(String[] args){
		new Initializer();
	}
}
