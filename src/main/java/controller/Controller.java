package controller;

import DAO.jdbc.*;
import exceptions.DbException;
import model.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Created by Администратор on 28.01.2017.
 */
public class Controller {

    static CompanysDaoImpl companysDao;
    static CustomerDaoImpl customerDao;
    static SkillsDaoImpl skillsDao;
    static ProjectDaoImpl projectDao;
    static DevelopersDaoImpl developersDao;

    static {
        try {
            companysDao = new CompanysDaoImpl();
            customerDao = new CustomerDaoImpl();
            skillsDao = new SkillsDaoImpl();
            projectDao = new ProjectDaoImpl();
            developersDao = new DevelopersDaoImpl();
        } catch (DbException e) {

        }

    }

    public Company addCompany(String companyName) throws DbException {
        Company company = new Company(companyName);
        try {
            return companysDao.add(company);
        } catch (DbException e) {
            throw e;
        }
    }

    public boolean deleteCompany(Company company) throws DbException {
        try {
            return companysDao.delete(company.getId());
        } catch (DbException e) {
            throw e;
        }
    }

    public Company updateCompany(Company company) throws DbException {
        try {
            return companysDao.update(company);
        } catch (DbException e) {
            throw e;
        }
    }

    public List<Company> findCompanyByName(String companyName) throws DbException {
        try {
            return companysDao.findByName(companyName);
        } catch (DbException e) {
            throw e;
        }
    }

    public Company findCompanyById(int id) throws DbException {
        try {
            return companysDao.findById(id);
        } catch (DbException e) {
            throw e;
        }
    }

    public Customer addCustomer(String customerName) throws DbException {
        Customer customer = new Customer(customerName);
        try {
            return customerDao.add(customer);
        } catch (DbException e) {
            throw e;
        }
    }

    public boolean deleteCustomer(Customer customer) throws DbException {
        try {
            return customerDao.delete(customer.getId());
        } catch (DbException e) {
            throw e;
        }
    }

    public Customer updateCustomer(Customer customer) throws DbException {
        try {
            return customerDao.update(customer);
        } catch (DbException e) {
            throw e;
        }
    }

    public List<Customer> findCustomerByName(String customerName) throws DbException {
        try {
            return customerDao.findByName(customerName);
        } catch (DbException e) {
            throw e;
        }
    }

    public Customer findCustomerById(int id) throws DbException {
        try {
            return customerDao.findById(id);
        } catch (DbException e) {
            throw e;
        }
    }

    public Skill addSkill(String Name) throws DbException {
        Skill skill = new Skill(Name);
        try {
            return skillsDao.add(skill);
        } catch (DbException e) {
            throw e;
        }
    }

    public boolean deleteSkill(Skill skill) throws DbException {
        try {
            return skillsDao.delete(skill.getId());
        } catch (DbException e) {
            throw e;
        }
    }

    public Skill updateSkill(Skill skill) throws DbException {
        try {
            return skillsDao.update(skill);
        } catch (DbException e) {
            throw e;
        }
    }

    public List<Skill> findSkillByName(String skillName) throws DbException {
        try {
            return skillsDao.findByName(skillName);
        } catch (DbException e) {
            throw e;
        }
    }

    public Skill findSkillById(int id) throws DbException {
        try {
            return skillsDao.findById(id);
        } catch (DbException e) {
            throw e;
        }
    }

    public Developer addDeveloper(String firstName, String lastName, LocalDate birthDate, String address, int salary, Company company, Set<Skill> skills) throws DbException {
        Developer obj = new Developer(firstName, lastName, birthDate, address, salary, company, skills);
        try {
            return developersDao.add(obj);
        } catch (DbException e) {
            throw e;
        }
    }

    public boolean deleteDeveloper(Developer obj) throws DbException {
        try {
            return developersDao.delete(obj.getId());
        } catch (DbException e) {
            throw e;
        }
    }

    public Developer updateDeveloper(Developer obj) throws DbException {
        try {
            return developersDao.update(obj);
        } catch (DbException e) {
            throw e;
        }
    }

    public List<Developer> findDeveloperByName(String name) throws DbException {
        try {
            return developersDao.findByName(name);
        } catch (DbException e) {
            throw e;
        }
    }

    public Developer findDeveloperById(int id) throws DbException {
        try {
            return developersDao.findById(id);
        } catch (DbException e) {
            throw e;
        }
    }

    public Project addProject(String projectName, int cost, Customer customer, Company company, Set<Developer> developers) throws DbException {
        Project obj = new Project(projectName, cost, customer, company, developers);
        try {
            return projectDao.add(obj);
        } catch (DbException e) {
            throw e;
        }
    }

    public boolean deleteProject(Project obj) throws DbException {
        try {
            return projectDao.delete(obj.getId());
        } catch (DbException e) {
            throw e;
        }
    }

    public Project updateProject(Project obj) throws DbException {
        try {
            return projectDao.update(obj);
        } catch (DbException e) {
            throw e;
        }
    }

    public List<Project> findProjectByName(String name) throws DbException {
        try {
            return projectDao.findByName(name);
        } catch (DbException e) {
            throw e;
        }
    }

    public Project findProjectById(int id) throws DbException {
        try {
            return projectDao.findById(id);
        } catch (DbException e) {
            throw e;
        }
    }

    public static CompanysDaoImpl getCompanysDao() {
        return companysDao;
    }

    public static CustomerDaoImpl getCustomerDao() {
        return customerDao;
    }

    public static SkillsDaoImpl getSkillsDao() {
        return skillsDao;
    }

    public static ProjectDaoImpl getProjectDao() {
        return projectDao;
    }

    public static DevelopersDaoImpl getDevelopersDao() {
        return developersDao;
    }
}
