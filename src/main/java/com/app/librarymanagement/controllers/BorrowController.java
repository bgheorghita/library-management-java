package com.app.librarymanagement.controllers;

import com.app.librarymanagement.dto.BookLendingInfoDTO;
import com.app.librarymanagement.dto.BorrowTransactionDTO;
import com.app.librarymanagement.models.BorrowTransaction;
import com.app.librarymanagement.services.BorrowService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/borrow")
@AllArgsConstructor
public class BorrowController {
    private final BorrowService borrowingService;

    @PostMapping()
    public ResponseEntity<BorrowTransactionDTO> borrowBook(@RequestParam Long userId, @RequestParam Long bookId) {
        BorrowTransactionDTO transactionDTO = borrowingService.borrowBook(userId, bookId);
        return ResponseEntity.ok(transactionDTO);
    }

    @PostMapping("/return")
    public ResponseEntity<BorrowTransactionDTO> returnBook(@RequestParam Long userId, @RequestParam Long bookId) {
        BorrowTransactionDTO transactionDTO = borrowingService.returnBook(userId, bookId);
        return ResponseEntity.ok(transactionDTO);
    }

    @GetMapping("info")
    public ResponseEntity<List<BookLendingInfoDTO>> getAllLoanInfo() {
        List<BookLendingInfoDTO> lendingInfo = borrowingService.getAllLoanInfo();
        return ResponseEntity.ok(lendingInfo);
    }
}

