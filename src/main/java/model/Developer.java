package model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class Developer {
    private int id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String address;
    private int salary;
    private Company company;
    private Set<Skill> skills;

    public Developer(String firstName, String lastName, LocalDate birthDate, String address, int salary, Company company, Set<Skill> skills) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.address = address;
        this.salary = salary;
        this.company = company;
        this.skills = skills;
    }

    public Developer(int id, String firstName, String lastName, LocalDate birthDate, String address, int salary, Company company, Set<Skill> skills) {
        this(firstName, lastName, birthDate, address, salary, company, skills);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (this.id == 0) {
            this.id = id;
        }
    }

    public void addSkill(Skill skill) {
        skills.add(skill);
    }

    public void delSkill(Skill skill) {
        skills.remove(skill);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }
}
