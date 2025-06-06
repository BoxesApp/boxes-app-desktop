package com.boxesapp.desktopapp.model.dao;

import java.util.List;

public interface Dao <T> {
    public void create(T t);
    public void update(T t);
    public void delete(T t);
    public T findById(Integer id);
    public List<T> findAll();
}
