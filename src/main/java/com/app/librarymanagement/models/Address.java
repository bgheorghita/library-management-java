package com.app.librarymanagement.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "addresses")
@Getter
@Setter
public class Address extends BaseEntity {
    @Column(name = "postalCode", nullable = false)
    private String postalCode;

    @Column(name = "flatNumber", nullable = false)
    private String flatNumber;
}
