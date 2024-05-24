package com.revolversolutions.trainingmanagement.mapper;


import com.revolversolutions.trainingmanagement.dto.UserRequest;
import com.revolversolutions.trainingmanagement.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserRequestDTOMapper implements EntityDTOMapper<User, UserRequest>{

    private final ModelMapper modelMapper;

    public UserRequestDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserRequest toDto(User entity) {
        return modelMapper.map(entity, UserRequest.class);
    }

    public User toEntity(UserRequest dto) {
        return modelMapper.map(dto, User.class);
    }
}
