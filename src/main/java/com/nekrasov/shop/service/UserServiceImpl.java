package com.nekrasov.shop.service;

import com.nekrasov.shop.dao.UserRepository;
import com.nekrasov.shop.domain.Role;
import com.nekrasov.shop.domain.User;
import com.nekrasov.shop.dto.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    private UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean save(UserDTO userDto) {
        if (!Objects.equals(userDto.getPassword(), userDto.getMatchingpassword())){
            throw new RuntimeException("password is not equals");
        }
        User user = User.builder()
                .name(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .role(Role.ROLE_CLIENT)
                .build();
        userRepository.save(user);
        return true;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public List<UserDTO> getAll() {
       return userRepository.findAll().stream()
               .map(this::toDto)
               .collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findFirstByName(username);
        if (user == null){
            throw new UsernameNotFoundException("User not found with name " + username);
        }

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().name()));
        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                roles

        );
    }

    private UserDTO toDto(User user){
        return UserDTO.builder()
                .username(user.getName())
                .email(user.getEmail())
                .build();

    }

    @Override
    public User findByName(String name) {
        return userRepository.findFirstByName(name);
    }

    @Override
    @Transactional
    public void updateProfile(UserDTO userDTO) {
        User savedUser = userRepository.findFirstByName(userDTO.getUsername());
        if (savedUser == null){
            throw new RuntimeException("User not found by name " + userDTO.getUsername());
        }

        boolean isChanged = false;
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()){
            savedUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            isChanged = true;
        }
        if(!Objects.equals(userDTO.getEmail(), savedUser.getEmail())){
            savedUser.setEmail(userDTO.getEmail());
            isChanged = true;
        }
        if(isChanged){
            userRepository.save(savedUser);
        }

    }
}
