package com.wallace.desafio.util;

//import java.io.BufferedInputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Properties;
//import java.util.ResourceBundle;

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
		
//		Properties props = new Properties();
//		try {
//			File f = new File(
//					"/DesafioBackEndB2W/resources/config.properties"); 
//					InputStream is = new BufferedInputStream(new FileInputStream(f));
//			props.load(is);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		//Opens connection with database
//		this.mongoClient = MongoClients.create(props.getProperty("DB_CONNECTION"));
		
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
