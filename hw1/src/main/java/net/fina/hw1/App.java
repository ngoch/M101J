package net.fina.hw1;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.bson.Document;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        final Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(App.class, "/");

        Spark.get(new Route("/") {
            @Override
            public Object handle(Request request, Response response) {
                StringWriter sw = new StringWriter();
                try {
                    Map<String, String> dataMap = new HashMap<String, String>();
                    dataMap.put("test", "Submit Text");

                    Template template = configuration.getTemplate("hello.ftl");
                    template.process(dataMap, sw);
                } catch (Exception e) {
                    e.printStackTrace();
                    halt(500);
                }
                return sw;
            }
        });

        Spark.post(new Route("/data") {
            @Override
            public Object handle(Request request, Response response) {
                String text = request.queryParams("text");
                try (MongoClient mongoClient = new MongoClient()) {
                    MongoDatabase db = mongoClient.getDatabase("hw1_data");
                    MongoCollection<Document> collection = db.getCollection("S_TEXT");
                    if (text != null && (!text.isEmpty())) {
                        Document document = new Document();
                        document.put("text", text);
                        collection.insertOne(document);
                    }
                    StringWriter writer = new StringWriter();
                    FindIterable<Document> iterable = collection.find();
                    for (Document d : iterable) {
                        writer.append(d.toJson());
                        writer.append("</br>");
                    }
                    return writer;
                }
            }
        });
    }
}
