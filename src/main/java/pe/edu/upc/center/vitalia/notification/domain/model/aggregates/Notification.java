package pe.edu.upc.center.vitalia.notification.domain.model.aggregates;

import  jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title; // New field for frontend compatibility
    private String content;
    private LocalDateTime createdAt; // Renamed from timestamp
    private LocalDateTime updatedAt; // Add this field

    @Column(nullable = false) // Ensure this column is not nullable
    private LocalDateTime timestamp;

    private String status; // Ensure this field exists
    private Long userId; // Ensure this field exists
    private Long recipientId; // Ensure this field exists

    @Column(nullable = false)
    private String type; // Ensure this field exists

    // Constructor
    public Notification(Long id, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt, String status, Long userId, Long recipientId, String type) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt; // Initialize this field
        this.timestamp = LocalDateTime.now(); // Initialize this field
        this.status = status;
        this.userId = userId;
        this.recipientId = recipientId;
        this.type = type;
    }

    // Additional constructor
    public Notification(String title, String content, Long userId, Long recipientId, String type) {
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.timestamp = LocalDateTime.now(); // Initialize timestamp here
        this.status = "unread";
        this.userId = userId;
        this.recipientId = recipientId;
        this.type = type;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getRecipientId() { return recipientId; }
    public void setRecipientId(Long recipientId) { this.recipientId = recipientId; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    // Other getters and setters omitted for brevity
}