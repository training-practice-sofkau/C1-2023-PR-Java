package ec.com.books.sofka.api.usecases.interfaces;

import ec.com.books.sofka.api.domain.dto.BookDTO;
import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface UpdateBook {
    Mono<BookDTO> updateBook(String id, @Valid BookDTO bookDTO);
}
