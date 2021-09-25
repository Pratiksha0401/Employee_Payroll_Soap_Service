package com.payroll.service;

import java.util.List;

import com.payroll.entity.EmployeePayrollData;
import com.payroll.wsdlClasses.GetAllEmployeeRequest;
import com.payroll.wsdlClasses.GetAllEmployeeResponse;
import com.payroll.wsdlClasses.GetEmployeeByIdRequest;

public interface IEmployeePayrollService {

	EmployeePayrollData addEmployee(EmployeePayrollData empData);
	
	boolean deleteEmployee(long id);
	
	EmployeePayrollData getEmployeeById(long id);
	
	//boolean updateEmployee(long id, EmployeePayrollData empData);

	boolean updateEmployee(EmployeePayrollData empData);

	GetAllEmployeeResponse getAllEmployees(GetAllEmployeeRequest request);
}
