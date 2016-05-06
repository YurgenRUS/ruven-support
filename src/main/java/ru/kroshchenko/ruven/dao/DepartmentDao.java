package ru.kroshchenko.ruven.dao;

import ru.kroshchenko.ruven.entities.Department;
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
public class DepartmentDao {

    static final Logger logger = LoggerFactory.getLogger(ClassNameUtil.getCurrentClassName());

    public static Department save(Department department) {
        try (Connection connection = DBConnection.getConnection()) {
            if (!checkExists(department)) {
                String query = "INSERT INTO department (name, description) VALUES (?, ?)";
                PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, department.getName());
                statement.setString(2, department.getDescription());
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

    public static boolean checkExists(Department department) throws SQLException {
        String sql = "SELECT 1 AS exists_check FROM department WHERE name = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, department.getName());
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        }
    }

    public static Department get(Integer departmentId) {
        Department department = null;
        String sql = "SELECT * FROM department WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, departmentId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    department = new Department(rs);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return department;
    }

    public static List<Department> getAll() {
        ArrayList<Department> departments = new ArrayList<>();
        String sql = "SELECT * FROM department";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    departments.add(new Department(rs));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return departments;
    }
}
