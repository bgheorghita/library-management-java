package com.app.librarymanagement.controllers;

import com.app.librarymanagement.models.BorrowTransaction;
import com.app.librarymanagement.services.BorrowService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/borrow")
@AllArgsConstructor
public class BorrowController {
    private final BorrowService borrowingService;

    @PostMapping()
    public ResponseEntity<BorrowTransaction> borrowBook(@RequestParam Long userId, @RequestParam Long inventoryId) {
        BorrowTransaction transaction = borrowingService.borrowBook(userId, inventoryId);
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/return")
    public ResponseEntity<BorrowTransaction> returnBook(@RequestParam Long userId, @RequestParam Long bookId) {
        BorrowTransaction transaction = borrowingService.returnBook(userId, bookId);
        return ResponseEntity.ok(transaction);
    }
}

