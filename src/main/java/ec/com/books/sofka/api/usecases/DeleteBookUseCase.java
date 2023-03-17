package ec.com.books.sofka.api.usecases;

import ec.com.books.sofka.api.repository.IBookRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class DeleteBookUseCase implements Function<String, Mono<Void>> {
    private final IBookRepository bookRepository;

    private final ModelMapper mapper;

    @Override
    public Mono<Void> apply(String id) {
        return this.bookRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())))
                .flatMap(book -> this.bookRepository.deleteById(book.getId()));
    }
}
