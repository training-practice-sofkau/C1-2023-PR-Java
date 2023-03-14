package ec.com.books.sofka.api.service;

import ec.com.books.sofka.api.domain.collection.Book;
import ec.com.books.sofka.api.domain.dto.BookDTO;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IBookService {
    //GETS
    Flux<BookDTO> getAllBooks();

    Mono<BookDTO> getBookById(String id);

    //POST
    Mono<BookDTO> saveBook(BookDTO bookDTO);

    //PUT
    Mono<BookDTO> updateBook(String id, BookDTO bookDTO);

    //DELETE
    Mono<String> deleteBook(String id);

    //Mappers
    BookDTO toDto(Book book);

    Book toEntity(BookDTO bookDTO);
}
