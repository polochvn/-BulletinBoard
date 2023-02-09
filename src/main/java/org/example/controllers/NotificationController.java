package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.example.dto.NotificationDto;
import org.example.services.NotificationService;
import org.example.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/notifications")
@RequiredArgsConstructor
@Tag(name = "Notification Controller", description = "Реализует получение уведомлений для покупателей")
public class NotificationController {
    private final static Log log = LogFactory.getLog(UserController.class);
    private final NotificationService notificationService;
    private final UserService userService;
    @Operation(
            summary = "Создание уведомления",
            description = "Позволяет создать уведомление"
    )
    @PostMapping
    NotificationDto create(@RequestBody NotificationDto notificationDto) {
        log.info("Create Notification!");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        long userId = userService.getUserEmail(auth.getName()).getId();
        return notificationService.create(notificationDto, userId);
    }

    @Operation(
            summary = "Список входящих уведомлений",
            description = "Пользователь может получить список его входящих уведомлений"
    )
    @GetMapping("/recipient")
    List<NotificationDto> getAllByRecipient() {
        log.info("Get All Notifications By Recipient");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        long userId = userService.getUserEmail(auth.getName()).getId();
        return notificationService.getAllNotificationByRecipient(userId);
    }
}
