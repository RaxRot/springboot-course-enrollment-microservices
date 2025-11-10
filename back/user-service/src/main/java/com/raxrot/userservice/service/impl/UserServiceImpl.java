package com.raxrot.userservice.service.impl;

import com.raxrot.userservice.dto.UserRequest;
import com.raxrot.userservice.dto.UserResponse;
import com.raxrot.userservice.exceptions.ApiException;
import com.raxrot.userservice.mapper.UserMapper;
import com.raxrot.userservice.model.User;
import com.raxrot.userservice.repository.UserRepository;
import com.raxrot.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new ApiException("Email already exist", HttpStatus.CONFLICT);
        }

        User user = UserMapper.mapToUser(userRequest);
        User savedUser=userRepository.save(user);
        return UserMapper.mapToUserResponse(savedUser);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User>users=userRepository.findAll();
        List<UserResponse>userResponses=users.stream()
                .map(user->UserMapper.mapToUserResponse(user))
                .collect(Collectors.toList());
        return userResponses;
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user=userRepository.findById(id)
                .orElseThrow(()-> new ApiException("User not found", HttpStatus.NOT_FOUND));
        return UserMapper.mapToUserResponse(user);
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User user=userRepository.findById(id)
                .orElseThrow(()-> new ApiException("User not found", HttpStatus.NOT_FOUND));

        if (!user.getEmail().equals(userRequest.getEmail())
                && userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new ApiException("Email already exist", HttpStatus.CONFLICT);
        }

        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        User updatedUser=userRepository.save(user);

        return UserMapper.mapToUserResponse(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ApiException("User not found", HttpStatus.NOT_FOUND);
        }
        userRepository.deleteById(id);
    }
}
