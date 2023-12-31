package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.saveCopyDto;
import com.example.demo.entity.Book;
import com.example.demo.entity.Copy;
import com.example.demo.entity.Employee;
import com.example.demo.service.BookService;
import com.example.demo.service.CopyService;

@RestController
@CrossOrigin(maxAge=3600)
public class CopyController {
	
	@Autowired
	private CopyService service;
	
	@Autowired
	private BookService bookService;
	
	@RequestMapping("copy/{id}")
	public Copy findCopyById(@PathVariable Long id) {
		return service.findById(id).get();
	}
	
	@RequestMapping("copies/all")
	public Iterable<Copy> findAll(){
		return service.findAll();
	}
	
	/**
	 * Create a new copy for a book.
	 * 
	 * @param dto The DTO containing the copy details.
	 * @return The created copy.
	 */
	@RequestMapping(value = "copy/create", method = RequestMethod.POST)
	public Copy create(@RequestBody saveCopyDto dto) {
	    // Find the book by ID
	    Optional<Book> bookOptional = bookService.findById(dto.getBookId());
	    
	    Copy copy = new Copy();
	    
	    copy.setBook(bookOptional.get());
	    copy.setActive(true);
	    
	    return service.createCopy(copy);
	}

	/**
	 * Update an existing copy.
	 * 
	 * @param id            The ID of the copy to update.
	 * @param updatedCopy   The updated copy details.
	 */
	@RequestMapping(value = "copy/update/{id}", method = RequestMethod.PATCH)
	public void update(@PathVariable Long id, @RequestBody Copy updatedCopy) {
	    // Find the existing copy by ID
	    Optional<Copy> existingCopy = service.findById(id);
	    
	    if (existingCopy.isPresent()) {
	        Copy copy = existingCopy.get();
	        copy.setActive(updatedCopy.isActive());     
	        
	        service.updateCopy(copy);
	    }
	}
	
	/**
	 * Searches copies based on the given book id.
	 * 
	 * @param searchTerm the term to search for
	 * @return an iterable collection of copy matching the book id
	 */
	@RequestMapping("copies/search")
	public Iterable<Copy> searchCopies(@RequestParam long bookId) {
	    return service.searchCopies(bookId);
	}
	
	/**
	 * Searches copies with active = true based on the given book id.
	 * 
	 * @param searchTerm the term to search for
	 * @return an iterable collection of copy matching the book id with isActive()
	 */
	@RequestMapping("copies/active")
	public Iterable<Copy> getActiveCopies(@RequestParam long bookId) {
	    return service.getActiveCopiesWithoutLoan(bookId);
	}

	@RequestMapping("copies/{copyId}/status")
	public ResponseEntity<String> getCopyStatus(@PathVariable Long copyId) {
        Optional<Copy> copy = service.findById(copyId);

        if (copy.isPresent()) {
            Optional<Employee> borrower = service.getBorrowerForCopy(copy.get());

            if (borrower.isPresent()) {
                return ResponseEntity.ok(borrower.get().getFirstName() + " " + borrower.get().getLastName());
            } else {
                return ResponseEntity.ok("-");
            }
        } else {
        	return ResponseEntity.notFound().build();
        }
    }
}

