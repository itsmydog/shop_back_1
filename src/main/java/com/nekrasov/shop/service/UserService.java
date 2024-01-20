package com.nekrasov.shop.service;

import com.nekrasov.shop.domain.User;
import com.nekrasov.shop.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService { //security
    boolean save(UserDTO userDto);
    void save(User user);
    List<UserDTO> getAll();


    User findByName(String name);

    void updateProfile(UserDTO userDTO);
}
