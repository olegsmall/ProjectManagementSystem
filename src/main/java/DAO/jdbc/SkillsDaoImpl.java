package DAO.jdbc;

import DAO.CustomersDAO;
import DAO.SkillsDAO;
import exceptions.DbException;
import model.Customer;
import model.Skill;
import sql_utils.SqlParams;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Администратор on 27.01.2017.
 */
public class SkillsDaoImpl implements SkillsDAO {
    private Connection conn;

    public SkillsDaoImpl() throws DbException {
        try {
            this.conn = DriverManager.getConnection(SqlParams.JDBC_POSTGRES_URL, SqlParams.POSTGRESS_USER_NAME, SqlParams.POSTGRESS_USER_PASSWORD);
        } catch (SQLException e) {
            throw new DbException("Can't create connection to the database  because of exception: " + e.getMessage(), e);
        }
    }

    @Override
    public Skill add(Skill obj) throws DbException {
        String sql = "INSERT INTO skills (skill_name) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, obj.getSkillName());
            stmt.execute();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            obj.setId(id);
            return obj;

        } catch (SQLException e) {
            throw new DbException("Skill not created because of exception: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean delete(int id) throws DbException {
        String sql = "DELETE FROM skills WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int result = stmt.executeUpdate();
            return (result != 0);

        } catch (SQLException e) {
            throw new DbException("Skill not deleted because of exception: " + e.getMessage(), e);
        }
    }

    @Override
    public Skill update(Skill obj) throws DbException {
        String sql = "UPDATE skills SET skill_name=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, obj.getSkillName());
            stmt.setInt(2, obj.getId());
            int result = stmt.executeUpdate();
            if (result != 0) {
                return obj;
            } else {
                throw new DbException("This Skill was not found in the database");
            }
        } catch (SQLException e) {
            throw new DbException("Skill not created because of exception: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Skill> findByName(String name) throws DbException {
        String sql = "SELECT id, skill_name FROM skills WHERE skill_name ILIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            return convertResultSetToObject(rs);
        } catch (SQLException e) {
            throw new DbException("Can't make search because of  exception : " + e.getMessage(), e);
        }
    }

    @Override
    public Skill findById(int id) throws DbException {
        String sql = "SELECT id, skill_name FROM skills WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            List<Skill> skills = convertResultSetToObject(rs);
            if (skills.size() > 0) {
                return skills.get(0);
            } else {
                throw new DbException("Skill with id: " + id + "doesn't exist");
            }
        } catch (SQLException e) {
            throw new DbException("Can't make search because of exception: " + e.getMessage(), e);
        }
    }

    private List<Skill> convertResultSetToObject(ResultSet rs) throws SQLException {
        List<Skill> skills = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("skill_name");
            Skill skill = new Skill(id, name);
            skills.add(skill);
        }
        return skills;
    }
}

