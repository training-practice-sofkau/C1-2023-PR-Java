package ec.com.books.sofka.api.usecases;

import ec.com.books.sofka.api.NullParameterException.NullParameterException;
import ec.com.books.sofka.api.domain.collection.Book;
import ec.com.books.sofka.api.domain.dto.BookDTO;
import ec.com.books.sofka.api.repository.IBookRepository;
import ec.com.books.sofka.api.usecases.interfaces.SaveBook;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class SaveBookUseCase implements SaveBook {

    private final IBookRepository bookRepository;

    private final ModelMapper mapper;

    @Override
    public Mono<BookDTO> save(@NonNull BookDTO bookDTO) {

        if (bookDTO.getIsbn() == null) {
            return Mono.error(new NullParameterException("isbn cannot be null"));
        }
        if (bookDTO.getTitle() == null) {
            return Mono.error(new NullParameterException("title cannot be null"));
        }
        if (bookDTO.getYear() == null) {
            return Mono.error(new NullParameterException("year cannot be null"));
        }

        return this.bookRepository.save(mapper.map(bookDTO, Book.class))
                .switchIfEmpty(Mono.error(new Throwable("Something went wrong with the request")))
                .map(book -> mapper.map(book, BookDTO.class));
    }

}
