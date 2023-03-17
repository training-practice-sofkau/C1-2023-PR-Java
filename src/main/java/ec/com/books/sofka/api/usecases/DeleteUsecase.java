package ec.com.books.sofka.api.usecases;

import ec.com.books.sofka.api.repository.IBookRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class DeleteUsecase implements Function<String, Mono<String>> {
    private final IBookRepository bookRepository;
    private final ModelMapper modelMapper;
    @Override
    public Mono<String> apply(String id) {
        return this.bookRepository
                .findById(id)
                .switchIfEmpty(Mono.empty())
                .flatMap(book -> this.bookRepository.deleteById(book.getId())
                        .then(Mono.just(id)));
    }
}
