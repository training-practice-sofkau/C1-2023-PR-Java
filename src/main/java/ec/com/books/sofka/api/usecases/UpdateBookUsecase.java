package ec.com.books.sofka.api.usecases;

import ec.com.books.sofka.api.domain.dto.BookDTO;
import ec.com.books.sofka.api.repository.IBookRepository;
import ec.com.books.sofka.api.usecases.interfaces.UpdateBook;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@AllArgsConstructor
public class UpdateBookUsecase implements UpdateBook {

    private final IBookRepository bookRepository;

    private final SaveBookUsecase saveBookUsecase;
    @Override
    public Mono<BookDTO> update(String id, BookDTO bookDTO) {
        return this.bookRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())))
                .flatMap(book -> {
                    bookDTO.setId(book.getId());
                    return saveBookUsecase.save(bookDTO);
                });
    }
}
