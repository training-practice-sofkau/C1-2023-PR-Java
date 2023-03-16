package ec.com.books.sofka.api.usecases;

import ec.com.books.sofka.api.domain.dto.BookDTO;
import ec.com.books.sofka.api.repository.IBookRepository;
import ec.com.books.sofka.api.usecases.interfaces.UpdateBook;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UpdateBookUseCase implements UpdateBook {

    private final IBookRepository bookRepository;
    private final SaveBookUseCase saveBookUseCase;

    public Mono<BookDTO> update(String id, BookDTO bookDTO) {
        return bookRepository.findById(id)
                .switchIfEmpty(Mono.error(new Throwable("Not found")))
                .flatMap(book -> {
                    bookDTO.setId(book.getId());
                    return saveBookUseCase.save(bookDTO);
                });
    }

}
