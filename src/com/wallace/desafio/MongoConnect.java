package com.wallace.desafio;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoConnect {
	
	private MongoClient mongoClient;
	private MongoDatabase database;
	private MongoCollection<Document> collection;
	
	public MongoCollection<Document> connectDB() {
		
		//Opens connection with database
		this.mongoClient = MongoClients.create("mongodb://localhost:27017");
		
		//Gets the collection
		this.database = mongoClient.getDatabase("starwars");
		this.collection = database.getCollection("planets");
		
		//Returns the collection
		return this.collection;
	}
	
	public void disconnectDB() {
		this.mongoClient.close();
	}

	public MongoClient getMongoClient() {
		return mongoClient;
	}

	public MongoDatabase getDatabase() {
		return database;
	}

	public MongoCollection<Document> getCollection() {
		return collection;
	}

}
