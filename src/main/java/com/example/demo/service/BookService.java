package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Book;

import com.example.demo.repository.IBookRepository;


@Service
public class BookService {
	
	@Autowired
	private IBookRepository repo;
	
	public Iterable<Book> findAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

}
