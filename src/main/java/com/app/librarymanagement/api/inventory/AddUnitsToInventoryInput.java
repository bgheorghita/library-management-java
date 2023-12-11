package com.app.librarymanagement.api.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AddUnitsToInventoryInput {
    private Long bookInventoryId;
    private Integer unitsToAdd;
}
