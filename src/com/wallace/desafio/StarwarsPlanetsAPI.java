package com.wallace.desafio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.DeleteResult;

@Path("starwarsplanetsapi")
public class StarwarsPlanetsAPI {
	
	//Lists all the planets on DB
	//Receives no input
	//Returns planets info in the form of a JSON Array
	@GET
	@Path("/listplanets")
	public Response listPlanets() {
		
		//Opens connection with database
		MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
		
		//Gets the collection
		MongoDatabase database = mongoClient.getDatabase("starwars");
		MongoCollection<Document> collection = database.getCollection("planets");
		
		//Creates and initializes planet object
		MongoIterable<Document> planetsTemp = null;
		
		try {

			//Looks for planets
			planetsTemp = collection.find();
			
		} catch (Exception exception) {
			
			//Logs error, closes connection and sends response
			exception.printStackTrace();
			mongoClient.close();
			return Response.serverError().build();
			
		}
		
		//Copies found planets info and closes connection
		Iterator<Document> planets = planetsTemp.iterator();
		mongoClient.close();
		
		//Verifies if there's a planet in the search result
		if (!planets.hasNext()) {
			return Response.noContent().build();
		}
		
		//Instantiate JSON Array for storing planets
		JSONArray planetsJSON = new JSONArray();
		
		try {
			
			//Fills the JSON Array with the planets
			while (planets.hasNext()) {
				
				//Gets next planet
				Document planet = planets.next();
				
				//Creates a JSON Object of a planet and copies the fields of current planet
				JSONObject planetJSON = new JSONObject();
				planetJSON.put("id", planet.getObjectId("_id"));
				planetJSON.put("name", planet.getString("name"));
				planetJSON.put("climate", planet.getString("climate"));
				planetJSON.put("terrain", planet.getString("terrain"));
				planetJSON.put("number_of_films", planet.getInteger("number_of_films"));
				
				//Adds JSON of current planet to the JSON Array of planets
				planetsJSON.put(planetJSON);
				
			}
			
			//Sends response
			return Response.ok().entity(planetsJSON.toString()).build();
		
		} catch (Exception exception) {
			
			//Logs error and sends response
			exception.printStackTrace();
			return Response.serverError().build();
			
		}
		
	}
	
	//Searches DB for a planet with inputed name
	//Receives the name of the planet in the form of a string parameter on the URL
	//Returns planet info in the form of a JSON
	@GET
	@Path("/searchbyname/{name}")
	public Response searchName(@PathParam("name") String name) {
		
		//Opens connection with database
		MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
		
		//Gets the collection
		MongoDatabase database = mongoClient.getDatabase("starwars");
		MongoCollection<Document> collection = database.getCollection("planets");
		
		//Creates and initializes planet object
		Document planet = null;
		
		try {
			
			//Looks for a planet with inserted name
			planet = collection.find(eq("name", name)).first();
			
		} catch (Exception exception) {
			
			//Logs error, closes connection and sends response
			exception.printStackTrace();
			mongoClient.close();
			return Response.serverError().build();
			
		}
		
		//Closes connection with DB
		mongoClient.close();
		
		//Verifies if there's a planet in the search result
		if (planet == null) {
			return Response.noContent().build();
		}
		
		try {
			
			//Creates a JSON Object of a planet and copies the fields of the found planet
			JSONObject planetJSON = new JSONObject();
			planetJSON.put("id", planet.getObjectId("_id"));
			planetJSON.put("name", planet.getString("name"));
			planetJSON.put("climate", planet.getString("climate"));
			planetJSON.put("terrain", planet.getString("terrain"));
			planetJSON.put("number_of_films", planet.getInteger("number_of_films"));
			
			//Sends response
			return Response.ok().entity(planetJSON.toString()).build();
			
		} catch (Exception exception) {
			
			//Logs error and sends response
			exception.printStackTrace();
			return Response.serverError().build();
			
		}
		
	}
	
	//Searches DB for a planet with inputed id
	//Receives the id of the planet in the form of a string parameter on the URL
	//Returns planet info in the form of a JSON
	@GET
	@Path("/searchbyid/{id}")
	public Response searchID(@PathParam("id") String id) {
		
		//Opens connection with database
		MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
		
		//Gets the collection
		MongoDatabase database = mongoClient.getDatabase("starwars");
		MongoCollection<Document> collection = database.getCollection("planets");
		
		//Creates and initializes object id
		ObjectId objectId = null;
		
		try {
			
			//Creates object id from inputed id
			objectId = new ObjectId(id);
			
		} catch (Exception exception) {
			
			//Logs error, closes connection and sends response
			exception.printStackTrace();
			mongoClient.close();
			return Response.noContent().build();
			
		}
		
		//Creates and initializes planet object
		Document planet = null;
		
		try {
			
			//Looks for a planet with object of inputed id
			planet = collection.find(eq("_id", objectId)).first();
			
		} catch (Exception exception) {
			
			//Logs error, closes connection and sends response
			exception.printStackTrace();
			mongoClient.close();
			return Response.serverError().build();
			
		}
		
		//Closes connection with DB
		mongoClient.close();

		//Verifies if there's a planet in the search result
		if (planet == null) {
			return Response.noContent().build();
		}
		
		try {
			
			//Creates a JSON Object of a planet and copies the fields of the found planet
			JSONObject planetJSON = new JSONObject();
			planetJSON.put("id", planet.getObjectId("_id"));
			planetJSON.put("name", planet.getString("name"));
			planetJSON.put("climate", planet.getString("climate"));
			planetJSON.put("terrain", planet.getString("terrain"));
			planetJSON.put("number_of_films", planet.getInteger("number_of_films"));
			
			//Closes connection and sends response
			return Response.ok().entity(planetJSON.toString()).build();
			
		} catch (Exception exception) {
			
			//Closes connection and sends response
			exception.printStackTrace();
			return Response.serverError().build();
			
		}
		
	}
	
	//Deletes a planet with inputed id from DB
	//Receives the id of the planet in the form of a string parameter on the URL
	//Returns result of the delete on the HTTP status code
	@DELETE
	@Path("/deleteplanet/{id}")
	public Response deletePlanet(@PathParam("id") String id) {
		
		//Opens connection with database
		MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
		
		//Gets the collection
		MongoDatabase database = mongoClient.getDatabase("starwars");
		MongoCollection<Document> collection = database.getCollection("planets");
		
		//Creates and initializes object id
		ObjectId objectId = null;
		
		try {
			
			//Looks for a planet with inserted id
			objectId = new ObjectId(id);
			
		} catch (Exception exception) {
			
			//Logs error, closes connection and sends response
			exception.printStackTrace();
			mongoClient.close();
			return Response.noContent().build();
			
		}
		
		//Creates and initializes planet object
		Document planet = null;
		
		try {
		
			planet = collection.find(eq("_id", objectId)).first();
			
		} catch (Exception exception) {
			
			//Logs error, closes connection and sends response
			exception.printStackTrace();
			mongoClient.close();
			return Response.serverError().build();
			
		}
		
		//Verifies if there's a planet in the search result
		if (planet == null) {
			mongoClient.close();
			return Response.noContent().build();
		}
		
		try {
			
			//Tries to delete the planet
			DeleteResult deleteResult = collection.deleteOne(planet);
			if(deleteResult.wasAcknowledged()) {
				
				//Closes connection and sends response
				mongoClient.close();
				return Response.ok().build();
				
			} else {
				
				//Closes connection and sends response
				mongoClient.close();
				return Response.serverError().build();
				
			}
			
		} catch (Exception exception) {
			
			//Closes connection and sends response
			exception.printStackTrace();
			mongoClient.close();
			return Response.serverError().build();
			
		}
		
	}
	
	//Adds a planet with inputed to DB
	//Receives JSON containing name, climate and terrain of the planet
	//Returns result of the addition on the HTTP status code
	@POST
	@Path("/addplanet/")
	public Response addPlanet(String inputPlanetJSONString) {
		
		//Creates and initializes string to receive the response of HTTTP request
		String responseString = null;
		
		try {
			
			//Opens connection and defines request headers
			URL url = new URL("https://swapi.co/api/planets/");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", "Mozilla/5.0");
			connection.setRequestProperty("Accept", "application/json");
		
			//Verifies if expected response was obtained
			int status = connection.getResponseCode();
			if (status != 200) {
				return Response.serverError().build();
			}
			
			//Reads response and convert it to string
			BufferedReader responseBuffer = new BufferedReader(
					  new InputStreamReader(connection.getInputStream()));
			String responseLine;
			StringBuffer response = new StringBuffer();
			while ((responseLine = responseBuffer.readLine()) != null) {
			    response.append(responseLine);
			}
			responseBuffer.close();
			responseString = response.toString();
			
		} catch (Exception exception) {
			
			//Logs error and sends response
			exception.printStackTrace();
			return Response.serverError().build();
			
		}
		
		//Parses inputPlanet to JSONObject
		JSONObject inputPlanetJSON;
		try {
			inputPlanetJSON = new JSONObject(inputPlanetJSONString);
		} catch (Exception exception) {
			return Response.noContent().build();
		}
		
		//Verifies if the planet exists and gets its info
		boolean planetExists = false;
		JSONObject planet = new JSONObject();
		try {
			
			//Gets array of planets from SWAPI
			JSONObject jsonObjectResponse = new JSONObject(responseString);
			JSONArray planetsArray = jsonObjectResponse.getJSONArray("results");
			
			//Verifies if there's a star wars planet with the name of the to be inserted one
			int count = planetsArray.length();
			for (int i = 0; i < count; i++) {
				
				planet = planetsArray.getJSONObject(i);
				if (planet.getString("name").equals(inputPlanetJSON.getString("name"))) {
					
					planetExists = true;
					break;
					
				}
				
			}
			
		} catch (Exception exception) {
			
			exception.printStackTrace();
			return Response.serverError().build();
			
		}
			
		//Return no content if no planet with such name was found
		if (!planetExists) {
			return Response.noContent().build();
		}
		
		
		//Opens connection with database
		MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
		
		//Gets the collection
		MongoDatabase database = mongoClient.getDatabase("starwars");
		MongoCollection<Document> collection = database.getCollection("planets");
		
		//Creates and initializes object of planet to be verified on DB
		Document verifyPlanet = null;
		
		try {
			
			//Looks for a planet with inserted name
			verifyPlanet = collection.find(eq("name", inputPlanetJSON.getString("name"))).first();
			
		} catch (Exception exception) {
			
			//Closes connection and sends response
			exception.printStackTrace();
			mongoClient.close();
			return Response.serverError().build();
			
		}
		
		//Verifies if there's already a planet on DB with the name of the to be inserted one
		if (verifyPlanet != null) {
			mongoClient.close();
			return Response.ok().build();
		}
			
		try {
			
			//Prepare new planet JSON using user entered and SWAPI data 
			int numberOfFilms = planet.getJSONArray("films").length();
			JSONObject newPlanetJSON = new JSONObject();
			newPlanetJSON.put("name", inputPlanetJSON.getString("name"));
			newPlanetJSON.put("climate", inputPlanetJSON.getString("climate"));
			newPlanetJSON.put("terrain", inputPlanetJSON.getString("terrain"));
			newPlanetJSON.put("number_of_films", numberOfFilms);
			
			//Parses newPlanetJSON to document in order to add it to MongoDB collection 
			Document newPlanet = Document.parse(newPlanetJSON.toString());
			collection.insertOne(newPlanet);
			
			//Closes and sends response
			mongoClient.close();
			return Response.created(null).build();
			
		} catch (Exception e) {
			
			//Closes connection and sends response
			mongoClient.close();
			e.printStackTrace();
			return Response.serverError().build();
			
		}
		
	}

}
