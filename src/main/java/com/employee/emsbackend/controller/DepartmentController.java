package com.employee.emsbackend.controller;

import com.employee.emsbackend.dto.DepartmentDto;
import com.employee.emsbackend.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    public DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<DepartmentDto> createDepartment(@RequestBody DepartmentDto departmentDto) {
        DepartmentDto createdDepartment=departmentService.createDepartment(departmentDto);
        return new ResponseEntity<>(createdDepartment, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable("id") Long departmentId) {
       return ResponseEntity.ok(departmentService.getDepartmentById(departmentId));
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<DepartmentDto> updateDepartment(@RequestBody DepartmentDto departmentDto,
                                          @PathVariable("id") Long departmentId) {
        DepartmentDto updatedDepartment=departmentService.updateDepartment(departmentDto,departmentId);
        return ResponseEntity.ok(updatedDepartment);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable("id") Long departmentId) {
        departmentService.deleteDepartment(departmentId);
        return ResponseEntity.ok("Department Deleted Successfully");
    }
}
