package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entity.Book;
import com.example.demo.entity.Employee;
import com.example.demo.entity.Reservation;

public interface IReservationRepository extends CrudRepository<Reservation, Long>,
	org.springframework.data.jpa.repository.JpaSpecificationExecutor<Reservation> {

	
	void deleteByBookAndEmployee(Book book, Employee employee);
	
	/**
	 * A method that returns the by date ordered list of reservations for a specified employee
	 * 
	 * @param employeeId the id of the employee for which you want the reservations
	 * @return the list of reservations attached to this employee
	 */
	Iterable<Reservation> findByEmployee_idOrderByAllowedDescReservationDateDesc(long employeeId);
	
	


}
