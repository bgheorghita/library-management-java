package com.app.librarymanagement.services.impl;

import com.app.librarymanagement.dto.BookLendingInfoDTO;
import com.app.librarymanagement.dto.BorrowTransactionDTO;
import com.app.librarymanagement.dto.UserDTO;
import com.app.librarymanagement.models.Book;
import com.app.librarymanagement.models.BookInventory;
import com.app.librarymanagement.models.BorrowTransaction;
import com.app.librarymanagement.models.User;
import com.app.librarymanagement.repositories.BookInventoryRepository;
import com.app.librarymanagement.repositories.BookRepository;
import com.app.librarymanagement.repositories.BorrowTransactionRepository;
import com.app.librarymanagement.repositories.UserRepository;
import com.app.librarymanagement.services.BookInventoryService;
import com.app.librarymanagement.services.BorrowService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BorrowServiceImpl implements BorrowService {
    private final BorrowTransactionRepository borrowTransactionRepository;
    private final BookInventoryService bookInventoryService;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BookInventoryRepository bookInventoryRepository;
    @Override
    public BorrowTransactionDTO borrowBook(Long userId, Long bookId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found."));

        BookInventory inventory = bookInventoryService.findByBookId(bookId)
                .orElseThrow(() -> new RuntimeException("Inventory not found for bookId: " + bookId));

        if (inventory.getUnits() <= 0) {
            throw new RuntimeException("No available units to borrow for bookId: " + bookId);
        }

        inventory.setUnits(inventory.getUnits() - 1);
        bookInventoryRepository.save(inventory);

        BorrowTransaction transaction = new BorrowTransaction();
        transaction.setUser(user);
        transaction.setBook(book);
        transaction.setBorrowDate(LocalDateTime.now());
        BorrowTransaction savedTransaction = borrowTransactionRepository.save(transaction);

        return convertToBorrowTransactionDTO(savedTransaction);
    }


    @Override
    public BorrowTransactionDTO returnBook(Long userId, Long bookId) {
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
        BorrowTransaction savedTransaction = borrowTransactionRepository.save(transaction);

        bookInventoryService.returnBook(bookId);

        return convertToBorrowTransactionDTO(savedTransaction);
    }

    public List<BookLendingInfoDTO> getAllLoanInfo() {
        List<BorrowTransaction> transactions = borrowTransactionRepository.findAll(); // Fetch all transactions

        return transactions.stream()
                .map(this::convertToBookLendingInfoDTO) // Convert each transaction to DTO
                .collect(Collectors.toList());
    }

    private BookLendingInfoDTO convertToBookLendingInfoDTO(BorrowTransaction transaction) {
        BookLendingInfoDTO dto = new BookLendingInfoDTO();
        dto.setBookId(transaction.getBook().getId());
        dto.setBookTitle(transaction.getBook().getTitle());
        dto.setUserId(transaction.getUser().getId());
        dto.setUsername(transaction.getUser().getUsername());
        dto.setBorrowDate(transaction.getBorrowDate());
        dto.setReturnDate(transaction.getReturnDate());

        LocalDateTime endDate = (transaction.getReturnDate() != null) ? transaction.getReturnDate() : LocalDateTime.now();
        Duration duration = Duration.between(transaction.getBorrowDate(), endDate);

        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;

        String loanDuration;
        if (days > 0) {
            loanDuration = String.format("%d days, %d hours, %d minutes", days, hours, minutes);
        } else if (hours > 0) {
            loanDuration = String.format("%d hours, %d minutes", hours, minutes);
        } else {
            loanDuration = String.format("%d minutes", minutes);
        }

        dto.setLoanDuration(loanDuration);

        return dto;
    }
    private BorrowTransactionDTO convertToBorrowTransactionDTO(BorrowTransaction transaction) {
        BorrowTransactionDTO dto = new BorrowTransactionDTO();
        dto.setId(transaction.getId());
        dto.setUser(convertToUserDTO(transaction.getUser()));
        dto.setBookId(transaction.getBook().getId());
        dto.setBorrowDate(transaction.getBorrowDate());
        dto.setReturnDate(transaction.getReturnDate());
        return dto;
    }

    private UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        return userDTO;
    }
}
