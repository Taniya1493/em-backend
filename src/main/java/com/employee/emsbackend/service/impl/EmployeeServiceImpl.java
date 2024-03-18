package com.employee.emsbackend.service.impl;

import com.employee.emsbackend.dto.EmployeeDto;
import com.employee.emsbackend.entity.Department;
import com.employee.emsbackend.entity.Employee;
import com.employee.emsbackend.exception.ResourceNotFoundException;
import com.employee.emsbackend.repository.DepartmentRepository;
import com.employee.emsbackend.repository.EmployeeRepository;
import com.employee.emsbackend.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    private DepartmentRepository departmentRepository;
    private ModelMapper modelMapper;


    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee=modelMapper.map(employeeDto, Employee.class);

        Department department=departmentRepository.findById(employeeDto.getDepartmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not exists with ","id: ", employeeDto.getDepartmentId()));
        employee.setDepartment(department);

        Employee savedEmployee=employeeRepository.save(employee);
        return modelMapper.map(savedEmployee,EmployeeDto.class);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employeeList=employeeRepository.findAll();
        return employeeList.stream().map((employee) -> modelMapper.map(employee,EmployeeDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto getEmployeeById(long id) {
        Employee employee=employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee","id",id));

        return modelMapper.map(employee,EmployeeDto.class);
    }

    @Override
    public EmployeeDto updateEmployee(EmployeeDto employeeDto, long id) {
        Employee employee=employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee","id",id));

        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());

        Department department=departmentRepository.findById(employeeDto.getDepartmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not exists with ","id: ", employeeDto.getDepartmentId()));
        employee.setDepartment(department);

        Employee updatedEmployee=employeeRepository.save(employee);

        return modelMapper.map(updatedEmployee,EmployeeDto.class);
    }

    @Override
    public void deleteEmployee(long id) {
        Employee employee=employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee","id",id));

        employeeRepository.delete(employee);
    }
}
