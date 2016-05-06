package ru.kroshchenko.ruven.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Kroshchenko
 *         2016.05.02
 */
public class Role {

    private Integer id;
    private RoleName name;
    private String description;
    private String comment;

    public Role() {

    }

    public Role(ResultSet rs) throws SQLException {
        id = rs.getInt("id");
        name = RoleName.valueOf(rs.getString("name"));
        description = rs.getString("description");
        comment = rs.getString("comment");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RoleName getName() {
        return name;
    }

    public void setName(RoleName name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
