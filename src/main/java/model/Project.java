package model;

import java.util.Set;

public class Project {
    private int id;
    private String projectName;
    private int cost;
    private Customer customer;
    private Company company;
    private Set<Developer> developers;

    public Project(String projectName, int cost, Customer customer, Company company, Set<Developer> developers) {
        this.projectName = projectName;
        this.cost = cost;
        this.customer = customer;
        this.company = company;
        this.developers = developers;
    }

    public Project(int id, String projectName, int cost, Customer customer, Company company, Set<Developer> developers) {
        this(projectName, cost, customer, company, developers);
        this.id = id;
    }

    public void addDeveloper(Developer developer) {
        developers.add(developer);
    }

    public void delDeveloper(Developer developer) {
        developers.remove(developer);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (this.id == 0) {
            this.id = id;
        }
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(Set<Developer> developers) {
        this.developers = developers;
    }
}
