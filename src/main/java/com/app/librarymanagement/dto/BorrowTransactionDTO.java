package com.app.librarymanagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class BorrowTransactionDTO {
    private Long id;
    private UserDTO user;
    private Long bookId;
    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;

}
