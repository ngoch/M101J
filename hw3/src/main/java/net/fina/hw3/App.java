package net.fina.hw3;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import org.bson.Document;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        try (MongoClient mongoClient = new MongoClient()) {
            MongoDatabase db = mongoClient.getDatabase("school");
            MongoCollection<Document> collection = db.getCollection("students");
            MongoCollection<Document> outCollection = db.getCollection("students_02");
            List<Document> documents = collection
                    .find()
                    .sort(Sorts.ascending("scores.type"))
                    .into(new ArrayList<>());

            for (Document d : documents) {
                ArrayList<Document> scores = d.get("scores", ArrayList.class);

                Document homeworkDocument = null;
                ArrayList<Document> scoresList = new ArrayList<>();
                for (Document score : scores) {
                    if (score.getString("type").equals("homework")) {
                        if (homeworkDocument == null) {
                            homeworkDocument = score;
                        } else {
                            if (score.getDouble("score") > homeworkDocument.getDouble("score")) {
                                homeworkDocument = score;
                            }
                        }
                    } else {
                        scoresList.add(score);
                    }
                }
                scoresList.add(homeworkDocument);
                d.append("scores",scoresList);

//                outCollection.insertOne(d);
            }

        }
    }
}
