package com.payroll.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payroll.entity.EmployeePayrollData;
import com.payroll.repository.EmployeePayrollRepository;

@Service
@Transactional
public class EmployeePayrollService implements IEmployeePayrollService{
	
	@Autowired
	EmployeePayrollRepository empRepository;
	
	@Override
	public EmployeePayrollData addEmployee(EmployeePayrollData empData) {
		
		EmployeePayrollData empData1 = empRepository.save(empData);
		return empData1;
		
	}

}
