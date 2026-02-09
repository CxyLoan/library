package ru.coxey.library.utils;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import ru.coxey.library.entity.Book;

@UtilityClass
public class BookSpecificationUtils {

    public static Specification<Book> hasBookName(String bookName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("bookName"), bookName);
    }

    public static Specification<Book> hasAuthor(String author) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("author"), author);
    }

    public static Specification<Book> hasIsbn(String isbn) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("isbn"), isbn);
    }

    public static Specification<Book> hasYear(String year) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("publicationYear"), year);
    }

    public static Specification<Book> hasAvailable(Boolean available) {
        return (root, query, criteriaBuilder) -> {
            if (available) {
                return criteriaBuilder.greaterThan(root.get("availableCopies"), 0);
            } else {
                return criteriaBuilder.equal(root.get("availableCopies"), 0);
            }
        };
    }
}
