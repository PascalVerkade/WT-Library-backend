package com.example.demo.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

/**
 * A class that represents a Copy (type, not token). It has relations with Book and Loans.
 * Notable properties beside usual fields include: 
 * 	active (false if the copy is archived)
 * 
 */
@Entity
public class Copy {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@ManyToOne(optional = false)
	@JsonIgnore
	private Book book;
	
	@OneToMany(mappedBy = "copy")
    private List<Loan> loans;
	
	@Override
	public String toString() {
		return "Copy [id=" + id + ", book=" + book + ", loans=" + loans + ", active=" + active + "]";
	}

	@Column(nullable = false)
	private boolean active;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public List<Loan> getLoans() {
		return loans;
	}

	public void setLoans(List<Loan> loans) {
		this.loans = loans;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	
}
