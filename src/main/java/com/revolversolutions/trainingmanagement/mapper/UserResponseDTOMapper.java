package com.revolversolutions.trainingmanagement.mapper;

import com.revolversolutions.trainingmanagement.dto.UserResponse;
import com.revolversolutions.trainingmanagement.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserResponseDTOMapper implements EntityDTOMapper<User, UserResponse>{


    private final ModelMapper modelMapper;

    public UserResponseDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public User toEntity(UserResponse dto) {
        return modelMapper.map(dto,User.class);
    }

    @Override
    public UserResponse toDto(User entity) {
        return modelMapper.map(entity, UserResponse.class);
    }


}
