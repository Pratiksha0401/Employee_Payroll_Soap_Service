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
import com.payroll.wsdlClasses.GetAllEmployeeRequest;
import com.payroll.wsdlClasses.GetAllEmployeeResponse;
import com.payroll.wsdlClasses.GetEmployeeByIdRequest;
import com.payroll.wsdlClasses.GetEmployeeByIdResponse;
import com.payroll.wsdlClasses.ServiceStatus;

@Endpoint
public class EmployeePayrollEndpoint {

	public static final String NAMESPACE_URI = "http://payroll.com/employee";

	@Autowired
	IEmployeePayrollService empService;

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "addEmployeeRequest")
	@ResponsePayload
	public AddEmployeeResponse addEmployee(@RequestPayload AddEmployeeRequest request) {
		//System.out.println("request"+request.getName());
		AddEmployeeResponse response = new AddEmployeeResponse();
		ServiceStatus serviceStatus = new ServiceStatus();

		EmployeePayrollData empData = new EmployeePayrollData();
		empData.setName(request.getName());
		empData.setDepartment(request.getDepartment());
		empData.setSalary(request.getSalary());

		EmployeeInfo empInfo = new EmployeeInfo();

		empData = empService.addEmployee(empData);
		if (empData == null) {
			serviceStatus.setMessage("Exception while adding Entity");
		} else {
			BeanUtils.copyProperties(empData, empInfo);
			serviceStatus.setMessage("Employee Added Successfully");
		}

		response.setEmployeeInfo(empInfo);
		response.setServiceStatus(serviceStatus);
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getEmployeeByIdRequest")
	@ResponsePayload
	public GetEmployeeByIdResponse getEmployeeById(@RequestPayload GetEmployeeByIdRequest request) {
		GetEmployeeByIdResponse response = new GetEmployeeByIdResponse();
		// ServiceStatus serviceStatus = new ServiceStatus();

		EmployeePayrollData empData = empService.getEmployeeById(request.getId());
		EmployeeInfo empInfo = new EmployeeInfo();
		BeanUtils.copyProperties(empData, empInfo);
		response.setEmployeeInfo(empInfo);
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllEmployeeRequest")
	@ResponsePayload
	public GetAllEmployeeResponse getAllConatct(@RequestPayload GetAllEmployeeRequest request) {
		return  empService.getAllEmployees(request);	
	}

}
