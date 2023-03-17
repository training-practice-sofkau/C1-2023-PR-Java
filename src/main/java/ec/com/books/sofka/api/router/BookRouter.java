package ec.com.books.sofka.api.router;

import ec.com.books.sofka.api.domain.dto.BookDTO;
import ec.com.books.sofka.api.usecases.*;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class BookRouter {
    @Bean
    public RouterFunction<ServerResponse> getAllBooks(GetAllBooksUsecase getAllBooksUsecase){
        return route(GET("/books"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllBooksUsecase.get(), BookDTO.class))
                        .onErrorResume(throwable -> ServerResponse.noContent().build()));
    }

    @Bean
    public RouterFunction<ServerResponse> getBookById(GetBookByIdUsecase getBookByIdUsecase){
        return route(GET("/books/{id}"),
                request -> getBookByIdUsecase.apply(request.pathVariable("id"))
                        .flatMap(bookDTO -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(bookDTO))
                        .onErrorResume(throwable -> ServerResponse.notFound().build()));
    }

    @Bean
    //@Validated
    public RouterFunction<ServerResponse> saveBook(SaveBookUsecase saveBookUsecase){
        return route(POST("/books").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(BookDTO.class)
                        .flatMap(bookDTO -> saveBookUsecase.save(bookDTO)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))

                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.BAD_REQUEST).build())));
    }

    @Bean
    //@Validated
    public RouterFunction<ServerResponse> updateBook(UpdateBookUseCase updateBookUseCase){
        return route(PUT("/books/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(BookDTO.class)
                        .flatMap(bookDTO -> updateBookUseCase.updateBook(request.pathVariable("id"),
                                        bookDTO)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).build())));
    }

    @Bean
    public RouterFunction<ServerResponse> deleteBook(DeleteBookUseCase deleteBookUseCase){
        return route(DELETE("/books/{id}"),
                request -> deleteBookUseCase.apply(request.pathVariable("id"))
                        .thenReturn(ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue("book deleted"))
                        .flatMap(serverResponseMono -> serverResponseMono)
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).build()));
    }


}
