package ec.com.books.sofka.api.usecases;

import ec.com.books.sofka.api.domain.collection.Book;
import ec.com.books.sofka.api.repository.IBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    ModelMapper mapper;
    GetBookByIdUsecase service;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        service = new  GetBookByIdUsecase(repoMock, mapper);
    }

    @Test
    @DisplayName("getBookByID_Success")
    void getBookByIDSuccess() {
        //Build the scenario you need
        var monoBook = Mono.just(new Book("1", "title1", 2020));
        var bookID = "anyId";

        Mockito.when(repoMock.findById(ArgumentMatchers.anyString())).thenReturn(monoBook);

        var response = service.apply(bookID);

        StepVerifier.create(response)
                .expectNextCount(1)
                .verifyComplete();

        Mockito.verify(repoMock).findById(ArgumentMatchers.anyString());
    }

    @Test
    @DisplayName("getBookById_NonSuccess")
    void getBookByNonExistingID(){
        var studentID = "ID1";

        Mockito.when(repoMock.findById(studentID)).thenReturn(Mono.empty());

        var response = service.apply(studentID);

        StepVerifier.create(response)
                .expectNextCount(0)
                .verifyComplete();
        Mockito.verify(repoMock).findById(studentID);
    }

}