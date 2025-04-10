package com.cesar.bd_project.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface GenericDao<T> {

    List<T> list() throws SQLException;

    void create(T t);

    Optional<T> get(int id);

    void update(T t, int id);

    void delete(int id);

}