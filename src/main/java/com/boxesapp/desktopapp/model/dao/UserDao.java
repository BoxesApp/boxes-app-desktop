package com.boxesapp.desktopapp.model.dao;

import com.boxesapp.desktopapp.model.User;

public interface UserDao extends Dao<User> {
    public User findByEmailAndPassword(String email, String password);
}
