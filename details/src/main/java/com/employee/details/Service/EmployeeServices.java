package com.employee.details.Service;

import com.employee.details.Enitity.Employee;

import java.util.Optional;

public interface EmployeeServices {
    Optional<Employee> updateEmployee(Long id, Employee employeeDetails);

}
