package com.app.librarymanagement.controllers;

import com.app.librarymanagement.api.inventory.AddBookInventoryInput;
import com.app.librarymanagement.api.inventory.AddUnitsToInventoryInput;
import com.app.librarymanagement.api.inventory.SubtractUnitsFromInventoryInput;
import com.app.librarymanagement.models.BookInventory;
import com.app.librarymanagement.services.BookInventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Get all book inventories")
    @ApiResponse(responseCode = "200", description = "List of all book inventories",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BookInventory.class))))
    @GetMapping
    public ResponseEntity<List<BookInventory>> getAllBooks() {
        List<BookInventory> bookInventories = bookInventoryService.findAll();
        return new ResponseEntity<>(bookInventories, HttpStatus.OK);
    }

    @Operation(summary = "Add a book to the inventory")
    @ApiResponse(responseCode = "201", description = "Book added to inventory successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookInventory.class)))
    @PostMapping("/save")
    public ResponseEntity<BookInventory> addBookToInventory(@RequestBody AddBookInventoryInput addBookInventoryInput) {
        BookInventory bookInventory = bookInventoryService.save(addBookInventoryInput.getBookId(), addBookInventoryInput.getBookUnits());
        return new ResponseEntity<>(bookInventory, HttpStatus.CREATED);
    }

    @Operation(summary = "Add units to a book inventory")
    @ApiResponse(responseCode = "200", description = "Units added to book inventory successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookInventory.class)))
    @PostMapping("/add-units")
    public ResponseEntity<BookInventory> addUnitsToBookInventory(@RequestBody AddUnitsToInventoryInput addUnitsToInventoryInput) {
        BookInventory bookInventory = bookInventoryService.addUnitsToInventory(addUnitsToInventoryInput.getBookInventoryId(), addUnitsToInventoryInput.getUnitsToAdd());
        return new ResponseEntity<>(bookInventory, HttpStatus.OK);
    }

    @Operation(summary = "Subtract units from a book inventory")
    @ApiResponse(responseCode = "200", description = "Units subtracted from book inventory successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookInventory.class)))
    @PostMapping("/subtract-units")
    public ResponseEntity<BookInventory> subtractUnitsFromBookInventory(@RequestBody SubtractUnitsFromInventoryInput subtractUnitsFromInventoryInput) {
        BookInventory bookInventory = bookInventoryService.subtractUnitsFromInventory(subtractUnitsFromInventoryInput.getBookInventoryId(), subtractUnitsFromInventoryInput.getUnitsToSubtract());
        return new ResponseEntity<>(bookInventory, HttpStatus.OK);
    }
}
