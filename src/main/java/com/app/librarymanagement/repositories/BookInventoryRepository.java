package com.app.librarymanagement.repositories;

import com.app.librarymanagement.models.BookInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookInventoryRepository extends JpaRepository<BookInventory, Long> {
    Optional<BookInventory> findByBookId(Long bookId);
}

