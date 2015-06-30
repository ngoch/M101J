package net.fina.myblog.repository;

import net.fina.myblog.domain.User;
import net.fina.myblog.service.DbService;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

	@Override
	public User addUser(User user) {
		try {
			makePasswordHash(user);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		dbService.getDatastore().save(user);
		return user;
	}

	private void makePasswordHash(User user) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		messageDigest.update(user.getPassword().getBytes(), 0, user.getPassword().length());
		String hashedPass = new BigInteger(1, messageDigest.digest()).toString(16);
		if (hashedPass.length() < 32) {
			hashedPass = "0" + hashedPass;
		}
		user.setPassword(hashedPass);
	}
}
