package com.employee.details.Repo;

import com.employee.details.Enitity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
    public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(value = "SELECT e FROM Employee e ORDER BY e.id DESC")
    Optional<Employee> findTopByOrderByIdDesc();

    Optional<Object> findFirstByOrderByIdDesc();
}


