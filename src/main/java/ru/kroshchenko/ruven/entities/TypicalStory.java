package ru.kroshchenko.ruven.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Kroshchenko
 *         2016.05.02
 */
public class TypicalStory {

    private Integer id;
    private String instructions;
    private String keys;

    public TypicalStory() {

    }

    public TypicalStory(ResultSet rs) throws SQLException {
        id = rs.getInt("id");
        instructions = rs.getString("instructions");
        keys = rs.getString("keys");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }
}
