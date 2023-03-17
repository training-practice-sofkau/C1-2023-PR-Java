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
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DeleteBookUseCaseTest {

    @Mock
    IBookRepository repoMock;

    ModelMapper modelMapper;

    DeleteBookUseCase deleteBookUseCase;

    @BeforeEach
    void init() {
        modelMapper = new ModelMapper();
        deleteBookUseCase = new DeleteBookUseCase(repoMock, modelMapper);
    }

    @Test
    @DisplayName("deleteBookById_Success")
    void deleteBookById(){
        var book = new Book("1", "title1", 2020);
        book.setId("1");

        Mockito.when(repoMock.findById(Mockito.any(String.class))).thenReturn(Mono.just(book));

        Mockito.when(repoMock.deleteById(Mockito.any(String.class))).thenReturn(Mono.empty());

        var service = deleteBookUseCase.apply("1");

        StepVerifier.create(service)
                .expectComplete()
                .verify();

        Mockito.verify(repoMock).findById("1");
        Mockito.verify(repoMock).deleteById("1");
    }

    @Test
    @DisplayName("deleteBookById_Failed")
    void deleteBookById_Failed(){

        Mockito.when(repoMock.findById(Mockito.any(String.class)))
                .thenReturn(Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())));

        var service = deleteBookUseCase.apply("1");

        StepVerifier.create(service)
                .expectErrorMatches(throwable -> throwable instanceof Throwable &&
                        throwable.getMessage().equals(HttpStatus.NOT_FOUND.toString()))
                .verify();

        Mockito.verify(repoMock).findById("1");
    }

}