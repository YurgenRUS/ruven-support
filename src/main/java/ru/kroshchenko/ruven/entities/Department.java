package ru.kroshchenko.ruven.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Kroshchenko
 *         2016.05.02
 */
public class Department {

    private Integer id;
    private String name;
    private String description;

    public Department() {

    }

    public Department(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Department(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.name = rs.getString("name");
        this.description = rs.getString("description");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
