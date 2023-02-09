package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.example.dto.UserDto;
import org.example.model.User;
import org.example.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "Отвечает за пользователей")
public class UserController {
    private final static Log log = LogFactory.getLog(UserController.class);
    private final UserService userService;
    @Operation(
            summary = "Создание пользователя",
            description = "Позволяет создать пользователя"
    )
    @PostMapping
    UserDto create(@RequestBody UserDto userDto) {
        log.info("create user");
        return userService.create(userDto);
    }
    @Operation(
            summary = "Получение данных о пользователе",
            description = "Позволяет получить данные пользователя по Id"
    )
    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Long userId) {
        log.info("User Id = " + userId + ";");
        return userService.getUserById(userId);
    }

    @Operation(
            summary = "Изменение данных пользователя",
            description = "Позволяет изменить данные пользователя"
    )
    @PatchMapping("/{userId}")
    public UserDto update(@PathVariable Long userId, @RequestBody UserDto userDto) {
        log.info("Update User Id = " + userId + ";");
        return userService.update(userId, userDto);
    }
    @Operation(
            summary = "Удаление пользователя",
            description = "Позволяет удалить пользователя"
    )
    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Long userId) {
        log.info("Delete User Id = " + userId + ";");
        userService.delete(userId);
    }
    @Operation(
            summary = "Получение всех пользователей",
            description = "Получение списка всех пользователей"
    )
    @GetMapping
    public List<UserDto> getAllUsers() {
        log.info("Get All Users");
        return userService.getAllUsers();
    }
}
