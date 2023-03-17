package ec.com.books.sofka.api.usecases;

import ec.com.books.sofka.api.domain.dto.BookDTO;
import ec.com.books.sofka.api.repository.IBookRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class DeleteUsecase implements Function<String, Mono<String>> {
    private final IBookRepository bookRepository;

    private final ModelMapper mapper;

    @Override
    public Mono<String> apply(String id) {
        return this.bookRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Student not found")))
                .flatMap(book -> bookRepository.delete(book).thenReturn(id))
                .onErrorResume(Mono::error);
    }
}
