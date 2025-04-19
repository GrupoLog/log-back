package com.cesar.bd_project.dao;

import com.cesar.bd_project.model.ProductModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface GenericDao<T, ID> {

    List<T> list() throws SQLException;

    T save(T t) throws SQLException;

    T findById(ID id) throws SQLException;

    void update(T t) throws SQLException;

    void delete(ID id) throws SQLException;
}