package com.phoenix.user;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface UserDAO {
    void insertUser(User user);

    void updateUser(User user);

    Optional<User> getUserById(BigInteger userId);
}
