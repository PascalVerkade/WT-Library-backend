package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Employee;
import com.example.demo.repository.IEmployeeRepository;

@Service
public class EmployeeService {
    @Autowired
    IEmployeeRepository repo;

    public Employee newEmployee(Employee newEmployee) {
        newEmployee.setPassword(String.valueOf(newEmployee.getPassword().hashCode()));
        System.out.println(newEmployee);
        return repo.save(newEmployee);
    }

    public Employee login(Employee login) {
        Optional<Employee> collection = repo.login(login.getEmail(), String.valueOf(login.getPassword().hashCode()));
        if(collection.isPresent()) {
            return collection.get();
        }
        return null;
    }

    public Employee cookieValues(Employee email) {
        Employee cookieValues = repo.cookieValues(email.getEmail());
        cookieValues.setPassword(null);
        return cookieValues;
    }

	public Optional<Employee> findById(long employeeId) {
		return repo.findById(employeeId);
	}
}
