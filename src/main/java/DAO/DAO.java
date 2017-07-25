package DAO;

import exceptions.DbException;
import model.Company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {

    T add(T obj) throws DbException;

    boolean delete(int id) throws DbException;

    T update(T obj) throws DbException;

    List<T> findByName(String name) throws DbException;

    T findById(int id) throws DbException;

}
