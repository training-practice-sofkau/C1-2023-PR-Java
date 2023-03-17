package ec.com.books.sofka.api.service.impl;

import ec.com.books.sofka.api.domain.collection.Book;
import ec.com.books.sofka.api.domain.dto.BookDTO;
import ec.com.books.sofka.api.repository.IBookRepository;
import ec.com.books.sofka.api.service.IBookService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.util.Objects;
import java.util.stream.Stream;

//@Service
@AllArgsConstructor
@Slf4j
public class BookServiceImpl implements IBookService {
     private final IBookRepository bookRepository;
     private final ModelMapper mapper;

    @Override
    public Flux<BookDTO> getAllBooks() {
        return this.bookRepository
                .findAll()
                .switchIfEmpty(Flux.empty())
                .map(this::toDto);
    }

    @Override
    public Mono<BookDTO> getBookById(String id) {
        return this.bookRepository
                .findById(id)
                .switchIfEmpty(Mono.empty())
                .map(this::toDto);
    }

    @Override
    public Mono<BookDTO> saveBook(BookDTO bookDTO) {
       return  isNull(bookDTO) ? Mono.empty() : this.bookRepository.save(toEntity(bookDTO))
                .switchIfEmpty(Mono.empty())
                .map(this::toDto);
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
                .flatMap(book -> this.bookRepository.deleteById(book.getId())
                        .then(Mono.just(id))).switchIfEmpty(Mono.empty());
                //.flatMap(Mono::just);

    }

    @Override
    public BookDTO toDto(Book book) {
        return this.mapper.map(book, BookDTO.class);
    }

    @Override
    public Book toEntity(BookDTO bookDTO) {
        return this.mapper.map(bookDTO, Book.class);
    }

    @Override
    public Boolean isNull(BookDTO bookDTO) {
        return Math.toIntExact(Stream.of(bookDTO.getIsbn(), bookDTO.getTitle(), bookDTO.getYear())
                .filter(Objects::isNull).count()) > 0;
    }
}
