package com.wallace.desafio;

import java.io.IOException;
import java.util.HashMap;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.ValidationOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.BasicDBObject;

@Path("zipservice")
public class ZipCodeService {
	
	@GET
	@Path("/test")
	public Response test() {
		
		MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
		MongoDatabase database = mongoClient.getDatabase("starwars");
		MongoCollection<Document> collection = database.getCollection("planets");
		
		MongoIterable<Document> documents = collection.find();
		
		for (Document document:documents) {
			System.out.println(document.getString("name"));
		}
		 
		
		mongoClient.close();
		
		String planetsString = null;
		
		return Response.ok().entity(planetsString).build();
		
	}
	
	@GET
	@Path("/listPlanetsTest")
	public Response listPlanetsTest() {
		
		Response response = Response.serverError().build();
		MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
		
		try {
			
			MongoDatabase database = mongoClient.getDatabase("starwars");
			MongoCollection<Document> collection = database.getCollection("planets");
			
			MongoIterable<Document> planets = collection.find();
			
			JSONArray planetsJSON = new JSONArray();
			
			if (planets != null) {
				
				for (Document planet:planets) {
					
					JSONObject planetJSON = new JSONObject();
					planetJSON.put("id", planet.getObjectId("_id"));
					planetJSON.put("name", planet.getString("name"));
					planetJSON.put("climate", planet.getString("climate"));
					planetJSON.put("terrain", planet.getString("terrain"));
					
					planetsJSON.put(planetJSON);
				}
				
				response = Response.ok().entity(planetsJSON.toString()).build();
				
			} else {
				
				response = Response.ok().entity(null).build();
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
		mongoClient.close();
		return response;
		
	}
	
	@GET
	@Path("/listPlanets")
	public Response listPlanets() {
		
		MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
		
		try {
			mongoClient.listDatabases().first();
		} catch (Exception e) {
			return Response.serverError().build();
		}
		
		MongoDatabase database = mongoClient.getDatabase("starwars");
		MongoCollection<Document> collection = database.getCollection("planets");
		
		MongoIterable<Document> planets = collection.find();
		
		if (planets.first() == null) {
			mongoClient.close();
			return Response.noContent().build();
		}
		
		JSONArray planetsJSON = new JSONArray();
			
		try {
		
			for (Document planet:planets) {
				
				JSONObject planetJSON = new JSONObject();
				planetJSON.put("id", planet.getObjectId("_id"));
				planetJSON.put("name", planet.getString("name"));
				planetJSON.put("climate", planet.getString("climate"));
				planetJSON.put("terrain", planet.getString("terrain"));
				
				planetsJSON.put(planetJSON);
			}
			
			mongoClient.close();
			return Response.ok().entity(planetsJSON.toString()).build();
		
		} catch (Exception e) {
			
			e.printStackTrace();
			mongoClient.close();
			return Response.serverError().build();
			
		}
		
	}
	
	@GET
	@Path("/searchName/{name}")
	public Response searchName(@PathParam("name") String name) {
		
		Response response = Response.serverError().build();
		MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
		
		try {
			
			MongoDatabase database = mongoClient.getDatabase("starwars");
			MongoCollection<Document> collection = database.getCollection("planets");
			
			Document planet = collection.find(eq("name", name)).first();

			JSONObject planetJSON = new JSONObject();
			
			if (planet != null) {
				
				planetJSON.put("id", planet.getObjectId("_id"));
				planetJSON.put("name", planet.getString("name"));
				planetJSON.put("climate", planet.getString("climate"));
				planetJSON.put("terrain", planet.getString("terrain"));
				
				response = Response.ok().entity(planetJSON.toString()).build();
				
			} else {
				
				response = Response.ok().entity(null).build();
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
		mongoClient.close();
		return response;
		
	}
	
	@GET
	@Path("/searchID/{id}")
	public Response searchID(@PathParam("id") String id) {
		
		Response response = Response.serverError().build();
		MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
		
		try {
			
			MongoDatabase database = mongoClient.getDatabase("starwars");
			MongoCollection<Document> collection = database.getCollection("planets");
			
			ObjectId objectId = new ObjectId(id);
			Document planet = collection.find(eq("_id", objectId)).first();

			JSONObject planetJSON = new JSONObject();
			
			if (planet != null) {
				
				planetJSON.put("id", planet.getObjectId("_id"));
				planetJSON.put("name", planet.getString("name"));
				planetJSON.put("climate", planet.getString("climate"));
				planetJSON.put("terrain", planet.getString("terrain"));
				
				response = Response.ok().entity(planetJSON.toString()).build();
				
			} else {
				
				response = Response.ok().entity(null).build();
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
		mongoClient.close();
		return response;
		
	}
	
	@DELETE
	@Path("/deletePlanet/{id}")
	public Response deletePlanet(@PathParam("id") String id) {
		
		Response response = Response.serverError().build();
		MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
		
		try {
			
			MongoDatabase database = mongoClient.getDatabase("starwars");
			MongoCollection<Document> collection = database.getCollection("planets");
			
			ObjectId objectId = new ObjectId(id);
			Document planet = collection.find(eq("_id", objectId)).first();
			
			if (planet != null) {
				
				DeleteResult deleteResult = collection.deleteOne(planet);
				
				if(deleteResult.wasAcknowledged()) {
					
					response = Response.ok().build();
					
				} else {
					
					response = Response.serverError().build();
					
				}
				
			} else {
				
				response = Response.noContent().build();
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
		mongoClient.close();
		return response;
		
	}
	
	
	
	@GET
	@Path("/loockup/{zipcode}/{extension}")
	public Response loockup(@PathParam("zipcode") String zipcode, @PathParam("extension") String ext) throws JsonProcessingException {
		
		if (zipcode == null || zipcode.length() < 5) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("zip code missing or not long enough").build();
		}
		
		City city = new City();
		city.setZip(zipcode + "-" + ext);
		city.setCityName("New York");
		city.setLatitude("45.3");
		city.setLongitude("45.2");
		
		XmlMapper xmlMapper = new XmlMapper();
		String xmlString = xmlMapper.writeValueAsString(city);
		
		return Response.ok().entity(xmlString).build();
		
	}
	
	@POST
	@Path("/add")
	public Response addZipCode(MultivaluedMap<String, String> formParams) {
		
		String zipcode = formParams.getFirst("zipcode");
		String note = formParams.getFirst("note");
		
		return Response.ok().entity("received: " + zipcode + "-" + note).build();
		
	}
	
	@POST
	@Path("/addwithjson")
	public Response addWithJson(String jsonInputString) throws JsonParseException, JsonMappingException, IOException {
		
		ObjectMapper objectMapper = new ObjectMapper();
		City city = objectMapper.readValue(jsonInputString, City.class);
		
		return Response.ok().entity("Returning city: " + city.getCityName() + "-" + city.getZip()).build();
		
	}
	
	@POST
	@Path("/addwithjsonstring")
	public Response addWithJsonString(String jsonInputString) throws JsonParseException, JsonMappingException, IOException {
		
		HashMap<String, String> result = new ObjectMapper().readValue(jsonInputString, HashMap.class);
		
		String cityName = result.get("cityName");
		String zip = result.get("zip");
		
		return Response.ok().entity("Returning city: " + cityName + "-" + zip).build();
		
	}

}
