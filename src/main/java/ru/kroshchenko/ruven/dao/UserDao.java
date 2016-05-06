package ru.kroshchenko.ruven.dao;

import ru.kroshchenko.ruven.entities.User;
import ru.kroshchenko.ruven.utils.ClassNameUtil;
import ru.kroshchenko.ruven.utils.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kroshchenko
 *         2016.05.06
 */
public class UserDao {

    static final Logger logger = LoggerFactory.getLogger(ClassNameUtil.getCurrentClassName());

    public static User save(User user) {
        try (Connection connection = DBConnection.getConnection()) {
            if (!checkExists(user)) {
                String query = "INSERT INTO users (employee_id, role_id, login, password) VALUES (?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                if (user.getEmployeeId() != null) {
                    statement.setLong(1, user.getEmployeeId());
                } else {
                    statement.setNull(1, Types.INTEGER);
                }
                statement.setLong(2, user.getRole().getId());
                statement.setString(3, user.getLogin());
                statement.setString(4, user.getPassword());
                int affectedRows = statement.executeUpdate();
                if (affectedRows == 0) {
                    throw new RuntimeException("failed to insert user");
                }
                try (ResultSet rs = statement.getGeneratedKeys()) {
                    if (rs.next()) {
                        return get(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static boolean checkExists(User user) throws SQLException {
        String sql = "SELECT 1 AS exists_check FROM users WHERE login = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getLogin());
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        }
    }

    public static User get(Integer userId) {
        User user = null;
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    user = new User(rs);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return user;
    }

    public static List<User> getAll() {
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users as u";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    users.add(new User(rs));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return users;
    }
}
