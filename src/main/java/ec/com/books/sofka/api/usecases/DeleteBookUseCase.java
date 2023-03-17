package ec.com.books.sofka.api.usecases;

import ec.com.books.sofka.api.repository.IBookRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
public class DeleteBookUseCase implements Function<String, Mono<Void>> {
    IBookRepository bookRepository;

    public DeleteBookUseCase(IBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Mono<Void> apply(String id) {
        return bookRepository
                .findById(id)
                .switchIfEmpty(Mono.empty())
                .flatMap(book -> this.bookRepository.deleteById(id));
    }

}
