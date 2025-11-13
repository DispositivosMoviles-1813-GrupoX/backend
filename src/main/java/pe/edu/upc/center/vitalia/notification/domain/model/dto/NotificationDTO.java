  package pe.edu.upc.center.vitalia.notification.domain.model.dto;

public class NotificationDTO {
    private String id;
    private String title;
    private String content;
    private String createdAt;
    private String status;
    private String type; // Add this field if it doesn't exist
    private String userId; // Ensure this is a String if you are converting Long to String

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}