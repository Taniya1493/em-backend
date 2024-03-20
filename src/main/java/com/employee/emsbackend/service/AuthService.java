package com.employee.emsbackend.service;


import com.employee.emsbackend.dto.LoginDto;
import com.employee.emsbackend.dto.RegisterDto;

public interface AuthService {

    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}