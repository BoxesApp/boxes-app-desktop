package com.boxesapp.desktopapp.model.repository;

import com.boxesapp.desktopapp.model.User;
import com.boxesapp.desktopapp.model.dao.UserDao;

import java.util.List;

public class UserRepository implements UserDao {

    @Override
    public void create(User user) {

    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(User user) {

    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }
}
