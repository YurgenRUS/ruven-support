package ru.kroshchenko.ruven.controllers.rest.resources;

import org.apache.commons.lang3.StringUtils;
import ru.kroshchenko.ruven.dao.EmployeeDao;
import ru.kroshchenko.ruven.dao.RoleDao;
import ru.kroshchenko.ruven.entities.Employee;
import ru.kroshchenko.ruven.entities.User;

/**
 * @author Aleksandr Streltsov
 *         2016.05.06
 */
public class UserResource implements TableDataResource {

    private String name;
    private String role;
    private String login;

    public UserResource() {

    }

    public UserResource(User user) {
        Employee employee = EmployeeDao.get(user.getEmployeeId());
        if (employee != null) {
            name = employee.getFullName();
        }
        login = user.getLogin();
        role = user.getRole().getName().name();
    }

    public UserResource(String name, String role, String login) {
        this.name = name;
        this.role = role;
        this.login = login;
    }

    public boolean contains(String searchValue) {
        return searchValue == null
                || StringUtils.containsIgnoreCase(name, searchValue)
                || StringUtils.containsIgnoreCase(role, searchValue)
                || StringUtils.containsIgnoreCase(login, searchValue);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
