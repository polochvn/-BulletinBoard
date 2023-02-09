package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.converts.NotificationConverter;
import org.example.dto.NotificationDto;
import org.example.model.Notification;
import org.example.model.User;
import org.example.repository.NotificationRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository messageRepository;
    private final UserRepository userRepository;
    public NotificationDto create(NotificationDto notificationDto, Long senderId) {
        Notification notification = NotificationConverter.toNotification(notificationDto);
        Optional<User> recipient = userRepository.findById(senderId);
        recipient.ifPresent(notification::setUser);
        notification.setTime(LocalDateTime.now());
        return NotificationConverter.toNotificationDto(messageRepository.save(notification));
    }

    public List<NotificationDto> getAllNotificationByRecipient(Long recipientId) {
        Optional<User> user = userRepository.findById(recipientId);
        return user.map(value -> messageRepository.findAllByUser(value)
                .stream()
                .map(NotificationConverter::toNotificationDto)
                .collect(Collectors.toList())).orElse(null);
    }
}
