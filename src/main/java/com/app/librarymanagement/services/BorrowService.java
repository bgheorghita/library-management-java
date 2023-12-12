package com.app.librarymanagement.services;

import com.app.librarymanagement.models.BorrowTransaction;

public interface BorrowService {
    BorrowTransaction borrowBook(Long userId, Long inventoryId);
    BorrowTransaction returnBook(Long userId, Long inventoryId);
}
