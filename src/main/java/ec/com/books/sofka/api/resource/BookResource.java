package ec.com.books.sofka.api.resource;

import ec.com.books.sofka.api.domain.dto.BookDTO;
import ec.com.books.sofka.api.service.impl.BookServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


//@RestController
@AllArgsConstructor
public class BookResource {
    private final BookServiceImpl bookService;

    @GetMapping("/books")
    private Mono<ResponseEntity<Flux<BookDTO>>> getAll(){
        return this.bookService
                .getAllBooks()
                .switchIfEmpty(Flux.error(new Throwable(HttpStatus.NO_CONTENT.toString())))
                .collectList()
                .map(bookDTOS -> new ResponseEntity<>(Flux.fromIterable(bookDTOS), HttpStatus.OK))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(HttpStatus.NO_CONTENT)));

    }

    @GetMapping("/books/{id}")
    private Mono<ResponseEntity<BookDTO>> getById(@PathVariable String id){
        return this.bookService.getBookById(id)
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())))
                .map(bookDTO -> new ResponseEntity<>(bookDTO, HttpStatus.FOUND))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));

    }

    @PostMapping("/books")
    private Mono<ResponseEntity<BookDTO>> save(@RequestBody BookDTO bookDTO){

        return this.bookService
                .saveBook(bookDTO)
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NOT_ACCEPTABLE.toString())))
                .map(bookDTO1 -> new ResponseEntity<>(bookDTO1,HttpStatus.CREATED))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(bookDTO, HttpStatus.NOT_ACCEPTABLE)));

    }

    @PutMapping("/books/{id}")
    private Mono<ResponseEntity<BookDTO>> update(@PathVariable String id, @RequestBody BookDTO bookDTO){
        return this.bookService
                .updateBook(id, bookDTO)
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.BAD_REQUEST.toString())))
                .map(bookDTO1 -> new ResponseEntity<>(bookDTO1, HttpStatus.OK))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(bookDTO, HttpStatus.BAD_REQUEST)));
    }

    @DeleteMapping("/books/{id}")
    private Mono<ResponseEntity<String>> delete(@PathVariable String id){
        return this.bookService
                .deleteBook(id)
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())))
                .map(s-> new ResponseEntity<>("Deleted "+s, HttpStatus.OK))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }


}
