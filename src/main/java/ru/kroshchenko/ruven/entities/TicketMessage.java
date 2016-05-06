package ru.kroshchenko.ruven.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * @author Kroshchenko
 *         2016.05.02
 */
public class TicketMessage {

    private Integer id;
    private Integer authorId;
    private Integer ticketId;
    private String body;
    private Calendar created;

    public TicketMessage() {

    }

    public TicketMessage(ResultSet rs) throws SQLException {
        id = rs.getInt("id");
        authorId = rs.getInt("author_id");
        ticketId = rs.getInt("ticket_id");
        body = rs.getString("body");
        Timestamp created = rs.getTimestamp("created");
        if (created != null) {
            this.created = Calendar.getInstance();
            this.created.setTime(created);
        }
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }
}
