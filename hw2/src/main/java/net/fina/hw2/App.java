package net.fina.hw2;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.UpdateOptions;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.bson.Document;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        try (MongoClient mongoClient = new MongoClient()) {
            MongoDatabase db = mongoClient.getDatabase("students");
            MongoCollection<Document> collection = db.getCollection("grades");

            List<Document> documents = collection.find()
                    .sort(Sorts.ascending("student_id", "score"))
                    .into(new ArrayList<>());
            List<Document> deletedDocuments = new ArrayList<>();
            for (Document d : documents) {
                System.out.println(d);

                Document filter = new Document("type", "homework")
                        .append("student_id", d.get("student_id"));
                List<Document> tmp = collection.find(filter).sort(Sorts.ascending("score")).into(new ArrayList<>());
                if (tmp.size() > 1) {
                    Document changeDocument = tmp.iterator().next();
                    deletedDocuments.add(changeDocument);
                }
            }

            for (Document d : deletedDocuments) {
                //Remove extra documents
                //collection.deleteOne(d);
            }

        }
    }
}
