package com.practice.shopping.domain.service.user;

import com.practice.shopping.domain.dto.UserDto;
import com.practice.shopping.domain.exceptions.AlreadyExistException;
import com.practice.shopping.domain.exceptions.ResourceNotFoundException;
import com.practice.shopping.domain.model.User;
import com.practice.shopping.domain.repository.UserRepository;
import com.practice.shopping.domain.request.CreateUserRequest;
import com.practice.shopping.domain.request.UserUpdateRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class UserService implements UserServiceImpl {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(_ -> {

                    return User.builder()
                            .email(request.getEmail())
                            .password(request.getPassword())
                            .firstName(request.getFirstName())
                            .lastName(request.getLastName())
                            .build();
                })
                .orElseThrow(() -> new AlreadyExistException("Oops!" + request.getEmail() + " already exists!"));
    }

    @Override
    public User updateUser(UserUpdateRequest request, Long userId) {
        return userRepository.findById(userId)
                .map(existingUser -> {
                    existingUser.setFirstName(request.getFirstName());
                    existingUser.setLastName(request.getLastName());

                    return userRepository.save(existingUser);
                }).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .ifPresentOrElse(userRepository::delete, () -> { throw new ResourceNotFoundException("User not found!");});
    }

    @Override
    public UserDto convertUserToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
