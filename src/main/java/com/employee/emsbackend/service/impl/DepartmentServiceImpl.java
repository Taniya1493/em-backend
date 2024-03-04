package com.employee.emsbackend.service.impl;

import com.employee.emsbackend.dto.DepartmentDto;
import com.employee.emsbackend.entity.Department;
import com.employee.emsbackend.exception.ResourceNotFoundException;
import com.employee.emsbackend.repository.DepartmentRepository;
import com.employee.emsbackend.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepository;

    private ModelMapper modelMapper;
    @Override
    public DepartmentDto createDepartment(DepartmentDto departmentDto) {
        Department department=modelMapper.map(departmentDto,Department.class);
        Department savedDepartment=departmentRepository.save(department);
        return modelMapper.map(savedDepartment,DepartmentDto.class);
    }

    @Override
    public DepartmentDto getDepartmentById(Long departmentId) {
        Department department=departmentRepository.findById(departmentId).orElseThrow(() ->
                new ResourceNotFoundException("Department not exists with given ","id " , departmentId));
        return modelMapper.map(department,DepartmentDto.class);
    }
}
