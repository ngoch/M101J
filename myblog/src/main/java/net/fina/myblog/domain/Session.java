package net.fina.myblog.domain;

import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.EAN;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("sessions")
public class Session {

    @Id
    private ObjectId objectId;
    private String username;

    public ObjectId getObjectId() {
        return objectId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
