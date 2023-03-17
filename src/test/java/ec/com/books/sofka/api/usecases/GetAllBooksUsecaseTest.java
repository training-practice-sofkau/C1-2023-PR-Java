package ec.com.books.sofka.api.usecases;

import ec.com.books.sofka.api.domain.collection.Book;
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
import reactor.test.StepVerifier;


@ExtendWith(MockitoExtension.class)
class GetAllBooksUsecaseTest {
    @Mock
    IBookRepository repoMock;

    ModelMapper modelMapper;

    GetAllBooksUsecase service;

    @BeforeEach
    void init(){
        modelMapper = new ModelMapper();
        service = new  GetAllBooksUsecase(repoMock, modelMapper);
    }

    @Test
    @DisplayName("getAllBooks_Success")
    void getAllBooks() {
        //Build the scenario you need
        var fluxBooks = Flux.just(new Book("1", "title1", 2020), new Book("2", "title2", 2022));

        Mockito.when(repoMock.findAll()).thenReturn(fluxBooks);

        var response = service.get();

        StepVerifier.create(response)
                .expectNextCount(2)
                .verifyComplete();

        Mockito.verify(repoMock).findAll();
    }
}