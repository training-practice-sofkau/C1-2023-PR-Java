package ec.com.books.sofka.api.service.impl;

import ec.com.books.sofka.api.domain.collection.Book;
import ec.com.books.sofka.api.domain.dto.BookDTO;
import ec.com.books.sofka.api.repository.IBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    @Mock
    IBookRepository repoMock;

    ModelMapper modelMapper;

    BookServiceImpl bookService;

    @BeforeEach
    void init(){
        modelMapper = new ModelMapper();
        bookService = new BookServiceImpl(repoMock, modelMapper);
    }

    //Remember that we will test the functionality of the service. What type are the returning for these?
    //Are the test will be success or failure
    @Test
    @DisplayName("getAllBooks_Success")
    void getAllBooks() {
        //Build the scenario you need
        var fluxBooks = Flux.just(new Book("1", "title1", 2020), new Book("2", "title2", 2022));

        Mockito.when(repoMock.findAll()).thenReturn(fluxBooks);

        var service = bookService.getAllBooks();

        StepVerifier.create(service)
                .expectNextCount(2)
                .verifyComplete();

        Mockito.verify(repoMock).findAll();

    }

    @Test
    @DisplayName("getAllBooks_Empty")
    void getAllBooks_Empty() {
        //Build the scenario you need

        Mockito.when(repoMock.findAll()).thenReturn(Flux.empty());

        var service = bookService.getAllBooks();

        StepVerifier.create(service)
                .expectNextCount(0)
                .verifyComplete();

        Mockito.verify(repoMock).findAll();

    }


    @Test
    @DisplayName("getBookById_Success")
    void getBookById() {
        var book = new Book("1", "title1", 2020);
        book.setId("1");

        Mockito.when(repoMock.findById(Mockito.any(String.class))).thenReturn(Mono.just(book));

        var service = bookService.getBookById("1");

        StepVerifier.create(service)
                .expectNext(modelMapper.map(book, BookDTO.class))
                .expectComplete()
                .verify();

        Mockito.verify(repoMock).findById("1");

    }

    @Test
    @DisplayName("getBookById_Failed")
    void getBookById_Failed() {

        Mockito.when(repoMock.findById(Mockito.any(String.class))).thenReturn(Mono.empty());

        var service = bookService.getBookById("1");

        StepVerifier.create(service)
                .expectNext()
                .expectComplete()
                .verify();

        Mockito.verify(repoMock).findById("1");

    }

    @Test
    void saveBook() {
    }

    @Test
    void updateBook() {
    }

    @Test
    void deleteBook() {
    }

    @Test
    void toDto() {
    }

    @Test
    void toEntity() {
    }

    @Test
    void isNull() {
    }
}