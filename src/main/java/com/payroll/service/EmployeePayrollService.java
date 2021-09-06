package com.payroll.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payroll.entity.EmployeePayrollData;
import com.payroll.repository.EmployeePayrollRepository;

@Service
@Transactional
public class EmployeePayrollService implements IEmployeePayrollService {

	@Autowired
	EmployeePayrollRepository empRepository;

	@Override
	public EmployeePayrollData addEmployee(EmployeePayrollData empData) {
		Optional<EmployeePayrollData> empName = empRepository.findByName(empData.getName());
		System.out.println(empName);
		if (empName.isPresent()) {

		}
		EmployeePayrollData empData1 = empRepository.save(empData);
		return empData1;
	}

	@Override
	public boolean deleteEmployee(long id) {
		Optional<EmployeePayrollData> empId = empRepository.findById(id);
		// System.out.println("Delete Id: "+ id);
		if (empId.isPresent()) {
			empRepository.deleteById(id);
			return true;
		} else
			return false;
	}

	@Override
	public EmployeePayrollData getEmployeeById(long id) {
		EmployeePayrollData empdata = empRepository.findById(id).get();
		return empdata;
	}

	@Override
	public List<EmployeePayrollData> getAllEmployee() {
		List<EmployeePayrollData> empdata = empRepository.findAll();
		return empdata;
	}

	@Override
	public boolean updateEmployee(EmployeePayrollData empData) {
		try {
			empRepository.save(empData);
			return true;
		}catch(Exception e){
			return false;
		}
	}

}
