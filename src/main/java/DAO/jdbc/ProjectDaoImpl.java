package DAO.jdbc;

import DAO.CompanysDAO;
import DAO.DevelopersDAO;
import DAO.ProjectsDAO;
import controller.Controller;
import exceptions.DbException;
import model.*;
import sql_utils.SqlParams;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Администратор on 27.01.2017.
 */
public class ProjectDaoImpl implements ProjectsDAO {
    private Connection conn;

    public ProjectDaoImpl() throws DbException {
        try {
            this.conn = DriverManager.getConnection(SqlParams.JDBC_POSTGRES_URL, SqlParams.POSTGRESS_USER_NAME, SqlParams.POSTGRESS_USER_PASSWORD);
        } catch (SQLException e) {
            throw new DbException("Can't create connection to the database  because of exception: " + e.getMessage(), e);
        }
    }

    @Override
    public Project add(Project obj) throws DbException {
        String sql = "INSERT INTO projects (project_name, cost, customer_id, company_id) VALUES (?, ? , ?, ?)";
        String sqlDevs = "INSERT INTO developer_projects (developer_id, project_id) VALUES (?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement stmtDevs = conn.prepareStatement(sqlDevs)) {

            stmt.setString(1, obj.getProjectName());
            stmt.setInt(2, obj.getCost());
            stmt.setInt(3, obj.getCustomer().getId());
            stmt.setInt(4, obj.getCompany().getId());
            stmt.execute();

            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            obj.setId(id);

            for (Developer developer : obj.getDevelopers()) {
                stmtDevs.setInt(1, developer.getId());
                stmtDevs.setInt(2, obj.getId());
                stmtDevs.execute();
            }

            return obj;

        } catch (SQLException e) {
            throw new DbException("Developer not created because of exception: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean delete(int id) throws DbException {
        String sql = "DELETE FROM projects WHERE id=?";
        String sqlDevs = "DELETE FROM developer_projects WHERE project_id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             PreparedStatement stmtDevs = conn.prepareStatement(sqlDevs)) {

            stmtDevs.setInt(1, id);
            stmtDevs.executeUpdate();
            stmt.setInt(1, id);
            int result = stmt.executeUpdate();

            return (result != 0);

        } catch (SQLException e) {
            throw new DbException("Project not deleted because of exception: " + e.getMessage(), e);
        }
    }

    @Override
    public Project update(Project obj) throws DbException {
        String sql = "UPDATE projects SET id=?, project_name=?, cost=?, customer_id=?, company_id=? WHERE id=?";
        String sqlDel = "DELETE FROM developer_projects WHERE project_id = ?";
        String sqlAddDev = "INSERT INTO developer_projects (developer_id, project_id) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             PreparedStatement stmtDel = conn.prepareStatement(sqlDel);
             PreparedStatement stmtAdd = conn.prepareStatement(sqlAddDev)) {

            stmt.setInt(1, obj.getId());
            stmt.setString(2, obj.getProjectName());
            stmt.setInt(3, obj.getCost());
            stmt.setInt(4, obj.getCustomer().getId());
            stmt.setInt(5, obj.getCompany().getId());
            stmt.setInt(6, obj.getId());
            int result = stmt.executeUpdate();

            stmtDel.setInt(1, obj.getId());
            stmtDel.executeUpdate();

            for (Developer developer : obj.getDevelopers()) {
                stmtAdd.setInt(1, developer.getId());
                stmtAdd.setInt(2, obj.getId());
                stmtAdd.execute();
            }

            if (result != 0) {
                return obj;
            } else {
                throw new DbException("This Project was not found in the database");
            }
        } catch (SQLException e) {
            throw new DbException("Developer not created because of exception: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Project> findByName(String name) throws DbException {
        String sql = "SELECT * FROM projects WHERE projects.project_name ILIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            return convertResultSetToObject(rs);
        } catch (SQLException e) {
            throw new DbException("Can't  make search  because of  exception : " + e.getMessage(), e);
        }
    }

    @Override
    public Project findById(int id) throws DbException {
        String sql = "SELECT * FROM projects WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            List<Project> projects = convertResultSetToObject(rs);
            if (projects.size() > 0) {
                return projects.get(0);
            } else {
                throw new DbException("Project with id: " + id + " doesn't exist");
            }
        } catch (SQLException e) {
            throw new DbException("Can't make search because of exception: " + e.getMessage(), e);
        }
    }

    private List<Project> convertResultSetToObject(ResultSet rs) throws SQLException {
        List<Project> projects = new ArrayList<>();
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("project_name");
                int cost = rs.getInt("cost");
                Customer customer = Controller.getCustomerDao().findById(rs.getInt("customer_id"));
                Company company = Controller.getCompanysDao().findById(rs.getInt("company_id"));
                Set<Developer> developers = getProjectDevelopers(id);
                Project project = new Project(id, name, cost, customer, company, developers);
                projects.add(project);
            }
        } catch (DbException e) {

        }
        return projects;
    }

    private Set<Developer> getProjectDevelopers(int projectID) throws DbException {
        HashSet<Developer> developers = new HashSet<>();
        String sql = "SELECT developers.id FROM developers INNER JOIN developer_projects ON developers.id = developer_projects.developer_id WHERE developer_projects.project_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, projectID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int developerID = rs.getInt("id");
                Developer developer = new DevelopersDaoImpl().findById(developerID);
                developers.add(developer);
            }

        } catch (SQLException e) {
            throw new DbException("Can't make search because of exception: " + e.getMessage(), e);
        }
        return developers;
    }
}

