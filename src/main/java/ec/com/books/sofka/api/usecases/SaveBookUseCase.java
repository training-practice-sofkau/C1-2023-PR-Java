package ec.com.books.sofka.api.usecases;

import ec.com.books.sofka.api.domain.collection.Book;
import ec.com.books.sofka.api.domain.dto.BookDTO;
import ec.com.books.sofka.api.repository.IBookRepository;
import ec.com.books.sofka.api.usecases.interfaces.SaveBook;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class SaveBookUseCase implements SaveBook {

    private final IBookRepository bookRepository;
    private final ModelMapper mapper;

    @Override
    public Mono<BookDTO> save(BookDTO bookDTO) {
        return this.bookRepository.save(mapper.map(bookDTO, Book.class))
                .switchIfEmpty(Mono.empty())
                .map(book -> mapper.map(book, BookDTO.class));
    }

}
