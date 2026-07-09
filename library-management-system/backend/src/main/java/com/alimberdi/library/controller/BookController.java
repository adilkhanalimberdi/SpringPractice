package com.alimberdi.library.controller;

import com.alimberdi.library.dto.BookCreateRequest;
import com.alimberdi.library.dto.BookUpdateRequest;
import com.alimberdi.library.entity.Book;
import com.alimberdi.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
public class BookController {

	private final BookService bookService;

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<List<Book>> getAllBooks() {
		return ResponseEntity
				.ok(bookService.getAll());
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<Book> getBook(@PathVariable UUID id) {
		return ResponseEntity
				.ok(bookService.getById(id));
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Book> createBook(@RequestBody BookCreateRequest request) {
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(bookService.create(request));
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Book> updateBook(@PathVariable UUID id, @RequestBody BookUpdateRequest request) {
		return ResponseEntity
				.ok(bookService.update(id, request));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteBook(@PathVariable UUID id) {
		bookService.delete(id);
		return ResponseEntity
				.noContent()
				.build();
	}

}
