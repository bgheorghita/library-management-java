package com.app.librarymanagement.services.impl;

import com.app.librarymanagement.models.Book;
import com.app.librarymanagement.models.BookInventory;
import com.app.librarymanagement.repositories.BookInventoryRepository;
import com.app.librarymanagement.repositories.BookRepository;
import com.app.librarymanagement.services.BookInventoryService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookInventoryServiceImpl implements BookInventoryService {
    private final BookInventoryRepository bookInventoryRepository;
    private final BookRepository bookRepository;

    @Override
    public BookInventory save(Long bookId, Integer bookUnits) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found."));
        if(bookUnits < 0) {
            throw new RuntimeException("Invalid bookUnits");
        }
        BookInventory bookInventory = new BookInventory();
        bookInventory.setBook(book);
        bookInventory.setUnits(bookUnits);
        return bookInventoryRepository.save(bookInventory);
    }

    @Override
    public Optional<BookInventory> findByBookId(Long bookId) {
        return bookInventoryRepository.findByBookId(bookId);
    }

    @Override
    public Optional<BookInventory> findById(Long id) {
        return bookInventoryRepository.findById(id);
    }

    @Override
    @Transactional
    public BookInventory addUnitsToInventory(Long inventoryId, Integer unitsToAdd) {
        Optional<BookInventory> optionalBookInventory = findById(inventoryId);
        if(optionalBookInventory.isEmpty()) {
            throw new RuntimeException("Book inventory not found.");
        }

        if(unitsToAdd != null && unitsToAdd > 0) {
            BookInventory bookInventory = optionalBookInventory.get();
            bookInventory.setUnits(bookInventory.getUnits() + unitsToAdd);
            return bookInventory;
        } else {
            throw new RuntimeException("Units to add must be a positive number.");
        }
    }

    @Override
    @Transactional
    public BookInventory subtractUnitsFromInventory(Long inventoryId, Integer unitsToSubtract) {
        Optional<BookInventory> optionalBookInventory = findById(inventoryId);
        if(optionalBookInventory.isEmpty()) {
            throw new RuntimeException("Book inventory not found.");
        }

        BookInventory bookInventory = optionalBookInventory.get();
        Integer currentUnits = bookInventory.getUnits();
        if(unitsToSubtract != null && unitsToSubtract > 0 && currentUnits >= unitsToSubtract) {
            bookInventory.setUnits(bookInventory.getUnits() - unitsToSubtract);
            return bookInventory;
        } else {
            throw new RuntimeException("Units to subtract must be a positive number and must not be greater than the current book units.");
        }
    }

    @Override
    public List<BookInventory> findAll() {
        return bookInventoryRepository.findAll();
    }

    @Override
    public BookInventory borrowBook(Long inventoryId) {
        return bookInventoryRepository.findById(inventoryId).map(inventory -> {
            if (inventory.getUnits() > 0) {
                inventory.setUnits(inventory.getUnits() - 1);
                return bookInventoryRepository.save(inventory);
            } else {
                throw new IllegalStateException("No available units to borrow.");
            }
        }).orElseThrow(() -> new IllegalStateException("Inventory not found."));
    }
    @Override
    public BookInventory returnBook(Long bookId) {
        BookInventory inventory = bookInventoryRepository.findByBookId(bookId)
                .orElseThrow(() -> new IllegalStateException("Inventory for the given book not found."));

        inventory.setUnits(inventory.getUnits() + 1);
        return bookInventoryRepository.save(inventory);
    }

}
