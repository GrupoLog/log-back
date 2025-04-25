package com.cesar.bd_project.dao;

import java.util.List;

public interface GenericDao<T, ID> {

    List<T> list();

    T findById(ID id);

    void save(T t);

    void update(T t);

    void delete(ID id);
}