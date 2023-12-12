package com.app.librarymanagement.services.impl;

import com.app.librarymanagement.models.BookInventory;
import com.app.librarymanagement.models.BorrowTransaction;
import com.app.librarymanagement.models.User;
import com.app.librarymanagement.repositories.BookInventoryRepository;
import com.app.librarymanagement.repositories.BorrowTransactionRepository;
import com.app.librarymanagement.repositories.UserRepository;
import com.app.librarymanagement.services.BookInventoryService;
import com.app.librarymanagement.services.BorrowService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BorrowServiceImpl implements BorrowService {
    private final BorrowTransactionRepository borrowTransactionRepository;
    private final BookInventoryService bookInventoryService;
    private final UserRepository userRepository;


    @Override
    public BorrowTransaction borrowBook(Long userId, Long inventoryId) {
        BookInventory inventory = bookInventoryService.subtractUnitsFromInventory(inventoryId, 1);

        BorrowTransaction transaction = new BorrowTransaction();
        transaction.setBook(inventory.getBook());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));
        transaction.setUser(user);
        transaction.setBorrowDate(LocalDateTime.now());

        return borrowTransactionRepository.save(transaction);
    }


    @Override
    public BorrowTransaction returnBook(Long userId, Long bookId) {
        List<BorrowTransaction> transactions = borrowTransactionRepository
                .findByUserIdAndBook_Id(userId, bookId)
                .stream()
                .filter(t -> t.getReturnDate() == null)
                .toList();

        if (transactions.isEmpty()) {
            throw new IllegalStateException("Transaction not found.");
        }

        BorrowTransaction transaction = transactions.get(0);
        transaction.setReturnDate(LocalDateTime.now());
        borrowTransactionRepository.save(transaction);

        bookInventoryService.returnBook(bookId);
        return transaction;
    }


}
