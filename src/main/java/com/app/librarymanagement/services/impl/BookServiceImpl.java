package com.app.librarymanagement.services.impl;

import com.app.librarymanagement.models.Author;
import com.app.librarymanagement.models.Book;
import com.app.librarymanagement.repositories.AuthorRepository;
import com.app.librarymanagement.repositories.BookRepository;
import com.app.librarymanagement.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Override
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public Book updateBook(Long bookId, Book updatedBook) {
        Book existingBook = bookRepository.findById(bookId).orElse(null);

        if (existingBook != null) {
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setGenre(updatedBook.getGenre());

            List<Author> updatedAuthors = updatedBook.getAuthors().stream()
                    .map(author -> {
                        Long authorId = author.getId();
                        if(authorId == null) {
                            return author;
                        }

                        Author existingAuthor = authorRepository.findById(authorId).orElse(null);
                        if (existingAuthor != null) {
                            existingAuthor.setName(author.getName());
                            existingAuthor.setDescription(author.getDescription());
                            return existingAuthor;
                        } else {
                            return author;
                        }
                    })
                    .collect(Collectors.toList());

            existingBook.setAuthors(updatedAuthors);
            existingBook.setDescription(updatedBook.getDescription());
            return bookRepository.save(existingBook);
        } else {
            return null;
        }
    }
}
