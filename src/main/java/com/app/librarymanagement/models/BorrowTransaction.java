package com.app.librarymanagement.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "borrow_transactions")
@Getter
@Setter
public class BorrowTransaction extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "borrow_date", nullable = false)
    private LocalDateTime borrowDate;

    @Column(name = "return_date")
    private LocalDateTime returnDate;
}
