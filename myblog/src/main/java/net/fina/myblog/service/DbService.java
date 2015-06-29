package net.fina.myblog.service;

import org.mongodb.morphia.Datastore;

public interface DbService {
    Datastore getDatastore();
}
