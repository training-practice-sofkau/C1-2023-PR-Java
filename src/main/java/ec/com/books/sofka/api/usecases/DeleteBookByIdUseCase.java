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
public class DeleteBookByIdUseCase implements Function<String, Mono<Void>> {

    private final IBookRepository bookRepository;
    private final ModelMapper mapper;

    @Override
    public Mono<Void> apply(String ID) {
        return this.bookRepository
                .findById(ID)
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())))
                .flatMap(book -> this.bookRepository.deleteById(book.getId()));
                //.then(Mono.just(ID))).switchIfEmpty(Mono.empty());

    }

}
