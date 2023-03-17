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
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DeleteBookUseCaseTest {

    @Mock
    IBookRepository repoMock;



    DeleteBookUseCase service;

    @BeforeEach
    void init(){

        service = new DeleteBookUseCase(repoMock);
    }

    @Test
    @DisplayName("deleteBook_Success")
    void deleteBook(){

        Book book = new Book("1", "Test title", 2020);
        book.setId("testId");

        Mockito.when(repoMock.findById("testId")).thenReturn(Mono.just(book));
        //Mockito.when(repoMock.delete(book)).thenReturn(Mono.just("testId").then());
        Mockito.when(repoMock.delete(book)).thenReturn(Mono.empty());

        var response = service.apply("testId");

        StepVerifier.create(response)
                .expectNext("testId")
                .expectComplete()
                .verify();


    }

}