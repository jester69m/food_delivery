package com.auth.service;

import com.auth.dto.UserDto;
import com.auth.model.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    User createUser(UserDto userRequest);

    User updateUser(Long id, UserDto userRequest);

    void deleteUser(Long id);

    User getUserById(Long id);

    List<User> getAllUsers();

    Page<User> getAllUsersWithPagination(int page, int size);

}
