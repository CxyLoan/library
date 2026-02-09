package ru.coxey.library.mapper;

import org.mapstruct.Mapper;
import ru.coxey.library.dto.BookDto;
import ru.coxey.library.entity.Book;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    Book dtoToModel(BookDto bookDto);

    BookDto modelToDto(Book book);

    List<BookDto> toListDto(List<Book> books);
}
