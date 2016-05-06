package ru.kroshchenko.ruven.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kroshchenko.ruven.entities.TicketMessage;
import ru.kroshchenko.ruven.utils.ClassNameUtil;
import ru.kroshchenko.ruven.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kroshchenko
 *         2016.05.06
 */
public class TicketMessageDao {

    static final Logger logger = LoggerFactory.getLogger(ClassNameUtil.getCurrentClassName());

    public static TicketMessage save(TicketMessage ticketMessage) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "INSERT INTO ticket_message (ticket_id, author_id, body, created) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, ticketMessage.getTicketId());
            statement.setInt(2, ticketMessage.getAuthorId());
            statement.setString(3, ticketMessage.getBody());
            statement.setTimestamp(4, new Timestamp(ticketMessage.getCreated().getTimeInMillis()));
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

    public static TicketMessage get(Integer ticketMessageId) {
        TicketMessage ticketMessage = null;
        String sql = "SELECT * FROM ticket_message WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, ticketMessageId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    ticketMessage = new TicketMessage(rs);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return ticketMessage;
    }

    public static List<TicketMessage> getAll() {
        ArrayList<TicketMessage> ticketMessages = new ArrayList<>();
        String sql = "SELECT * FROM ticket_message";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    ticketMessages.add(new TicketMessage(rs));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return ticketMessages;
    }
}
