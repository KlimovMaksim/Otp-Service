package ru.klimov.otpservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.klimov.otpservice.dto.response.UserResponseDto;
import ru.klimov.otpservice.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getUsers() {
        log.info("Received request to get all users");
        List<UserResponseDto> users = userService.getUsers();
        log.info("Successfully retrieved {} users", users.size());
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.info("Received request to delete user with id: {}", id);
        userService.delete(id);
        log.info("Successfully deleted user with id: {}", id);
        return ResponseEntity.noContent().build();
    }
}
