package com.wallace.desafio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
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
		
		try {
			
			//Gets the collection
			MongoDatabase database = mongoClient.getDatabase("starwars");
			MongoCollection<Document> collection = database.getCollection("planets");
			
			//Looks for planets
			MongoIterable<Document> planets = collection.find();
			
			//Verifies if there's a planet in the search result
			if (planets.first() == null) {
				mongoClient.close();
				return Response.noContent().build();
			}
			
			//Instantiate JSON Array for storing planets
			JSONArray planetsJSON = new JSONArray();
			
			//Fills the JSON Array with the planets
			for (Document planet:planets) {
				
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
			
			//Closes connection and sends response
			mongoClient.close();
			return Response.ok().entity(planetsJSON.toString()).build();
		
		} catch (Exception exception) {
			
			//Closes connection and sends response
			exception.printStackTrace();
			mongoClient.close();
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
		
		try {
			
			//Gets the collection
			MongoDatabase database = mongoClient.getDatabase("starwars");
			MongoCollection<Document> collection = database.getCollection("planets");
			
			//Looks for a planet with inserted name
			Document planet = collection.find(eq("name", name)).first();
			
			//Verifies if there's a planet in the search result
			if (planet == null) {
				mongoClient.close();
				return Response.noContent().build();
			}
			
			//Creates a JSON Object of a planet and copies the fields of the found planet
			JSONObject planetJSON = new JSONObject();
			planetJSON.put("id", planet.getObjectId("_id"));
			planetJSON.put("name", planet.getString("name"));
			planetJSON.put("climate", planet.getString("climate"));
			planetJSON.put("terrain", planet.getString("terrain"));
			planetJSON.put("number_of_films", planet.getInteger("number_of_films"));
			
			//Closes connection and sends response
			mongoClient.close();
			return Response.ok().entity(planetJSON.toString()).build();
			
		} catch (Exception exception) {
			
			//Closes connection and sends response
			exception.printStackTrace();
			mongoClient.close();
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
		
		try {
			
			//Gets the collection
			MongoDatabase database = mongoClient.getDatabase("starwars");
			MongoCollection<Document> collection = database.getCollection("planets");
			
			//Looks for a planet with inserted id
			ObjectId objectId = new ObjectId(id);
			Document planet = collection.find(eq("_id", objectId)).first();

			//Verifies if there's a planet in the search result
			if (planet == null) {
				mongoClient.close();
				return Response.noContent().build();
			}
			
			//Creates a JSON Object of a planet and copies the fields of the found planet
			JSONObject planetJSON = new JSONObject();
			planetJSON.put("id", planet.getObjectId("_id"));
			planetJSON.put("name", planet.getString("name"));
			planetJSON.put("climate", planet.getString("climate"));
			planetJSON.put("terrain", planet.getString("terrain"));
			planetJSON.put("number_of_films", planet.getInteger("number_of_films"));
			
			//Closes connection and sends response
			mongoClient.close();
			return Response.ok().entity(planetJSON.toString()).build();
			
		} catch (Exception exception) {
			
			//Closes connection and sends response
			exception.printStackTrace();
			mongoClient.close();
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
		
		try {
			
			//Gets the collection
			MongoDatabase database = mongoClient.getDatabase("starwars");
			MongoCollection<Document> collection = database.getCollection("planets");
			
			//Looks for a planet with inserted id
			ObjectId objectId = new ObjectId(id);
			Document planet = collection.find(eq("_id", objectId)).first();
			
			//Verifies if there's a planet in the search result
			if (planet == null) {
				mongoClient.close();
				return Response.noContent().build();
			}
			
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
		
		try {
			
			URL url = new URL("https://swapi.co/api/planets/");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", "Mozilla/5.0");
			connection.setRequestProperty("Accept", "application/json");
			
			int status = connection.getResponseCode();
			if (status != 200) {
				
			}
			
			BufferedReader responseBuffer = new BufferedReader(
					  new InputStreamReader(connection.getInputStream()));
			String responseLine;
			StringBuffer response = new StringBuffer();
			while ((responseLine = responseBuffer.readLine()) != null) {
			    response.append(responseLine);
			}
			responseBuffer.close();
			
			//Gets array of planets from SWAPI
			JSONObject jsonObjectResponse = new JSONObject(response.toString());
			JSONArray planetsArray = jsonObjectResponse.getJSONArray("results");
			
			//Parses inputPlanet to JSONObject
			JSONObject inputPlanetJSON = new JSONObject(inputPlanetJSONString);
			
			//Verifies if there's a star wars planet with the name of the to be inserted one
			boolean planetExists = false;
			JSONObject planet = new JSONObject();
			int count = planetsArray.length();
			for (int i = 0; i < count; i++) {
				
				planet = planetsArray.getJSONObject(i);
				if (planet.getString("name").equals(inputPlanetJSON.getString("name"))) {
					
					planetExists = true;
					break;
					
				}
				
			}
			
			//Return no content if no planet with such name was found
			if (!planetExists) {
				return Response.noContent().build();
			}
			
			//Prepare new planet JSON using user entered and SWAPI data 
			int numberOfFilms = planet.getJSONArray("films").length();
			JSONObject newPlanetJSON = new JSONObject();
			newPlanetJSON.put("name", inputPlanetJSON.getString("name"));
			newPlanetJSON.put("climate", inputPlanetJSON.getString("climate"));
			newPlanetJSON.put("terrain", inputPlanetJSON.getString("terrain"));
			newPlanetJSON.put("number_of_films", numberOfFilms);
			
			//Opens connection with database
			MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
			
			//Gets the collection
			MongoDatabase database = mongoClient.getDatabase("starwars");
			MongoCollection<Document> collection = database.getCollection("planets");
			
			//Looks for a planet with inserted name
			Document verifyPlanet = collection.find(eq("name", inputPlanetJSON.getString("name"))).first();
			
			//Verifies if there's already a planet on db with the name of the to be inserted one
			if (verifyPlanet != null) {
				mongoClient.close();
				return Response.ok().build();
			}
			
			//Parses newPlanetJSON to document in order to add it to mongo db collection 
			Document newPlanet = Document.parse(newPlanetJSON.toString());
			collection.insertOne(newPlanet);
			
			//problem, mongo connection not always closed
			mongoClient.close();
			return Response.created(null).build();
			
			//problem, mongo connection not always closed
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//problem, mongo connection not always closed
		return null;
		
	}

}