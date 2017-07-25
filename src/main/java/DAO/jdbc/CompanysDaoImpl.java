package DAO.jdbc;

import DAO.CompanysDAO;
import exceptions.DbException;
import model.Company;
import sql_utils.SqlParams;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Администратор on 27.01.2017.
 */
public class CompanysDaoImpl implements CompanysDAO {
    private Connection conn;

    public CompanysDaoImpl() throws DbException {
        try {
            this.conn = DriverManager.getConnection(SqlParams.JDBC_POSTGRES_URL, SqlParams.POSTGRESS_USER_NAME, SqlParams.POSTGRESS_USER_PASSWORD);
        } catch (SQLException e) {
            throw new DbException("Can't create connection to the database  because of exception: " + e.getMessage(), e);
        }
    }

    @Override
    public Company add(Company obj) throws DbException {
        String sql = "INSERT INTO companies (company_name) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, obj.getCompanyName());
            stmt.execute();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            obj.setId(id);
            return obj;

        } catch (SQLException e) {
            throw new DbException("Company not created because of exception: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean delete(int id) throws DbException {
        String sql = "DELETE FROM companies WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int result = stmt.executeUpdate();
            return (result != 0);

        } catch (SQLException e) {
            throw new DbException("Company not deleted because of exception: " + e.getMessage(), e);
        }
    }

    @Override
    public Company update(Company obj) throws DbException {
        String sql = "UPDATE companies SET company_name=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, obj.getCompanyName());
            stmt.setInt(2, obj.getId());
            int result = stmt.executeUpdate();
            if (result != 0) {
                return obj;
            } else {
                throw new DbException("This company was not found in the database");
            }
        } catch (SQLException e) {
            throw new DbException("Company not created because of exception: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Company> findByName(String name) throws DbException {
        String sql = "SELECT * FROM companies WHERE company_name ILIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            return convertResultSetToObject(rs);
        } catch (SQLException e) {
            throw new DbException("Can't make search because of exception: " + e.getMessage(), e);
        }
    }

    @Override
    public Company findById(int id) throws DbException {
        String sql = "SELECT * FROM companies WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            List<Company> companies = convertResultSetToObject(rs);
            if (companies.size() > 0) {
                return companies.get(0);
            } else {
                throw new DbException("Company with id: " + id + "doesn't exist");
            }
        } catch (SQLException e) {
            throw new DbException("Can't make search because of exception: " + e.getMessage(), e);
        }
    }

    private List<Company> convertResultSetToObject(ResultSet rs) throws SQLException {
        List<Company> companies = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String companyName = rs.getString("company_name");
            Company company = new Company(id, companyName);
            companies.add(company);
        }
        return companies;
    }
}

