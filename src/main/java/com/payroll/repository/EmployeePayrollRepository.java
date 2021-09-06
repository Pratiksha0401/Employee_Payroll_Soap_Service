package com.payroll.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payroll.entity.EmployeePayrollData;

@Repository
public interface EmployeePayrollRepository extends JpaRepository<EmployeePayrollData, Long> {
	
	Optional<EmployeePayrollData> findByName(String name);

}
