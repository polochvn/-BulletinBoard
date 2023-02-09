package org.example.converts;

import org.example.dto.NotificationDto;
import org.example.model.Notification;

import java.time.format.DateTimeFormatter;

public class NotificationConverter {

    public static Notification toNotification(NotificationDto notificationDto) {
        return Notification.builder()
                .id(notificationDto.getId())
                .message(notificationDto.getMessage())
                .build();
    }

    public static NotificationDto toNotificationDto(Notification notification) {
        return NotificationDto.builder()
                .id(notification.getId())
                .message(notification.getMessage())
                .user(notification.getUser().getId())
                .time(notification.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
                .build();
    }
}
