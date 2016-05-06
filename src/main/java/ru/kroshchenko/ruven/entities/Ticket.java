package ru.kroshchenko.ruven.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * @author Kroshchenko
 *         2016.05.02
 */
public class Ticket {

    private Integer id;
    private Integer ownerId;
    private Integer assignedToId;
    private Integer typicalId;
    private Integer priority;
    private String subject;
    private Calendar created;
    private Calendar solved;
    private TicketStatus status = TicketStatus.NEW;
    private String statusReason;
    private String comment;

    public Ticket() {

    }

    public Ticket(ResultSet rs) throws SQLException {
        id = rs.getInt("id");
        ownerId = rs.getInt("owner_id");
        assignedToId = rs.getInt("assigned_to_id");
        typicalId = rs.getInt("typical_id");
        priority = rs.getInt("priority");
        subject = rs.getString("subject");
        statusReason = rs.getString("status_reason");
        comment = rs.getString("comment");

        Timestamp created = rs.getTimestamp("created");
        if (created != null) {
            this.created = Calendar.getInstance();
            this.created.setTime(created);
        }

        Timestamp solved = rs.getTimestamp("solved");
        if (solved != null) {
            this.solved = Calendar.getInstance();
            this.solved.setTime(solved);
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(Integer assignedToId) {
        this.assignedToId = assignedToId;
    }

    public Integer getTypicalId() {
        return typicalId;
    }

    public void setTypicalId(Integer typicalId) {
        this.typicalId = typicalId;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }

    public Calendar getSolved() {
        return solved;
    }

    public void setSolved(Calendar solved) {
        this.solved = solved;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
