package com.payroll.endpoint;

import java.util.ArrayList;
import java.util.List;

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
import com.payroll.wsdlClasses.DeleteEmployeeRequest;
import com.payroll.wsdlClasses.DeleteEmployeeResponse;
import com.payroll.wsdlClasses.EmployeeInfo;
import com.payroll.wsdlClasses.GetAllEmployeeRequest;
import com.payroll.wsdlClasses.GetAllEmployeeResponse;
import com.payroll.wsdlClasses.GetEmployeeByIdRequest;
import com.payroll.wsdlClasses.GetEmployeeByIdResponse;
import com.payroll.wsdlClasses.ServiceStatus;
import com.payroll.wsdlClasses.UpdateEmployeeRequest;
import com.payroll.wsdlClasses.UpdateEmployeeResponse;

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
		if (empData == null) {
			serviceStatus.setStatusCode("CONFLICT");
			serviceStatus.setMessage("Exception while adding Entity");
		} else {
			BeanUtils.copyProperties(empData, empInfo);
			serviceStatus.setStatusCode("SUCCESS");
			serviceStatus.setMessage("Employee Added Successfully");
		}

		response.setEmployeeInfo(empInfo);
		response.setServiceStatus(serviceStatus);
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteEmployeeRequest")
	@ResponsePayload
	public DeleteEmployeeResponse deleteEmployee(@RequestPayload DeleteEmployeeRequest request) {
		DeleteEmployeeResponse response = new DeleteEmployeeResponse();
		ServiceStatus serviceStatus = new ServiceStatus();

		boolean flag = empService.deleteEmployee(request.getId());

		if (flag == false) {
			serviceStatus.setStatusCode("FAIL");
			serviceStatus.setMessage("Exception while deletint Entity id=" + request.getId());
		} else {
			serviceStatus.setStatusCode("SUCCESS");
			serviceStatus.setMessage("Content Deleted Successfully");
		}

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
	public GetAllEmployeeResponse getAllEmployee(@RequestPayload GetAllEmployeeRequest request) {
		GetAllEmployeeResponse response = new GetAllEmployeeResponse();
		// ServiceStatus serviceStatus = new ServiceStatus();

		List<EmployeePayrollData> empDataList = empService.getAllEmployee();
		List<EmployeeInfo> empInfoList = new ArrayList<>();

		for (EmployeePayrollData empData : empDataList) {
			EmployeeInfo empInfo1 = new EmployeeInfo();
			BeanUtils.copyProperties(empData, empInfo1);
			empInfoList.add(empInfo1);
		}
		response.getEmployeeInfo().addAll(empInfoList);
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateEmployeeRequest")
	@ResponsePayload
	public UpdateEmployeeResponse updateEmployee(@RequestPayload UpdateEmployeeRequest request) {
		UpdateEmployeeResponse response = new UpdateEmployeeResponse();
		ServiceStatus serviceStatus = new ServiceStatus();

		EmployeePayrollData empDataById = empService.getEmployeeById(request.getEmployeeInfo().getId());

		if (empDataById == null) {
			serviceStatus.setStatusCode("NOT FOUND");
			serviceStatus.setMessage("Employee = " + request.getEmployeeInfo().getId() + " not found");
		} else {
			empDataById.setName(request.getEmployeeInfo().getName());
			;
			empDataById.setDepartment(request.getEmployeeInfo().getDepartment());
			empDataById.setSalary(request.getEmployeeInfo().getSalary());

			boolean flag = empService.updateEmployee(empDataById);

			if (flag == false) {
				serviceStatus.setStatusCode("CONFLICT");
				serviceStatus.setMessage("Exception while updating Entity=" + request.getEmployeeInfo().getId());
				;
			} else {
				serviceStatus.setStatusCode("SUCCESS");
				serviceStatus.setMessage("Content updated Successfully");
			}
		}

		response.setServiceStatus(serviceStatus);
		return response;
	}

}
