package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.converts.UserConverter;
import org.example.dto.UserDto;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDto create(UserDto userDto) {
        return UserConverter.toUserDto(userRepository.save(UserConverter.toUser(userDto)));
    }
    public UserDto update(Long userId, UserDto userDto) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            if (userDto.getEmail() != null) {
                user.get().setEmail(userDto.getEmail());
            }
            return UserConverter.toUserDto(userRepository.save(user.get()));
        }
        return null;
    }
    public void delete(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        user.ifPresent(userRepository::delete);
    }
    public UserDto getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        UserDto userDto = null;
        if (optionalUser.isPresent()) {
            userDto = UserConverter.toUserDto(optionalUser.get());
        }
        return userDto;
    }
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserConverter::toUserDto)
                .collect(Collectors.toList());
    }
    public UserDto getUserEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(UserConverter::toUserDto).orElse(null);
    }
}
