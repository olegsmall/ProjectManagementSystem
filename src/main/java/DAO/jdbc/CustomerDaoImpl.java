package DAO.jdbc;

import DAO.CompanysDAO;
import DAO.CustomersDAO;
import exceptions.DbException;
import model.Company;
import model.Customer;
import sql_utils.SqlParams;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Администратор on 27.01.2017.
 */
public class CustomerDaoImpl implements CustomersDAO {
    private Connection conn;

    public CustomerDaoImpl() throws DbException {
        try {
            this.conn = DriverManager.getConnection(SqlParams.JDBC_POSTGRES_URL, SqlParams.POSTGRESS_USER_NAME, SqlParams.POSTGRESS_USER_PASSWORD);
        } catch (SQLException e) {
            throw new DbException("Can't create connection to the database  because of exception: " + e.getMessage(), e);
        }
    }

    @Override
    public Customer add(Customer obj) throws DbException {
        String sql = "INSERT INTO customers (customer_name) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, obj.getCustomerName());
            stmt.execute();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            obj.setId(id);
            return obj;

        } catch (SQLException e) {
            throw new DbException("Customer not created because of exception: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean delete(int id) throws DbException {
        String sql = "DELETE FROM customers WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int result = stmt.executeUpdate();
            return (result != 0);

        } catch (SQLException e) {
            throw new DbException("Customer not deleted because of exception: " + e.getMessage(), e);
        }
    }

    @Override
    public Customer update(Customer obj) throws DbException {
        String sql = "UPDATE customers SET customer_name=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, obj.getCustomerName());
            stmt.setInt(2, obj.getId());
            int result = stmt.executeUpdate();
            if (result != 0) {
                return obj;
            } else {
                throw new DbException("This customer was not found in the database");
            }
        } catch (SQLException e) {
            throw new DbException("Customer not created because of exception: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Customer> findByName(String name) throws DbException {
        String sql = "SELECT id, customer_name FROM customers WHERE customer_name ILIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            return convertResultSetToObject(rs);
        } catch (SQLException e) {
            throw new DbException("Can't make search because of exception : " + e.getMessage(), e);
        }
    }

    @Override
    public Customer findById(int id) throws DbException {
        String sql = "SELECT id, customer_name FROM customers WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            List<Customer> customers = convertResultSetToObject(rs);
            if (customers.size() > 0) {
                return customers.get(0);
            } else {
                throw new DbException("Customer with id: " + id + "doesn't exist");
            }
        } catch (SQLException e) {
            throw new DbException("Can't make search because of exception: " + e.getMessage(), e);
        }
    }

    private List<Customer> convertResultSetToObject(ResultSet rs) throws SQLException {
        List<Customer> customers = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("customer_name");
            Customer customer = new Customer(id, name);
            customers.add(customer);
        }
        return customers;
    }
}

