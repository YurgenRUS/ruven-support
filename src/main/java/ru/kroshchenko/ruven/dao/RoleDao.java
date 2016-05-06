package ru.kroshchenko.ruven.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kroshchenko.ruven.entities.Role;
import ru.kroshchenko.ruven.entities.RoleName;
import ru.kroshchenko.ruven.utils.ClassNameUtil;
import ru.kroshchenko.ruven.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kroshchenko
 *         2016.05.06
 */
public class RoleDao {

    static final Logger logger = LoggerFactory.getLogger(ClassNameUtil.getCurrentClassName());

    public static Role save(Role role) {
        try (Connection connection = DBConnection.getConnection()) {
            if (!checkExists(role)) {
                String query = "INSERT INTO role (name, description, comment) VALUES (?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, role.getName().name());
                statement.setString(2, role.getDescription());
                statement.setString(3, role.getComment());
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

    public static boolean checkExists(Role role) throws SQLException {
        String sql = "SELECT 1 AS exists_check FROM role WHERE name = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, role.getName().name());
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        }
    }

    public static Role get(RoleName roleName) {
        Role role = null;
        String sql = "SELECT * FROM role WHERE name = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, roleName.name());
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    role = new Role(rs);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return role;
    }

    public static Role get(Integer roleId) {
        Role role = null;
        String sql = "SELECT * FROM role WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, roleId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    role = new Role(rs);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return role;
    }

    public static List<Role> getAll() {
        ArrayList<Role> roles = new ArrayList<>();
        String sql = "SELECT * FROM role";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    roles.add(new Role(rs));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return roles;
    }
}
