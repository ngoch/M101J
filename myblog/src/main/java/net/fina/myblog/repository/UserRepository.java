package net.fina.myblog.repository;

import net.fina.myblog.domain.User;

public interface UserRepository {

	User validateLogin(String username);

	User addUser(User user);
}
