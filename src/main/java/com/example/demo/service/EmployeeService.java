package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Book;
import com.example.demo.entity.Employee;
import com.example.demo.repository.IEmployeeRepository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;


@Service
public class EmployeeService {
    @Autowired
    IEmployeeRepository repo;

	/**
	 * Searches for books based on the provided search term.
	 *
	 * @param searchTerm the term to search for in book titles, writers, and ISBN.
	 * @return a list of books matching the search criteria.
	 */
	public List<Employee> searchEmployees(String searchTerm) {
		return repo.findAll(new Specification<Employee>() {
            @Override
            public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate firstNameCompare = criteriaBuilder.like(root.get("firstName"), "%" + searchTerm + "%");
                Predicate lastNameCompare = criteriaBuilder.like(root.get("lastName"), "%" + searchTerm + "%");
                Predicate emailCompare = criteriaBuilder.like(root.get("email"), "%" + searchTerm + "%");

                return criteriaBuilder.or(firstNameCompare, lastNameCompare, emailCompare);
            }
        });
    }

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
