package database;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.sql.*;

import databaseObjects.AbbreviationTable;
import databaseObjects.Party;
import databaseObjects.Politician;

/**
 * 
 * @author Christoffer Strandberg
 *
 */
public class Initializer {
	
	/**
	 * API
	 */
	private final String URL_API = "http://data.riksdagen.se/personlista/?fnamn=&enamn=&parti=&utformat=json&charset=UTF-8";
	
	private Client client;
	private WebTarget target;
	private LinkedList<Politician> politicians;
	private AbbreviationTable table;
	
	/**
	 * Database
	 */
	private final String URL_DB = "jdbc:mysql://195.178.232.16:3306/AB7455";
	private final String CONNECTIONUSER = "AB7455";
	private final String CONNECTIONPASSWORD = "kajsaecool";
	
	private final String QUERRY_INSERT_PARTY = " insert into parties (name, nameShort)"
	        + " values (?, ?)";
	private final String QUERRY_INSERT_POLITICIAN = " insert into politcans (name, nameShort)"
	        + " values (?, ?)";
	/**
	 * Other
	 */
//	private final String querry = "INSERT INTO parties values(3,'ay','S');";
	
	public Initializer(){
		client = ClientBuilder.newClient();
		target = client.target(URL_API);
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
		} catch(SQLException e){
			System.out.println("Could not get response from DB");
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

//		for(int i = 0; i < politicians.size(); i++){
//			Politician tempPol = politicians.get(i);
//			System.out.println(tempPol.getParty().getPartyName() + ": " + tempPol.getFirstname() + ", " + tempPol.getSurname());
//		}
	}
	
	private void initializeDB()throws SQLException{
		Connection conn = DriverManager.getConnection(URL_DB,CONNECTIONUSER,CONNECTIONPASSWORD);
//		Statement stmt = conn.createStatement();
		for(int i = 0; i < politicians.size(); i++){
			
			updateParty(conn, politicians.get(i).getParty());
			updatePolitician(conn, politicians.get(i));
		}
		
		conn.close();
	}
	
	public void updateParty(Connection conn, Party party){
//		Party tempParty = politicians.get(i).getParty();
		try {
			PreparedStatement tempStatement = conn.prepareStatement(QUERRY_INSERT_PARTY);
			tempStatement.setString(1, party.getPartyName());
			tempStatement.setString(2, party.getPartyAbbreviation());
			tempStatement.executeUpdate();
			System.out.println("List updated");
		} catch (SQLException e) {
			System.out.println("Already in the list");
		}
	}
	
	public void updatePolitician(Connection conn, Politician politician){
		
	}
	
	private String getResponseFromRiksdagen(){
		return target.request(MediaType.APPLICATION_JSON).get(String.class).toString();
	}
	
	public static void main(String[] args){
		new Initializer();
	}
}
