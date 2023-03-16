package ec.com.books.sofka.api.usecases;

import ec.com.books.sofka.api.domain.dto.BookDTO;
import ec.com.books.sofka.api.repository.IBookRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class GetBookByIdUseCase implements Function<String, Mono<BookDTO>> {
    private final IBookRepository bookRepository;

    private final ModelMapper mapper;
    @Override
    public Mono<BookDTO> apply(String id) {
        return this.bookRepository
                .findById(id)
                .switchIfEmpty(Mono.empty())
                .map(book-> mapper.map(book, BookDTO.class));
    }
}
