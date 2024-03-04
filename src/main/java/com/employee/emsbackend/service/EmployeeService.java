package com.employee.emsbackend.service;

import com.employee.emsbackend.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeDto employeeDto);

    List<EmployeeDto> getAllEmployees();

    EmployeeDto getEmployeeById(long id);

    EmployeeDto updateEmployee(EmployeeDto employeeDto,long id);

    void deleteEmployee(long id);
}
