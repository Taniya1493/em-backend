package com.employee.emsbackend.service.impl;

import com.employee.emsbackend.dto.DepartmentDto;
import com.employee.emsbackend.entity.Department;
import com.employee.emsbackend.exception.ResourceNotFoundException;
import com.employee.emsbackend.repository.DepartmentRepository;
import com.employee.emsbackend.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<DepartmentDto> getAllDepartments() {
        List<Department> departmentList=departmentRepository.findAll();
        return departmentList.stream().map((department) -> modelMapper.map(department,DepartmentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDto updateDepartment(DepartmentDto departmentDto,Long departmentId) {
        Department department=departmentRepository.findById(departmentId).orElseThrow(() ->
                new ResourceNotFoundException("Department not exists with given ","id " , departmentId));

        department.setDepartmentName(departmentDto.getDepartmentName());
        department.setDepartmentDescription(departmentDto.getDepartmentDescription());
        Department updatedDepartment=departmentRepository.save(department);
        return  modelMapper.map(updatedDepartment,DepartmentDto.class);
    }

    @Override
    public void deleteDepartment(Long departmentId) {
        Department department=departmentRepository.findById(departmentId).orElseThrow(() ->
                new ResourceNotFoundException("Department not exists with given ","id " , departmentId));

        departmentRepository.delete(department);
    }
}
