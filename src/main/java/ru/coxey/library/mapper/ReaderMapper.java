package ru.coxey.library.mapper;

import org.mapstruct.Mapper;
import ru.coxey.library.dto.ReaderDto;
import ru.coxey.library.entity.Reader;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReaderMapper {

    Reader dtoToModel(ReaderDto readerDto);

    ReaderDto modelToDto(Reader reader);

    List<ReaderDto> toListDto(List<Reader> readers);

    ReaderDto copy(ReaderDto dto);
}
