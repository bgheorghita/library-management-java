package com.app.librarymanagement.api.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AddBookInventoryInput {
    private Long bookId;
    private Integer bookUnits;
}
