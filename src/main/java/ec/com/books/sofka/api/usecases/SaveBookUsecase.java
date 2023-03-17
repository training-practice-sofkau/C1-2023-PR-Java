package ec.com.books.sofka.api.usecases;

import ec.com.books.sofka.api.domain.collection.Book;
import ec.com.books.sofka.api.domain.dto.BookDTO;
import ec.com.books.sofka.api.repository.IBookRepository;
import ec.com.books.sofka.api.usecases.interfaces.SaveBook;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
//@Validated
public class SaveBookUsecase implements SaveBook {

    private final IBookRepository bookRepository;

    private final ModelMapper mapper;
    @Override
    public Mono<BookDTO> save(BookDTO bookDTO) {
        return this.bookRepository.save(mapper.map(bookDTO, Book.class))
                //.switchIfEmpty(Mono.error(new Throwable(HttpStatus.NOT_ACCEPTABLE.toString())))
                .switchIfEmpty(Mono.empty())
                .map(book -> mapper.map(book, BookDTO.class));
    }

}
