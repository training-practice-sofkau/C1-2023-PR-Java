package ec.com.books.sofka.api.usecases;

import ec.com.books.sofka.api.domain.dto.BookDTO;
import ec.com.books.sofka.api.repository.IBookRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Service
@AllArgsConstructor
public class GetAllBooksUsecase implements Supplier<Flux<BookDTO>> {
    private final IBookRepository bookRepository;

    private final ModelMapper mapper;

    @Override
    public Flux<BookDTO> get() {
        return this.bookRepository
                .findAll()
                .switchIfEmpty(Flux.empty())
                .map(book -> mapper.map(book, BookDTO.class));
    }
}
