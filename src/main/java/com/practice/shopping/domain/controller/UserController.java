package com.practice.shopping.domain.controller;

import com.practice.shopping.domain.dto.UserDto;
import com.practice.shopping.domain.model.User;
import com.practice.shopping.domain.request.CreateUserRequest;
import com.practice.shopping.domain.request.UserUpdateRequest;
import com.practice.shopping.domain.response.ApiResponse;
import com.practice.shopping.domain.service.user.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final UserServiceImpl userService;

    @GetMapping("/{userId}/user")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        UserDto userDto = userService.convertUserToDto(user);

        return ResponseEntity.ok(new ApiResponse("Success", userDto));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request) {
        User user = userService.createUser(request);
        UserDto userDto = userService.convertUserToDto(user);

        return ResponseEntity.ok(new ApiResponse("Create User Success!", userDto));
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UserUpdateRequest request, @PathVariable Long userId) {
        User user = userService.updateUser(request, userId);
        UserDto userDto = userService.convertUserToDto(user);

        return ResponseEntity.ok(new ApiResponse("Update User Success!", userDto));
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);

        return ResponseEntity.ok(new ApiResponse("Delete User Success!", null));
    }
}
