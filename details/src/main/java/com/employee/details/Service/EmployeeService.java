package com.employee.details.Service;

import com.employee.details.Enitity.Employee;
import com.employee.details.Repo.EmployeeRepository;
import com.employee.details.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Service
@RequestMapping("/api/employees")
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Get all employees
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Get an employee by ID
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    // Create a new employee
    public void saveEmployee(Employee employee) {
        try {
            employeeRepository.save(employee);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Data integrity violation: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while saving the employee: " + e.getMessage());
        }
    }

    // Generate a new employee ID dynamically
//    @Autowired
//    private EmployeeRepository employeeRepository;

    public String generateEmployeeId() {
        Optional<Employee> lastEmployee = employeeRepository.findTopByOrderByIdDesc();
        String employeeId;

        // If there is no employee, start from EMP-0001
        if (lastEmployee.isPresent()) {
           String lastId = lastEmployee.get().getEmployeeId();
            // Extract the numeric part from the lastId and increment it
            int employeeNumber = Integer.parseInt(lastId.substring(4)); // Extract numeric part from "EMP-XXXX"
            employeeId = String.format("EMP-%04d", employeeNumber + 1);
        } else {
            // No employees found, start with EMP-0001
            employeeId = "EMP-0001";
        }

        return employeeId;
    }


    // Update an existing employee
    @Transactional  // Ensures the update operation is transactional
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        // Update employee details
        employee.setName(employeeDetails.getName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setDob(employeeDetails.getDob());
        employee.setSalary(employeeDetails.getSalary());
        employee.setStatus(employeeDetails.getStatus());

        return employeeRepository.save(employee);
    }

    // Delete an employee by ID
    public boolean deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        employeeRepository.delete(employee);
        return false;
    }


    @PostMapping
    public Employee createEmployee(Employee employee) {
        return employee;
    }
}