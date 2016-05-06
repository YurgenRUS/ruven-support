package ru.kroshchenko.ruven.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kroshchenko.ruven.entities.Ticket;
import ru.kroshchenko.ruven.utils.ClassNameUtil;
import ru.kroshchenko.ruven.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kroshchenko
 *         2016.05.06
 */
public class TicketDao {

    static final Logger logger = LoggerFactory.getLogger(ClassNameUtil.getCurrentClassName());

    public static Ticket save(Ticket ticket) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "INSERT INTO ticket (owner_id, assigned_to_id, typical_id, priority, subject," +
                    "created, solved, status, status_reason, comment) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, ticket.getOwnerId());
            statement.setInt(2, ticket.getAssignedToId());
            statement.setInt(3, ticket.getTypicalId());
            statement.setInt(4, ticket.getPriority());
            statement.setString(5, ticket.getSubject());
            statement.setTimestamp(6, new Timestamp(ticket.getCreated().getTimeInMillis()));
            statement.setTimestamp(7, new Timestamp(ticket.getSolved().getTimeInMillis()));
            statement.setString(8, ticket.getStatus().name());
            statement.setString(9, ticket.getStatusReason());
            statement.setString(10, ticket.getComment());
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

    public static Ticket get(Integer ticketId) {
        Ticket ticket = null;
        String sql = "SELECT * FROM ticket WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, ticketId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    ticket = new Ticket(rs);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return ticket;
    }

    public static List<Ticket> getAll() {
        ArrayList<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM ticket";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    tickets.add(new Ticket(rs));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return tickets;
    }
}
