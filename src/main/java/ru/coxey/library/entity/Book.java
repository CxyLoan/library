package ru.coxey.library.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(exclude = "bookLoans")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_name", nullable = false)
    private String bookName;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "isbn", nullable = false, unique = true)
    private String isbn;

    @Column(name = "publication_year", nullable = false)
    private String publicationYear;

    @Column(name = "total_copies", nullable = false)
    private Integer totalCopies;

    @Column(name = "available_copies", nullable = false)
    private Integer availableCopies;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BookLoan> bookLoans = new ArrayList<>();

}
