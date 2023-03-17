package ec.com.books.sofka.api.usecases;

import ec.com.books.sofka.api.domain.dto.BookDTO;
import ec.com.books.sofka.api.repository.IBookRepository;
import ec.com.books.sofka.api.usecases.interfaces.UpdateBook;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
//@Validated
public class UpdateBookUseCase implements UpdateBook {

    private final IBookRepository bookRepository;

    //private final ModelMapper mapper;

    private SaveBookUsecase saveBook;

    @Override
    public Mono<BookDTO> updateBook(String id, BookDTO bookDTO) {
        return this.bookRepository
                .findById(id)
                //.switchIfEmpty(Mono.empty())
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())))
                .flatMap(book -> {
                    bookDTO.setId(book.getId());
                    return saveBook.save(bookDTO);
                });
    }
}
