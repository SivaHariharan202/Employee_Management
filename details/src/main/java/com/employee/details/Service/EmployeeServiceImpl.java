package com.employee.details.Service;

import com.employee.details.Enitity.Employee;
import com.employee.details.Repo.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeServices {

    @Autowired
    private EmployeeRepository employeeRepository;

//    @Override
//    public Optional<Employee> getEmployeeById(Long id) {
//        return employeeRepository.findById(id); // Ensure this returns Optional<Employee>
//    }

    @Override
    public Optional<Employee> updateEmployee(Long id, Employee employeeDetails) {
        return employeeRepository.findById(id)
                .map(existingEmployee -> {
                    existingEmployee.setName(employeeDetails.getName());
                    existingEmployee.setEmail(employeeDetails.getEmail());
                    existingEmployee.setDob(employeeDetails.getDob());
                    existingEmployee.setAge(employeeDetails.getAge());
                    existingEmployee.setSalary(employeeDetails.getSalary());
                    existingEmployee.setStatus(employeeDetails.getStatus());
                    return employeeRepository.save(existingEmployee);
                });
    }
}

