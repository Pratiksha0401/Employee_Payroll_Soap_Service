package com.payroll.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.payroll.entity.EmployeePayrollData;

@Repository
public interface EmployeePayrollRepository extends JpaRepository<EmployeePayrollData, Long> {
	
	Optional<EmployeePayrollData> findByName(String name);
	
	@Query(value="select * from employee_payroll_data,employee_department where employee_id=id and department=:department", nativeQuery = true)
	List<EmployeePayrollData> findEmployeesByDepartment(@Param("department") String department);

}
