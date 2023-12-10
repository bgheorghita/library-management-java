package com.app.librarymanagement.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "books")
@Getter
@Setter
public class Book extends BaseEntity {
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "genre", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Genre genre;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "books_authors",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
    )
    private List<Author> authors;

    @Column(name = "description", nullable = false)
    private String description;
}
