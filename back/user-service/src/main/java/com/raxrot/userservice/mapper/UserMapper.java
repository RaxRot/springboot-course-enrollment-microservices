package com.raxrot.userservice.mapper;

import com.raxrot.userservice.dto.UserRequest;
import com.raxrot.userservice.dto.UserResponse;
import com.raxrot.userservice.model.User;

public class UserMapper {

    public static User mapToUser(final UserRequest userRequest) {
        return User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .phone(userRequest.getPhone())
                .build();
    }

    public static UserResponse mapToUserResponse(final User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
