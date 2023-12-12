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
    @JoinColumn(name = "book_inventory_id", nullable = false)
    private BookInventory bookInventory;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "borrow_date", nullable = false)
    private LocalDateTime borrowDate;

    @Column(name = "return_date")
    private LocalDateTime returnDate;
}
