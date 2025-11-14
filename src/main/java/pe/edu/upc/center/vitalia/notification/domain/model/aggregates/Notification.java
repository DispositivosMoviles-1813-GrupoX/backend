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

  @Column(nullable = false, length = 500)
  private String title;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String content;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
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

  private void validateFields(String title, String content, Long userId) {
    if (title == null || title.trim().isEmpty()) {
      throw new IllegalArgumentException("Title cannot be null or empty");
    }
    if (content == null || content.trim().isEmpty()) {
      throw new IllegalArgumentException("Content cannot be null or empty");
    }
    if (userId == null) {
      throw new IllegalArgumentException("User ID cannot be null");
    }
  }

  public void markAsRead() {
    this.notificationStatus = NotificationStatus.READ;
  }

  public void markAsArchived() {
    this.notificationStatus = NotificationStatus.ARCHIVED;
  }
}