package mongoJava.mongoTest;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import java.util.Arrays;
import com.mongodb.Block;

import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.List;

public class mongoTest {
	
	public static void main( String[] args )
    {
		//used to connect to mongo atlas
		//MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017");
		//MongoClient mongoClient = new MongoClient(connectionString);
		
		MongoClient mongoClient = new MongoClient();
		MongoDatabase database = mongoClient.getDatabase("Assignment4");
		MongoCollection<Document> collection = database.getCollection("customers");
		
		//print all documents
		System.out.println("Original:");
		for (Document cur : collection.find()) {
		    System.out.println(cur.toJson());
		}
		
//		new_customer = {"name": {"first": "Josh", "last": "Horn"},
//	            "phone number": ["507-444-5555"],
//	            "address": [{"street": "129 Lively Rd",
//	                         "city": "Sackville",
//	                         "state": "NS",
//	                         "zip": "b9e4a4"}]
//	            }
		Document doc = new Document("name", new Document("first", "Josh").append("last", "Horn"))
						.append("phone number", Arrays.asList("507-444-5555"))
						.append("address", Arrays.asList(new Document("street", "129 Lively Rd")
																	.append("city", "Sackville")
																	.append("state", "NS")
																	.append("zip", "b9e4a4")));
		collection.insertOne(doc);
		//print all documents
		System.out.println("Inserted One:");
		for (Document cur : collection.find()) {
		    System.out.println(cur.toJson());
		}
		
		//updates
//		Document query = new Document("name.first", "Josh");
//		Document newValue = new Document("name.first", "The REAL Josh");
//		Document update = new Document("$set", newValue);
//		collection.updateOne(query, update);
		collection.updateOne(eq("name.first", "Josh"), new Document("$set", new Document("name.first", "The REAL Josh")));
		//print all documents
		System.out.println("Updated:");
		for (Document cur : collection.find()) {
		    System.out.println(cur.toJson());
		}
	//wow	
		//delete
		collection.deleteOne(eq("name.first", "The REAL Josh"));
		//print all documents
		System.out.println("Deleted One:");
		for (Document cur : collection.find()) {
		    System.out.println(cur.toJson());
		}
    }
}
