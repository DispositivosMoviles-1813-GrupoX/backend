package pe.edu.upc.center.vitalia.notification.domain.model.commands;

public class UpdateNotificationCommand {
    private long id;
    private String type;
    private String content;
    private String status;

    public UpdateNotificationCommand(long id, String type, String content, String status) {
        this.id = id;
        this.type = type;
        this.content = content;
        this.status = status;
    }

    public long getId() { return id; }
    public String getType() { return type; }
    public String getContent() { return content; }
    public String getStatus() { return status; }
    public void setId(long id) { this.id = id; }
}