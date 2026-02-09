package ru.coxey.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.coxey.library.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    boolean existsBookByIsbn(String isbn);

    @Query(value = "SELECT 1", nativeQuery = true)
    Integer healthCheck();
}
