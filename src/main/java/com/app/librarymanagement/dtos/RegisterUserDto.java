package com.app.librarymanagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RegisterUserDto {
    private String username;
    private String password;
}
