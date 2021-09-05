package com.payroll.endpoint;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.payroll.entity.EmployeePayrollData;
import com.payroll.service.IEmployeePayrollService;
import com.payroll.wsdlClasses.AddEmployeeRequest;
import com.payroll.wsdlClasses.AddEmployeeResponse;
import com.payroll.wsdlClasses.EmployeeInfo;
import com.payroll.wsdlClasses.ServiceStatus;

@Endpoint
public class EmployeePayrollEndpoint {
	
	public static final String NAMESPACE_URI = "http://payroll.com/employee";
	
	@Autowired
	IEmployeePayrollService empService;
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "addEmployeeRequest")
	@ResponsePayload
	public AddEmployeeResponse addEmployee(@RequestPayload AddEmployeeRequest request) {
		AddEmployeeResponse response = new AddEmployeeResponse();
		ServiceStatus serviceStatus = new ServiceStatus();
		EmployeePayrollData empData = new EmployeePayrollData();
		empData.setName(request.getName());
		empData.setDepartment(request.getDepartment());
		empData.setSalary(request.getSalary());
		
		EmployeeInfo empInfo = new EmployeeInfo();
		
		empData = empService.addEmployee(empData);
		if(empData == null) {
			serviceStatus.setStatusCode("CONFLICT");
			serviceStatus.setMessage("Exception while adding Entity");
		}else {
			BeanUtils.copyProperties(empData, empInfo);
			serviceStatus.setStatusCode("SUCCESS");
			serviceStatus.setMessage("Employee Added Successfully");
		}
		
		response.setEmployeeInfo(empInfo);
		response.setServiceStatus(serviceStatus);
		return response;
	}


}
