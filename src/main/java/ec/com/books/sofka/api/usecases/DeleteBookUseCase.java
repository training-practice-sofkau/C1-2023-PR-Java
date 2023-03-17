package ec.com.books.sofka.api.usecases;

import ec.com.books.sofka.api.repository.IBookRepository;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class DeleteBookUseCase implements Function<String, Mono<String>> {
    IBookRepository bookRepository;

    public DeleteBookUseCase(IBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Mono<String> apply(String id) {
        return bookRepository
                .findById(id)
                .switchIfEmpty(Mono.empty())
                .flatMap(book -> this.bookRepository.deleteById(book.getId())
                        .then(Mono.just(id))).switchIfEmpty(Mono.empty());
    }

}
