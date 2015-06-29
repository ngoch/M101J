package net.fina.myblog.service;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.UnknownHostException;

@Service
public class MongoDbService implements DbService {

    private static final String DEFAULT_MONGO_URI = "mongodb://localhost";

    private MongoClient mongoClient;
    private Datastore datastore;

    @PostConstruct
    public void init() {
        try {
            mongoClient = new MongoClient(new MongoClientURI(DEFAULT_MONGO_URI));
            Morphia morphia = new Morphia();
            datastore = morphia.createDatastore(mongoClient, "blog");
            morphia.mapPackage("net.fina.myblog.domain");
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    @PreDestroy
    public void stop() {
        mongoClient.close();
    }

    @Override
    public Datastore getDatastore() {
        return this.datastore;
    }
}
