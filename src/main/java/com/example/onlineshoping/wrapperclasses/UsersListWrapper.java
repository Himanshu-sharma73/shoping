package com.example.onlineshoping.wrapperclasses;

import com.example.onlineshoping.dto.UserDto;
import com.example.onlineshoping.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class UsersListWrapper {
    private List<UserDto> usersList;
}
