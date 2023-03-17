package ec.com.books.sofka.api.usecases;

import ec.com.books.sofka.api.domain.collection.Book;
import ec.com.books.sofka.api.domain.dto.BookDTO;
import ec.com.books.sofka.api.repository.IBookRepository;
import ec.com.books.sofka.api.usecases.interfaces.UpdateBook;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class UpdateBookUseCase implements UpdateBook {

    private final IBookRepository bookRepository;

    private final ModelMapper mapper;



    @Override
    public Mono<BookDTO> update(String id, BookDTO bookDTO) {
        return this.bookRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable()))
                .flatMap(book -> {
                    bookDTO.setId(book.getId());
                    return bookRepository.save(mapper.map(bookDTO, Book.class));
                })
                .map(book -> mapper.map(book, BookDTO.class));
    }
}
