package com.payroll.service;

import java.util.List;

import com.payroll.entity.EmployeePayrollData;

public interface IEmployeePayrollService {

	EmployeePayrollData addEmployee(EmployeePayrollData empData);
	
	boolean deleteEmployee(long id);
	
	EmployeePayrollData getEmployeeById(long id);

	List<EmployeePayrollData> getAllEmployee();
	
	//boolean updateEmployee(long id, EmployeePayrollData empData);

	boolean updateEmployee(EmployeePayrollData empData);

}
