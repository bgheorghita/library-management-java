package com.app.librarymanagement.services;

import com.app.librarymanagement.models.Book;

import java.util.List;

public interface BookService {
    Book addBook(Book book);
    List<Book> getAllBooks();
    Book getBookById(Long id);
}
