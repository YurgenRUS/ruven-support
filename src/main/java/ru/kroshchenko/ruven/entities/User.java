package ru.kroshchenko.ruven.entities;

import ru.kroshchenko.ruven.dao.RoleDao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Kroshchenko
 *         2016.05.02
 */
public class User {

    private Integer id;
    private Integer employeeId;
    private Role role;
    private String login;
    private String password;

    public User() {
    }

    public User(Integer employeeId, Role role, String login, String password) {
        this.employeeId = employeeId;
        this.role = role;
        this.login = login;
        this.password = password;
    }

    public User(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.employeeId = rs.getInt("employee_id");
        this.role = RoleDao.get(rs.getInt("role_id"));
        this.login = rs.getString("login");
    }

    public Integer getId() {
        return id;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
