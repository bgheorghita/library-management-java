package com.app.librarymanagement.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "book_inventories")
@Getter
@Setter
public class BookInventory extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "book_id", nullable = false, unique = true)
    private Book book;

    @Column(name = "units", nullable = false)
    private Integer units;

    @Column(name = "available_units", nullable = false)
    private Integer availableUnits; // Available copies for borrowing
}
