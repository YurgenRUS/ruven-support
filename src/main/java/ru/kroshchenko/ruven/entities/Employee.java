package ru.kroshchenko.ruven.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Kroshchenko
 *         2016.05.02
 */
public class Employee {

    private Integer id;
    private Integer departmentId;
    private String fullName;
    private String phone;
    private String email;
    private Boolean vip;

    public Employee() {

    }

    public Employee(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.departmentId = rs.getInt("department_id");
        this.fullName = rs.getString("full_name");
        this.phone = rs.getString("phone");
        this.email = rs.getString("email");
        this.vip = rs.getBoolean("vip");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getVip() {
        return vip;
    }

    public void setVip(Boolean vip) {
        this.vip = vip;
    }
}
