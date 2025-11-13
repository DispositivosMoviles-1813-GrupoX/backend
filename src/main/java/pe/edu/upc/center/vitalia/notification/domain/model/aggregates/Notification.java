package pe.edu.upc.center.vitalia.notification.domain.model.aggregates;

import  jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.upc.center.vitalia.notification.domain.model.commands.CreateNotificationCommand;
import pe.edu.upc.center.vitalia.notification.domain.model.valueobjects.NotificationStatus;
import pe.edu.upc.center.vitalia.notification.domain.model.valueobjects.UserId;
import pe.edu.upc.center.vitalia.shared.domain.aggregates.AuditableAbstractAggregateRoot;

@Setter
@Getter
@Entity
@Table(name = "notifications")
@NoArgsConstructor
public class Notification extends AuditableAbstractAggregateRoot<Notification> {

  private String title;
  private String content;
  private NotificationStatus notificationStatus;

  @Embedded
  private UserId userId;

  public Notification(String title, String content, Long userId) {
    this.title = title;
    this.content = content;
    this.userId = new UserId(userId);
    this.notificationStatus = NotificationStatus.UNREAD;
  }

  public Notification(CreateNotificationCommand command) {
    this.title = command.title();
    this.content = command.content();
    this.userId = new UserId(command.userId());
    this.notificationStatus = NotificationStatus.UNREAD;
  }

  public void markAsRead() {
    this.notificationStatus = NotificationStatus.READ;
  }

  public void markAsArchived() {
    this.notificationStatus = NotificationStatus.ARCHIVED;
  }
}