package ru.coxey.library.service.reader;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.coxey.library.audit.AuditService;
import ru.coxey.library.dto.ReaderDto;
import ru.coxey.library.entity.Reader;
import ru.coxey.library.exceptions.EmailNotUniqueException;
import ru.coxey.library.exceptions.PhoneNumberNotUniqueException;
import ru.coxey.library.exceptions.ReaderNotFoundException;
import ru.coxey.library.mapper.ReaderMapper;
import ru.coxey.library.repository.ReaderRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static ru.coxey.library.utils.MapperUtils.map;

@Service
@RequiredArgsConstructor
public class ReaderServiceImpl implements ReaderService {

    private final ReaderRepository repository;
    private final ReaderMapper mapper;
    private final AuditService auditService;

    @Override
    public List<ReaderDto> getAllReaders() {
        return mapper.toListDto(repository.findAll());
    }

    @Override
    public ReaderDto getReaderById(Long id) {
        return mapper.modelToDto(findReaderById(id));
    }

    @Override
    @Transactional
    public ReaderDto createReader(ReaderDto readerDto) {
        isUniqueEmailAndPhoneNumber(readerDto);
        final var reader = mapper.dtoToModel(readerDto);
        reader.setRegistrationDate(LocalDate.now());
        repository.save(reader);
        auditService.createReader(readerDto);
        return readerDto;
    }

    @Override
    @Transactional
    public ReaderDto updateReader(Long id, ReaderDto readerDto) {
        isUniqueEmailAndPhoneNumber(id ,readerDto);
        final var reader = findReaderById(id);
        mapping(reader, readerDto);
        repository.save(reader);
        return mapper.modelToDto(reader);
    }

    @Override
    @Transactional
    public void deleteReader(Long id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return;
        }
        throw new ReaderNotFoundException(String.format("Читатель с id {%s} не найден", id));
    }

    private Reader findReaderById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ReaderNotFoundException(String.format("Читатель с id {%s} не найден", id)));
    }

    private void isUniqueEmailAndPhoneNumber(ReaderDto readerDto) {
        if (repository.existsReaderByEmail(readerDto.getEmail())) {
            throw new EmailNotUniqueException("Email читателя не уникален");
        }
        if (repository.existsReaderByPhoneNumber(readerDto.getPhoneNumber())) {
            throw new PhoneNumberNotUniqueException("Номер телефона читателя не уникален");
        }
    }

    private void isUniqueEmailAndPhoneNumber(Long id, ReaderDto readerDto) {
        if (Objects.nonNull(repository.findReaderByIdNotAndEmail(id, readerDto.getEmail()))) {
            throw new EmailNotUniqueException("Email читателя не уникален");
        }
        if (Objects.nonNull(repository.findReaderByIdNotAndPhoneNumber(id, readerDto.getPhoneNumber()))) {
            throw new PhoneNumberNotUniqueException("Номер телефона читателя не уникален");
        }
    }

    private void mapping(Reader reader, ReaderDto readerDto) {
        reader.setFirstName(map(reader::getFirstName, readerDto::getFirstName));
        reader.setLastName(map(reader::getLastName, readerDto::getLastName));
        reader.setEmail(map(reader::getEmail, readerDto::getEmail));
        reader.setPhoneNumber(map(reader::getPhoneNumber, readerDto::getPhoneNumber));
    }
}
