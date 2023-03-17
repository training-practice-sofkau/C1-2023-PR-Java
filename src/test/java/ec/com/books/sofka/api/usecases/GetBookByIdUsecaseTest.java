package ec.com.books.sofka.api.usecases;

import ec.com.books.sofka.api.domain.collection.Book;
import ec.com.books.sofka.api.repository.IBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
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
        service = new GetBookByIdUsecase(repoMock,modelMapper);
    }

    @Test
    void getBookById(){

        var monoBook = Mono.just(new Book("1", "title1", 2020));

        Mockito.when(repoMock.findById(ArgumentMatchers.anyString())).thenReturn(monoBook);

        var response = service.apply("bookId");

        StepVerifier.create(response)
                .expectNextCount(1)
                .verifyComplete();

        Mockito.verify(repoMock).findById("bookId");
    }

}