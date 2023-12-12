package com.app.librarymanagement.services.impl;

import com.app.librarymanagement.models.BookInventory;
import com.app.librarymanagement.models.BorrowTransaction;
import com.app.librarymanagement.repositories.BookInventoryRepository;
import com.app.librarymanagement.repositories.BorrowTransactionRepository;
import com.app.librarymanagement.services.BookInventoryService;
import com.app.librarymanagement.services.BorrowService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class BorrowServiceImpl implements BorrowService {
    private final BookInventoryRepository bookInventoryRepository;
    private final BorrowTransactionRepository borrowTransactionRepository;
    private final BookInventoryService bookInventoryService;


    @Override
    public BorrowTransaction borrowBook(Long userId, Long inventoryId) {
        BookInventory inventory = bookInventoryService.borrowBook(inventoryId);

        BorrowTransaction transaction = new BorrowTransaction();
        transaction.setBookInventory(inventory);
        transaction.setUserId(userId);
        transaction.setBorrowDate(LocalDateTime.now());
        bookInventoryService.subtractUnitsFromInventory(inventoryId, 1);
        return borrowTransactionRepository.save(transaction);
    }

    @Override
    public BorrowTransaction returnBook(Long userId, Long inventoryId) {
        BorrowTransaction transaction = borrowTransactionRepository
                .findByUserIdAndBookInventory_Id(userId, inventoryId)
                .orElseThrow(() -> new IllegalStateException("Transaction not found."));

        transaction.setReturnDate(LocalDateTime.now());
        borrowTransactionRepository.save(transaction);

        bookInventoryService.returnBook(inventoryId);
        bookInventoryService.addUnitsToInventory(inventoryId, 1);
        return transaction;
    }
}
