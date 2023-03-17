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
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DeleteBookUseCaseTest {

    @Mock
    IBookRepository repoMock;

    ModelMapper modelMapper;

    DeleteBookUseCase service;

    @BeforeEach
    void init(){
        modelMapper = new ModelMapper();
        service = new DeleteBookUseCase(repoMock);
    }

    @Test
    void deleteBook(){

        var book = new Book("1", "title1", 2020);
        var monoBook = Mono.just(new Book("1", "title1", 2020));

        Mockito.when(repoMock.findById(ArgumentMatchers.anyString())).thenReturn(monoBook);

        Mockito.when(repoMock.delete(ArgumentMatchers.any(Book.class))).thenReturn(Mono.empty());

        var response = service.delete(ArgumentMatchers.anyString());

        StepVerifier.create(response)
                .expectNextCount(0)
                .verifyComplete();

        Mockito.verify(repoMock).findById(ArgumentMatchers.anyString());
        Mockito.verify(repoMock).delete(ArgumentMatchers.any(Book.class));

    }

    @Test
    @DisplayName("deleteBook_Fail")
    void deleteBook_Fail() {

        Mockito.when(repoMock.findById(ArgumentMatchers.anyString())).thenReturn(Mono.empty());

        var response = service.delete("");

        StepVerifier.create(response)
                .expectError(Throwable.class);

        Mockito.verify(repoMock).findById("");

    }

    }