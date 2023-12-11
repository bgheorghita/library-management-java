package com.app.librarymanagement.api.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RegisterUser {
    private String username;
    private String password;
}
