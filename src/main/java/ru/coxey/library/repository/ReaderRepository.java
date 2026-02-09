package ru.coxey.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.coxey.library.entity.Reader;

public interface ReaderRepository extends JpaRepository<Reader, Long> {

    boolean existsReaderByEmail(String email);
    boolean existsReaderByPhoneNumber(String phoneNumber);
    Reader findReaderByIdNotAndPhoneNumber(Long id, String phoneNumber);
    Reader findReaderByIdNotAndEmail(Long id, String email);
}
