package ec.com.books.sofka.api.usecases.interfaces;

import ec.com.books.sofka.api.domain.dto.BookDTO;
import reactor.core.publisher.Mono;

public interface UpdateBook {
    Mono<BookDTO> update(String id, BookDTO bookDTO);
}
