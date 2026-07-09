package com.alimberdi.library.service;

import com.alimberdi.library.dto.BookCreateRequest;
import com.alimberdi.library.dto.BookUpdateRequest;
import com.alimberdi.library.entity.Author;
import com.alimberdi.library.entity.Book;
import com.alimberdi.library.exception.ResourceNotFoundException;
import com.alimberdi.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {

	private final BookRepository repository;
	private final AuthorService authorService;

	public List<Book> getAll() {
		return repository.findAll();
	}

	public Book getById(UUID id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("book with id " + id + " not found"));
	}

	@Transactional(rollbackFor = Exception.class)
	public Book create(BookCreateRequest request) {
		Author author = authorService.getByName(request.authorName());

		Book book = Book.builder()
				.title(request.title())
				.author(author)
				.publishedYear(request.publishedYear())
				.isbn(request.isbn())
				.build();

		return repository.save(book);
	}

	@Transactional(rollbackFor = Exception.class)
	public Book update(UUID id, BookUpdateRequest request) {
		Author author = authorService.getByName(request.authorName());

		Book book = getById(id);
		book.setTitle(request.title());
		book.setAuthor(author);
		book.setPublishedYear(request.publishedYear());
		book.setIsbn(request.isbn());

		return repository.save(book);
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(UUID id) {
		repository.deleteById(id);
	}

}
