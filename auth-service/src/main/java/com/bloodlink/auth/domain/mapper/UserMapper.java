package com.bloodlink.auth.domain.mapper;


import com.bloodlink.auth.domain.model.User;
import com.bloodlink.auth.dto.response.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponse (User user);
}
