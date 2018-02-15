package com.example.mymongodb;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Java meets MongoDB
 *
 */
public class App {

    public static void main( String[] args ) {
	
	String MONGODB_HOST = "mongo.apps.josborne.com";
        int MONGODB_PORT = 443;

	MongoClientOptions.Builder builder = MongoClientOptions.builder();
	builder.sslEnabled(true).build();
	// If hostname or common name in certificate does not match hostname of 
	//system, the following code is required. 
	builder.sslInvalidHostNameAllowed(true).build();
	MongoClientOptions sslOptions = builder.build();
	// create mongo client with SSL option
	//MongoCredential.createMongoCRCredential(“user”, "tpfdf", "pw".toCharArray());
	//mongoClient = new MongoClient(myServer, credential, sslOptions); 
        
        try {

            /**
             *  Connect to MongoDB
             */
            MongoClient mongo = new MongoClient(new com.mongodb.ServerAddress(MONGODB_HOST, MONGODB_PORT), sslOptions);


            /**
             * Get DB
             *
             * If it doesn't exist, MongoDB will create it
             */
            MongoDatabase db = mongo.getDatabase("testDB");

            /**
             * Get Collection
             *
             * If it doesn't exist, MongoDB will create it
             */
            MongoCollection collection = db.getCollection("testColl");


            /**
             * Create documents
             */
            Document doc1 = new Document("name", "MongoDB")
                            .append("type", "database")
                            .append("count", 1)
                            .append("info", new Document("x", 203).append("y", 102));

            Document doc2 = new Document("name", "Cassandra")
                            .append("type", "database")
                            .append("count", 1)
                            .append("info", new Document("x", 123).append("y", 52));


            /**
             * Create List<Document>
             */
            List<Document> documents = new ArrayList<Document>();
            documents.add(doc1);
            documents.add(doc2);

            /**
             * Insert List<Document> to collection
             */
            collection.insertMany(documents);

            /**
             * Find All Documents in a Collection
             */
            MongoCursor<Document> cursor = collection.find().iterator();
            try {
                while (cursor.hasNext()) {
                    System.out.println(cursor.next().toJson());
                }
            } finally {
                cursor.close();
            }

            System.out.println("Done");


        } catch (MongoException e) {
            e.printStackTrace();
        }

    }
}
