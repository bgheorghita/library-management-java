package com.app.librarymanagement.repositories;

import com.app.librarymanagement.models.BorrowTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BorrowTransactionRepository extends JpaRepository<BorrowTransaction, Long> {
    List<BorrowTransaction> findByUserIdAndBook_Id(Long userId, Long bookId);

}

