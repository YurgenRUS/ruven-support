package ru.kroshchenko.ruven.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kroshchenko.ruven.entities.TypicalStory;
import ru.kroshchenko.ruven.utils.ClassNameUtil;
import ru.kroshchenko.ruven.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kroshchenko
 *         2016.05.06
 */
public class TypicalStoryDao {

    static final Logger logger = LoggerFactory.getLogger(ClassNameUtil.getCurrentClassName());

    public static TypicalStory save(TypicalStory typicalStory) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "INSERT INTO typical_story (instructions, keys) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, typicalStory.getInstructions());
            statement.setString(2, typicalStory.getKeys());
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    return get(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static TypicalStory get(Integer typicalStoryId) {
        TypicalStory typicalStory = null;
        String sql = "SELECT * FROM typical_story WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, typicalStoryId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    typicalStory = new TypicalStory(rs);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return typicalStory;
    }

    public static List<TypicalStory> getAll() {
        ArrayList<TypicalStory> typicalStories = new ArrayList<>();
        String sql = "SELECT * FROM typical_story";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    typicalStories.add(new TypicalStory(rs));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return typicalStories;
    }
}
