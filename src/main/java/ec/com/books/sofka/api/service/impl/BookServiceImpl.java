package ec.com.books.sofka.api.service.impl;

import ec.com.books.sofka.api.domain.collection.Book;
import ec.com.books.sofka.api.domain.dto.BookDTO;
import ec.com.books.sofka.api.repository.IBookRepository;
import ec.com.books.sofka.api.service.IBookService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class BookServiceImpl implements IBookService {
     private final IBookRepository bookRepository;
     private final ModelMapper mapper;

    @Override
    public Flux<BookDTO> getAllBooks() {
        return this.bookRepository
                .findAll()
                //.switchIfEmpty(Flux.error(new Throwable(HttpStatus.NO_CONTENT.toString())))
                .switchIfEmpty(Flux.empty())
                .map(this::toDto);
                //.onErrorResume(throwable -> Flux.empty());
    }

    @Override
    public Mono<BookDTO> getBookById(String id) {
        return this.bookRepository
                .findById(id)
                .switchIfEmpty(Mono.empty())
                .map(this::toDto);
                //.onErrorResume(throwable -> Mono.empty());
    }

    @Override
    public Mono<BookDTO> saveBook(BookDTO bookDTO) {
        return this.bookRepository.save(toEntity(bookDTO)).map(this::toDto);
    }

    @Override
    public Mono<BookDTO> updateBook(String id, BookDTO bookDTO) {

        return this.bookRepository
                .findById(id)
                .switchIfEmpty(Mono.empty())
                .flatMap(book -> {
                    bookDTO.setId(book.getId());
                    return this.saveBook(bookDTO);
                });
    }

    @Override
    public Mono<String> deleteBook(String id) {

        return this.bookRepository
                .findById(id)
                .switchIfEmpty(Mono.empty())
                .flatMap(book -> this.bookRepository.deleteById(book.getId()))
                .flatMap(unused -> Mono.just(id));
    }

    @Override
    public BookDTO toDto(Book book) {
        return this.mapper.map(book, BookDTO.class);
    }

    @Override
    public Book toEntity(BookDTO bookDTO) {
        return this.mapper.map(bookDTO, Book.class);
    }
}
