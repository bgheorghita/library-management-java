package com.app.librarymanagement.services;

import com.app.librarymanagement.dto.BookLendingInfoDTO;
import com.app.librarymanagement.dto.BorrowTransactionDTO;
import com.app.librarymanagement.models.BorrowTransaction;

import java.util.List;

public interface BorrowService {
    BorrowTransactionDTO borrowBook(Long userId, Long bookId);
    BorrowTransactionDTO returnBook(Long userId, Long inventoryId);
    List<BookLendingInfoDTO> getAllLoanInfo();
}
