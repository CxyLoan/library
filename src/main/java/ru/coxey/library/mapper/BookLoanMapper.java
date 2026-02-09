package ru.coxey.library.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.coxey.library.dto.BookLoanDto;
import ru.coxey.library.entity.BookLoan;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookLoanMapper {

    @Mapping(source = "book.bookName", target = "bookName")
    @Mapping(source = "book.author", target = "author")
    @Mapping(source = "book.publicationYear", target = "publicationYear")
    @Mapping(source = "loanDate", target = "loanDate")
    @Mapping(source = "dueDate", target = "dueDate")
    @Mapping(source = "returnDate", target = "returnDate")
    @Mapping(source = "status", target = "status")
    BookLoanDto toBookLoanDto(BookLoan bookLoan);

    List<BookLoanDto> toBookLoanDtoList(List<BookLoan> bookLoans);
}
