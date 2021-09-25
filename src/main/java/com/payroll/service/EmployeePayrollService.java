package com.payroll.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payroll.entity.EmployeePayrollData;
import com.payroll.exception.SoapException;
import com.payroll.repository.EmployeePayrollRepository;
import com.payroll.wsdlClasses.EmployeeInfo;
import com.payroll.wsdlClasses.GetAllEmployeeRequest;
import com.payroll.wsdlClasses.GetAllEmployeeResponse;
import com.payroll.wsdlClasses.GetEmployeeByIdRequest;

@Service
@Transactional
public class EmployeePayrollService implements IEmployeePayrollService {

	@Autowired
	EmployeePayrollRepository empRepository;

	@Override
	public EmployeePayrollData addEmployee(EmployeePayrollData empData) {
		Optional<EmployeePayrollData> empName = empRepository.findByName(empData.getName());
		//System.out.println(empName);
		//System.out.println("name"+empData.getName());
		if (empName.isPresent()) {
			throw new SoapException("Employee Already Present");
		}
		if( empData.getName()=="" || empData.getName()==null) {
			throw new SoapException("Name Cannot be null");
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
		return empRepository.findById(id)
				.orElseThrow(() -> new SoapException("Employee Not Found"));
	}

	@Override
	public GetAllEmployeeResponse getAllEmployees(GetAllEmployeeRequest request) {
		GetAllEmployeeResponse response = new GetAllEmployeeResponse();
		List<EmployeePayrollData> empDataList = empRepository.findAll();
		System.out.println("list=="+empDataList);
		List<EmployeeInfo> infoList = new ArrayList<>();
		
		for (EmployeePayrollData empData : empDataList) {
			System.out.println("get1=="+empData);
			System.out.println("get1=="+empData.getDepartment());
			EmployeeInfo info = new EmployeeInfo();
			BeanUtils.copyProperties(empData, info);
			infoList.add(info);
			System.out.println("info department=="+info.getDepartment());
			
		}
		response.getEmployeeInfo().addAll(infoList);
		
		return response;
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
