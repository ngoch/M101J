package net.fina.myblog.repository;

import net.fina.myblog.domain.User;
import net.fina.myblog.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserMongoRepository implements UserRepository {

    @Autowired
    private DbService dbService;

    @Override
    public User validateLogin(String username) {
        return dbService.getDatastore().find(User.class).field("id").equal(username).limit(1).get();
    }
}
