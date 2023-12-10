package com.app.librarymanagement.repositories;

import com.app.librarymanagement.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
