package ec.com.books.sofka.api.usecases;

import ec.com.books.sofka.api.domain.collection.Book;
import ec.com.books.sofka.api.domain.dto.BookDTO;
import ec.com.books.sofka.api.repository.IBookRepository;
import ec.com.books.sofka.api.usecases.interfaces.UpdateBook;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UpdateBookUseCase implements UpdateBook {

    private final IBookRepository bookRepository;
    private final ModelMapper modelMapper;


    @Override
    public Mono<BookDTO> update(String id, BookDTO bookDTO) {
        return bookRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Book not found")))
                .flatMap(book -> {
                    bookDTO.setId(book.getId());
                    return bookRepository.save(modelMapper.map(bookDTO, Book.class));
                })
                .map(book -> modelMapper.map(book, BookDTO.class))
                .onErrorResume(error -> Mono.error(new RuntimeException("Student not found")));
    }
}
