package ec.com.books.sofka.api.usecases;

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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GetBookByIdUsecaseTest {

    @Mock
    IBookRepository repoMock;

    ModelMapper modelMapper;

    GetBookByIdUsecase service;

    @BeforeEach
    void init(){
        modelMapper = new ModelMapper();
        service = new GetBookByIdUsecase(repoMock, modelMapper);
    }

    @Test
    @DisplayName("getBookByID_Success")
    void getBookByID(){

        var book = new Book("1", "title1", 2020);
        book.setId("testId");

        Mockito.when(repoMock.findById(Mockito.any(String.class))).thenReturn(Mono.just(book));

        var response = service.apply("testId");

        StepVerifier.create(response)
                .expectNext(modelMapper.map(book, BookDTO.class))
                .expectComplete()
                .verify();

        Mockito.verify(repoMock).findById("testId");
    }

}