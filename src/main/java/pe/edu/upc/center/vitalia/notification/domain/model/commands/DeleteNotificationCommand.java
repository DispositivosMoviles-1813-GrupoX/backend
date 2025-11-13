package pe.edu.upc.center.vitalia.notification.domain.model.commands;

public class DeleteNotificationCommand {
    private long id;

    public DeleteNotificationCommand() {}
    public DeleteNotificationCommand(long id) {
        this.id = id;
    }
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
}