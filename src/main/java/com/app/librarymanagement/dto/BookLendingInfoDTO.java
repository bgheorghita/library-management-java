package com.app.librarymanagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BookLendingInfoDTO {
    private Long bookId;
    private String bookTitle;
    private Long userId;
    private String username;
    private LocalDateTime borrowDate;
    private String loanDuration;
    private LocalDateTime returnDate;
}
