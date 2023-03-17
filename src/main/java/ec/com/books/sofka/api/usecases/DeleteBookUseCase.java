package ec.com.books.sofka.api.usecases;

import ec.com.books.sofka.api.repository.IBookRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class DeleteBookUseCase implements Function<String, Mono<String>> {
    private final IBookRepository bookRepository;

    private final ModelMapper mapper;

    @Override
    public Mono<String> apply(String id) {
        return this.bookRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())))
                .flatMap(book -> this.bookRepository.deleteById(book.getId())
                        .flatMap(unused -> Mono.just(id)));
    }
}
