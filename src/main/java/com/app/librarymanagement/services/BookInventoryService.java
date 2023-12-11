package com.app.librarymanagement.services;

import com.app.librarymanagement.models.BookInventory;

import java.util.List;
import java.util.Optional;

public interface BookInventoryService {
    BookInventory save(Long bookId, Integer bookUnits);
    Optional<BookInventory> findByBookId(Long bookId);
    Optional<BookInventory> findById(Long id);
    BookInventory addUnitsToInventory(Long inventoryId, Integer unitsToAdd);
    BookInventory subtractUnitsFromInventory(Long inventoryId, Integer unitsToSubtract);
    List<BookInventory> findAll();
}
