package pe.edu.upc.center.vitalia.notification.domain.model.entities;

import java.util.Date;

public class Message {
    private long senderId;
    private long receiverId;
    private String content;
    private Date timestamp;
    private boolean readStatus;

    public Message(long senderId, long receiverId, String content, Date timestamp) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.timestamp = timestamp;
        this.readStatus = false;
    }

    public void markAsRead() {
        this.readStatus = true;
    }

    // Getters and setters omitted for brevity
}