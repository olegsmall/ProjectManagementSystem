import DAO.DAO;
import DAO.jdbc.*;
import controller.Controller;
import model.*;
import exceptions.DbException;

import java.sql.SQLException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws SQLException, DbException {

        Controller controller = new Controller();

        // Компании
        String companyName = "Компания_1";
        System.out.println("Добавляем новую компанию: " + companyName);
        Company company = controller.addCompany(companyName);
        int companyID = company.getId();
        companyName = company.getCompanyName();
        System.out.println("В базу данных была добавлена компания " + companyName + " c id " + companyID);

        System.out.println("------------------------------------------------------");
        System.out.println("Выполняем поиск компании по имени " + companyName);
        List<Company> companys = controller.findCompanyByName(companyName);
        for (Company company1 : companys) {
            System.out.println("Компания " + company1.getCompanyName() + " с id " + company1.getId());
        }

        System.out.println("------------------------------------------------------");
        System.out.println("Выполняем поиск компании по id " + companyID);
        company = controller.findCompanyById(companyID);
        System.out.println("Найдена компания " + company.getCompanyName() + " с id " + company.getId());

        System.out.println("------------------------------------------------------");
        String newCompanyName = "Компания_Новая";
        System.out.println("Выполняем обновление имени компании с " + company.getCompanyName() + " на " + newCompanyName);
        company.setCompanyName(newCompanyName);
        controller.updateCompany(company);
        company = controller.findCompanyById(companyID);
        System.out.println("Обновлена компания " + company.getCompanyName() + " с id " + company.getId());

        System.out.println("------------------------------------------------------");
        System.out.println("Выполняем удаление компании " + company.getCompanyName() + " с id " + company.getId());
        boolean resultat = controller.deleteCompany(company);
        if (resultat) {
            System.out.println("Компания " + company.getCompanyName() + " с id " + company.getId() + " удалена успешно");
        } else {
            System.out.println("Компания " + company.getCompanyName() + " с id " + company.getId() + " не удалена");
        }

        // Кастомеры
        String castomerName = "Кастомер_1";
        System.out.println("Добавляем новую кастомер: " + castomerName);
        Customer castomer = controller.addCustomer(castomerName);
        int castomerID = castomer.getId();
        castomerName = castomer.getCustomerName();
        System.out.println("В базу данных была добавлена кастомер " + castomerName + " c id " + castomerID);

        System.out.println("------------------------------------------------------");
        System.out.println("Выполняем поиск кастомер по имени " + castomerName);
        List<Customer> castomers = controller.findCustomerByName(castomerName);
        for (Customer castomer1 : castomers) {
            System.out.println("Кастомер " + castomer1.getCustomerName() + " с id " + castomer1.getId());
        }

        System.out.println("------------------------------------------------------");
        System.out.println("Выполняем поиск Кастомер по id " + castomerID);
        castomer = controller.findCustomerById(castomerID);
        System.out.println("Найдена кастомер " + castomer.getCustomerName() + " с id " + castomer.getId());

        System.out.println("------------------------------------------------------");
        String newCastomerName = "Кастомер_новый";
        System.out.println("Выполняем обновление имени кастомера с " + castomer.getCustomerName() + " на " + newCastomerName);
        castomer.setCustomerName(newCastomerName);
        controller.updateCustomer(castomer);
        castomer = controller.findCustomerById(castomerID);
        System.out.println("Обновлена кастомер " + castomer.getCustomerName() + " с id " + castomer.getId());

        System.out.println("------------------------------------------------------");
        System.out.println("Выполняем удаление кастомера " + castomer.getCustomerName() + " с id " + castomer.getId());
        boolean resultCustomerDelete = controller.deleteCustomer(castomer);
        if (resultCustomerDelete) {
            System.out.println("кастомер " + castomer.getCustomerName() + " с id " + castomer.getId() + " удалена успешно");
        } else {
            System.out.println("кастомер " + castomer.getCustomerName() + " с id " + castomer.getId() + " не удалена");
        }

        // Skills
        String skillName = "skill_1";
        System.out.println("Добавляем новую skill: " + skillName);
        Skill skill = controller.addSkill(skillName);
        int skillId = skill.getId();
        skillName = skill.getSkillName();
        System.out.println("В базу данных была добавлена skill " + skillName + " c id " + skillId);

        System.out.println("------------------------------------------------------");
        System.out.println("Выполняем поиск skill по имени " + skillName);
        List<Skill> skills = controller.findSkillByName(skillName);
        for (Skill skill1 : skills) {
            System.out.println("skill " + skill1.getSkillName() + " с id " + skill1.getId());
        }

        System.out.println("------------------------------------------------------");
        System.out.println("Выполняем поиск skill по id " + skillId);
        skill = controller.findSkillById(skillId);
        System.out.println("Найдена skill " + skill.getSkillName() + " с id " + skill.getId());

        System.out.println("------------------------------------------------------");
        String newSkillName = "skill_новый";
        System.out.println("Выполняем обновление имени skill с " + skill.getSkillName() + " на " + newSkillName);
        skill.setSkillName(newSkillName);
        controller.updateSkill(skill);
        skill = controller.findSkillById(skillId);
        System.out.println("Обновлена skill " + skill.getSkillName() + " с id " + skill.getId());

        System.out.println("------------------------------------------------------");
        System.out.println("Выполняем удаление skill " + skill.getSkillName() + " с id " + skill.getId());
        boolean resultSkill = controller.deleteSkill(skill);
        if (resultSkill) {
            System.out.println("skill " + skill.getSkillName() + " с id " + skill.getId() + " удалена успешно");
        } else {
            System.out.println("skill " + skill.getSkillName() + " с id " + skill.getId() + " не удалена");
        }

        // Developers
        String developerFirstName = "developerFirstNa_5";
        String developerLastName = "lastName_1";
        Date developerBirthDate = new Date();
        String developerAdress = "developer_address_1";
        int salary = 5000;
        Company developerCompany = controller.findCompanyById(3);
        Set<Skill> developerSkills = new HashSet<>();
        developerSkills.add(controller.findSkillById(1));
        developerSkills.add(controller.findSkillById(6));
        System.out.println("Добавляем новую developer: " + developerFirstName + " " + developerLastName);
        Developer developer = controller.addDeveloper(developerFirstName, developerLastName, new java.sql.Date(developerBirthDate.getTime()).toLocalDate(),
                developerAdress, salary, developerCompany, developerSkills);
        int developerId = developer.getId();
        developerFirstName = developer.getFirstName();
        developerLastName = developer.getLastName();
        System.out.println("В базу данных была добавлена developer " + developerFirstName + " " + developerLastName + " c id " + skillId);

        System.out.println("------------------------------------------------------");
        System.out.println("Выполняем поиск developer по имени " + developerFirstName);
        List<Developer> developers = controller.findDeveloperByName(developerFirstName);
        for (Developer developer1 : developers) {
            System.out.println("skill " + developer1.getFirstName() + " " + developerLastName + " с id " + developer1.getId());
        }

        System.out.println("------------------------------------------------------");
        System.out.println("Выполняем поиск developer по id " + developerId);
        developer = controller.findDeveloperById(developerId);
        System.out.println("Найдена developer " + developer.getFirstName() + " " + developerLastName + " с id " + developer.getId());

        System.out.println("------------------------------------------------------");
        String newDeveloperName = "developer_новый";
        System.out.println("Выполняем обновление имени developer с " + developer.getFirstName() + " на " + newDeveloperName);
        developer.setFirstName(newDeveloperName);
        controller.updateDeveloper(developer);
        developer = controller.findDeveloperById(developerId);
        System.out.println("Обновлена developer " + developer.getFirstName() + " " + developerLastName + " с id " + developer.getId());

        System.out.println("------------------------------------------------------");
        System.out.println("Выполняем удаление developer " + developer.getFirstName() + " " + developerLastName + " с id " + developer.getId());
        boolean resultDeveloper = controller.deleteDeveloper(developer);
        if (resultDeveloper) {
            System.out.println("developer " + developer.getFirstName() + " " + developerLastName + " с id " + developer.getId() + " удалена успешно");
        } else {
            System.out.println("developer " + developer.getFirstName() + " " + developerLastName + " с id " + developer.getId() + " не удалена");
        }

        // Projects
        String projectName = "Project_3";
        int projectCost = 100000;
        Customer projectCustomer = controller.findCustomerById(2);
        Company projectCompany = controller.findCompanyById(2);
        Set<Developer> projectDevelopers = new HashSet<>();
        projectDevelopers.add(controller.findDeveloperById(1));
        projectDevelopers.add(controller.findDeveloperById(2));
        projectDevelopers.add(controller.findDeveloperById(3));
        projectDevelopers.add(controller.findDeveloperById(4));
        projectDevelopers.add(controller.findDeveloperById(5));
        System.out.println("Добавляем новый Project: " + projectName + " владелец " + projectCustomer + " исполнитель " + projectCompany);
        Project project = controller.addProject(projectName, projectCost, projectCustomer, projectCompany, projectDevelopers);
        int projectId = project.getId();
        projectName = project.getProjectName();
        System.out.println("В базу данных был добавлен Project " + projectName + " c id " + projectId);

        System.out.println("------------------------------------------------------");
        System.out.println("Выполняем поиск Project по имени " + projectName);
        List<Project> projects = controller.findProjectByName(projectName);
        for (Project project1 : projects) {
            System.out.println("Найден Project " + project1.getProjectName() + " с id " + project1.getId());
        }

        System.out.println("------------------------------------------------------");
        System.out.println("Выполняем поиск Project по id " + projectId);
        project = controller.findProjectById(projectId);
        System.out.println("Найден Project " + project.getProjectName() + " с id " + project.getId());

        System.out.println("------------------------------------------------------");
        String newProjectName = "Project_новый";
        System.out.println("Выполняем обновление имени Project с " + project.getProjectName() + " на " + newProjectName);
        project.setProjectName(newProjectName);
        controller.updateProject(project);
        project = controller.findProjectById(projectId);
        System.out.println("Обновлен project " + project.getProjectName() + " с id " + project.getId());

        System.out.println("------------------------------------------------------");
        System.out.println("Выполняем удаление Project " + project.getProjectName() + " " + " с id " + project.getId());
        boolean resultProject = controller.deleteProject(project);
        if (resultProject) {
            System.out.println("Project " + project.getProjectName() + " с id " + project.getId() + " удален успешно");
        } else {
            System.out.println("Project " + project.getProjectName() + " с id " + project.getId() + " не удален");
        }
    }
}
