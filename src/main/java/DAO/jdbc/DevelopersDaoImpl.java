package DAO.jdbc;

import DAO.DAO;
import DAO.DevelopersDAO;
import DAO.SkillsDAO;
import exceptions.DbException;
import model.Company;
import model.Developer;
import model.Skill;
import sql_utils.SqlParams;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DevelopersDaoImpl implements DevelopersDAO {
    private Connection conn;

    public DevelopersDaoImpl() throws DbException {
        try {
            this.conn = DriverManager.getConnection(SqlParams.JDBC_POSTGRES_URL, SqlParams.POSTGRESS_USER_NAME, SqlParams.POSTGRESS_USER_PASSWORD);
        } catch (SQLException e) {
            throw new DbException("Can't create connection to the database  because of exception: " + e.getMessage(), e);
        }
    }

    @Override
    public Developer add(Developer obj) throws DbException {
        String sql = "INSERT INTO developers (first_name, last_name, birth_date, address, salary, company_id) VALUES (?, ? , ?, ?, ?, ?)";
        String sqlSkills = "INSERT INTO developer_skills (developer_id, skill_id) VALUES (?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement stmtSkills = conn.prepareStatement(sqlSkills)) {

            stmt.setString(1, obj.getFirstName());
            stmt.setString(2, obj.getLastName());
            stmt.setDate(3, Date.valueOf(obj.getBirthDate()));
            stmt.setString(4, obj.getAddress());
            stmt.setInt(5, obj.getSalary());
            stmt.setInt(6, obj.getCompany().getId());
            stmt.execute();

            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            obj.setId(id);

            for (Skill skill : obj.getSkills()) {
                stmtSkills.setInt(1, obj.getId());
                stmtSkills.setInt(2, skill.getId());
                stmtSkills.execute();
            }

            return obj;

        } catch (SQLException e) {
            throw new DbException("Developer not created because of exception: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean delete(int id) throws DbException {
        String sql = "DELETE FROM developers WHERE id=?";
        String sqlSkills = "DELETE FROM developer_skills WHERE developer_id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             PreparedStatement stmtSkill = conn.prepareStatement(sqlSkills)) {

            stmtSkill.setInt(1, id);
            stmtSkill.executeUpdate();
            stmt.setInt(1, id);
            int result = stmt.executeUpdate();

            return (result != 0);

        } catch (SQLException e) {
            throw new DbException("Developer not deleted because of exception: " + e.getMessage(), e);
        }
    }

    @Override
    public Developer update(Developer obj) throws DbException {
        String sql = "UPDATE developers SET id=?, first_name=?, last_name=?, birth_date=?, address=?, salary=?, company_id=? WHERE id=?";
        String sqlDel = "DELETE FROM developer_skills WHERE developer_id = ?";
        String sqlAddSkills = "INSERT INTO developer_skills (developer_id, skill_id) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             PreparedStatement stmtDel = conn.prepareStatement(sqlDel);
             PreparedStatement stmtAdd = conn.prepareStatement(sqlAddSkills)) {

            stmt.setInt(1, obj.getId());
            stmt.setString(2, obj.getFirstName());
            stmt.setString(3, obj.getLastName());
            stmt.setDate(4, Date.valueOf(obj.getBirthDate()));
            stmt.setString(5, obj.getAddress());
            stmt.setInt(6, obj.getSalary());
            stmt.setInt(7, obj.getCompany().getId());
            stmt.setInt(8, obj.getId());
            int result = stmt.executeUpdate();

            stmtDel.setInt(1, obj.getId());
            stmtDel.executeUpdate();

            for (Skill skill : obj.getSkills()) {
                stmtAdd.setInt(1, obj.getId());
                stmtAdd.setInt(2, skill.getId());
                stmtAdd.execute();
            }

            if (result != 0) {
                return obj;
            } else {
                throw new DbException("This Developer was not found in the database");
            }
        } catch (SQLException e) {
            throw new DbException("Developer not created because of exception: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Developer> findByName(String name) throws DbException {
        String sql = "SELECT * FROM developers WHERE first_name ILIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            return convertResultSetToObject(rs);
        } catch (SQLException e) {
            throw new DbException("Can't make search  because of  exception : " + e.getMessage(), e);
        }
    }

    @Override
    public Developer findById(int id) throws DbException {
        String sql = "SELECT * FROM developers WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            List<Developer> developers = convertResultSetToObject(rs);
            if (developers.size() > 0) {
                return developers.get(0);
            } else {
                throw new DbException("Developer with id: " + id + "doesn't exist");
            }
        } catch (SQLException e) {
            throw new DbException("Can't make search because of exception: " + e.getMessage(), e);
        }
    }

    private List<Developer> convertResultSetToObject(ResultSet rs) throws SQLException, DbException {
        List<Developer> developers = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String first_name = rs.getString("first_name");
            String last_name = rs.getString("last_name");
            Date birth_date = rs.getDate("birth_date");
            String address = rs.getString("address");
            int salary = rs.getInt("salary");
            int company_id = rs.getInt("company_id");
            Set<Skill> skills = getDeveloperSkills(id);
            Company company = new CompanysDaoImpl().findById(company_id);

            Developer developer = new Developer(id, first_name, last_name, birth_date.toLocalDate(), address, salary, company, skills);
            developers.add(developer);
        }
        return developers;
    }

    private Set<Skill> getDeveloperSkills(int developerID) throws DbException {
        Set<Skill> skills = new HashSet<>();
        String sql = "SELECT skills.id, skills.skill_name FROM skills INNER JOIN developer_skills ON skills.id = developer_skills.skill_id WHERE developer_skills.developer_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, developerID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String skill_name = rs.getString("skill_name");
                skills.add(new Skill(id, skill_name));
            }
        } catch (SQLException e) {
            throw new DbException("Can't make search because of exception: " + e.getMessage(), e);
        }
        return skills;
    }

}

