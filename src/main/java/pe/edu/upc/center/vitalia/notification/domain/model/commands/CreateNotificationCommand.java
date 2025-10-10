package pe.edu.upc.center.vitalia.notification.domain.model.commands;

import lombok.Getter;

@Getter
public class CreateNotificationCommand {
    private String title;
    private String content;
    private Long userId;

    // Constructor
    public CreateNotificationCommand(String title, String content, Long userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

    // Getters (if not using Lombok)
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public Long getUserId() { return userId; }
}