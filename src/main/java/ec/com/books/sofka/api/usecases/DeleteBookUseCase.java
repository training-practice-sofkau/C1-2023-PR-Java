package ec.com.books.sofka.api.usecases;

import ec.com.books.sofka.api.repository.IBookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class DeleteBookUseCase implements Function<String, Mono<String>> {

    private final IBookRepository bookRepository;

    @Override
    public Mono<String> apply(String id) {
        return bookRepository.findById(id)
                .flatMap(book -> bookRepository.delete(book).thenReturn(id))
                .switchIfEmpty(Mono.error(new RuntimeException(id)));
    }
}
