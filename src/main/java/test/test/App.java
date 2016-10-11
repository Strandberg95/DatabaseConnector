package test.test;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import databaseObjects.AbbreviationTable;

/**
 * Hello world!
 *
 */
public class App{
	
    public static void main( String[] args ){
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://data.riksdagen.se/personlista/?fnamn=&enamn=&parti=&utformat=json&charset=UTF-8");
		String response = target.request(MediaType.APPLICATION_JSON).get(String.class).toString();
	    

		try {
			
			JSONObject jsonObject = new JSONObject(response);
			JSONObject search = (JSONObject) jsonObject.get("personlista");//1
		    JSONArray entry = (JSONArray) search.get("person");//2
		    
		    AbbreviationTable table = new AbbreviationTable();
		    
		    for(int i = 0; i < entry.length(); i++){
		    	JSONObject obj = entry.getJSONObject(i);
		    	System.out.println(table.getNameFromAbbreviation((String)obj.get("parti")));
		    	System.out.println(obj.get("parti"));
		    }
		    
//		    JSONObject obj1 = entry.getJSONObject(0);
//		    System.out.println(obj1.get("tilltalsnamn"));
//		    System.out.println(obj1.get("efternamn"));
//		    System.out.println(obj1.get("parti")); 
//		    
//		    JSONObject obj2 = entry.getJSONObject(1);
//		    System.out.println(obj2.get("efternamn"));
//		    System.out.println(obj2.get("parti")); 
//		    
//		    JSONObject obj3 = entry.getJSONObject(2);
//		    System.out.println(obj3.get("efternamn"));
//		    System.out.println(obj3.get("parti")); 
//			
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
}
