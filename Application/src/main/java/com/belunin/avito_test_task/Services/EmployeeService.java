package com.belunin.avito_test_task.Services;

import com.belunin.avito_test_task.Exceptions.ResourceNotFoundException;
import com.belunin.avito_test_task.Models.Employee;
import com.belunin.avito_test_task.Repositories.EmployeeRepository;
import com.belunin.avito_test_task.Repositories.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee createUser(Employee user) {
        return employeeRepository.save(user);
    }

    public Employee getUserById(UUID id) {
        return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }

    public Employee updateUser(UUID id, Employee userDetails) {
        Employee user = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setUsername(userDetails.getUsername());

        return employeeRepository.save(user);
    }

    public void deleteUser(UUID id) {
        Employee user = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        employeeRepository.delete(user);
    }

    public List<Employee> getAllUsers() {
        return employeeRepository.findAll();
    }
}

