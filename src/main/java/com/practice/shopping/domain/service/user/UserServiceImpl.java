package com.practice.shopping.domain.service.user;

import com.practice.shopping.domain.dto.UserDto;
import com.practice.shopping.domain.model.User;
import com.practice.shopping.domain.request.CreateUserRequest;
import com.practice.shopping.domain.request.UserUpdateRequest;

public interface UserServiceImpl {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);
}
