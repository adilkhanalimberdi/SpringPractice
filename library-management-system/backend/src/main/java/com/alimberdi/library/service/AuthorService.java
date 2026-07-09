package com.alimberdi.library.service;

import com.alimberdi.library.entity.Author;
import com.alimberdi.library.exception.ResourceNotFoundException;
import com.alimberdi.library.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthorService {

	private final AuthorRepository repository;

	public List<Author> getAll() {
		return repository.findAll();
	}

	public Author getById(UUID id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("author with id " + id + " not found"));
	}

	public Author getByName(String name) {
		return repository.findByName(name)
				.orElseThrow(() -> new ResourceNotFoundException("author with name " + name + " not found"));
	}

}
