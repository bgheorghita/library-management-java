package com.app.librarymanagement.controllers;

import com.app.librarymanagement.api.inventory.AddBookInventoryInput;
import com.app.librarymanagement.api.inventory.AddUnitsToInventoryInput;
import com.app.librarymanagement.api.inventory.SubtractUnitsFromInventoryInput;
import com.app.librarymanagement.models.BookInventory;
import com.app.librarymanagement.services.BookInventoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/book-inventories")
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class BookInventoryController {
    private final BookInventoryService bookInventoryService;

    @GetMapping
    public ResponseEntity<List<BookInventory>> getAllBooks() {
        List<BookInventory> bookInventories = bookInventoryService.findAll();
        return new ResponseEntity<>(bookInventories, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<BookInventory> addBookToInventory(@RequestBody AddBookInventoryInput addBookInventoryInput) {
        BookInventory bookInventory = bookInventoryService.save(addBookInventoryInput.getBookId(), addBookInventoryInput.getBookUnits());
        return new ResponseEntity<>(bookInventory, HttpStatus.CREATED);
    }

    @PostMapping("/add-units")
    public ResponseEntity<BookInventory> addUnitsToBookInventory(@RequestBody AddUnitsToInventoryInput addUnitsToInventoryInput) {
        BookInventory bookInventory = bookInventoryService.addUnitsToInventory(addUnitsToInventoryInput.getBookInventoryId(), addUnitsToInventoryInput.getUnitsToAdd());
        return new ResponseEntity<>(bookInventory, HttpStatus.OK);
    }

    @PostMapping("/subtract-units")
    public ResponseEntity<BookInventory> subtractUnitsFromBookInventory(@RequestBody SubtractUnitsFromInventoryInput subtractUnitsFromInventoryInput) {
        BookInventory bookInventory = bookInventoryService.subtractUnitsFromInventory(subtractUnitsFromInventoryInput.getBookInventoryId(), subtractUnitsFromInventoryInput.getUnitsToSubtract());
        return new ResponseEntity<>(bookInventory, HttpStatus.OK);
    }
}
