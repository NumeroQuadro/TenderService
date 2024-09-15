package com.belunin.avito_test_task.Controllers;

import com.belunin.avito_test_task.Models.Dtos.EmployeeDTO;
import com.belunin.avito_test_task.Models.Employee;
import com.belunin.avito_test_task.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    private EmployeeDTO convertToDTO(Employee employee) {
        return new EmployeeDTO(employee.getId(), employee.getUsername(), employee.getFirstName(), employee.getLastName());
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllUsers() {
        List<EmployeeDTO> dtos = employeeService.getAllUsers().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getUserById(@PathVariable UUID id) {
        Employee employee = employeeService.getUserById(id);

        return ResponseEntity.ok(convertToDTO(employee));
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createUser(@RequestBody EmployeeDTO userDto) {
        Employee user = new Employee();
        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        Employee createdUser = employeeService.createUser(user);

        return new ResponseEntity<>(convertToDTO(createdUser), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateUser(@PathVariable UUID id, @RequestBody EmployeeDTO userDto) {
        Employee user = new Employee();
        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        Employee updatedUser = employeeService.updateUser(id, user);

        return ResponseEntity.ok(convertToDTO(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        employeeService.deleteUser(id);

        return ResponseEntity.ok().build();
    }
}
