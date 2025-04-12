package com.cesar.bd_project.dao;

import com.cesar.bd_project.model.ProductModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface GenericDao<T> {

    List<T> list() throws SQLException;

    ProductModel save(T t) throws SQLException;

    Optional<T> get(int id);

    void update(T t, int id);

    void delete(int id);

}