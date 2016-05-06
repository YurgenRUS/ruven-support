package ru.kroshchenko.ruven.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kroshchenko.ruven.entities.Employee;
import ru.kroshchenko.ruven.utils.ClassNameUtil;
import ru.kroshchenko.ruven.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kroshchenko
 *         2016.05.06
 */
public class EmployeeDao {

    static final Logger logger = LoggerFactory.getLogger(ClassNameUtil.getCurrentClassName());

    public static Employee save(Employee employee) {
        try (Connection connection = DBConnection.getConnection()) {
            if (!checkExists(employee)) {
                String query = "INSERT INTO employee (department_id, full_name, phone, email, vip) " +
                        "VALUES (?, ?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                statement.setLong(1, employee.getDepartmentId());
                statement.setString(2, employee.getFullName());
                statement.setString(2, employee.getPhone());
                statement.setString(2, employee.getEmail());
                statement.setBoolean(2, employee.getVip());
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

    public static boolean checkExists(Employee employee) throws SQLException {
        String sql = "SELECT 1 AS exists_check FROM employee WHERE email = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, employee.getEmail());
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        }
    }

    public static Employee get(Integer employeeId) {
        Employee employee = null;
        String sql = "SELECT * FROM employee WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, employeeId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    employee = new Employee(rs);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return employee;
    }

    public static List<Employee> getAll() {
        ArrayList<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employee";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    employees.add(new Employee(rs));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return employees;
    }
}
