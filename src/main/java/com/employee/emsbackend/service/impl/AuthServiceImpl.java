package com.employee.emsbackend.service.impl;

import com.employee.emsbackend.dto.LoginDto;
import com.employee.emsbackend.dto.RegisterDto;
import com.employee.emsbackend.entity.Role;
import com.employee.emsbackend.entity.User;
import com.employee.emsbackend.exception.EmsAPIException;
import com.employee.emsbackend.repository.RoleRepository;
import com.employee.emsbackend.repository.UserRepository;
import com.employee.emsbackend.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;


    public AuthServiceImpl(AuthenticationManager authenticationManager,
                UserRepository userRepository,
                RoleRepository roleRepository,
                PasswordEncoder passwordEncoder) {
            this.authenticationManager = authenticationManager;
            this.userRepository = userRepository;
            this.roleRepository = roleRepository;
            this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsernameOrEmail(),loginDto.getPassword()
                ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "User logged-in Successfully";
    }


    @Override
    public String register(RegisterDto registerDto) {
        //add check for user already exists in database
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new EmsAPIException(HttpStatus.BAD_REQUEST,"Username is already exists!");
        }

        //add check for email already exists in database
        if(userRepository.existsByEmail(registerDto.getUsername())){
            throw new EmsAPIException(HttpStatus.BAD_REQUEST,"Email is already exists!");
        }

        User user=new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles=new HashSet<>();
        Role userRole=roleRepository.findByName("USER").get();
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

        return "User registered successfully";
    }
}
