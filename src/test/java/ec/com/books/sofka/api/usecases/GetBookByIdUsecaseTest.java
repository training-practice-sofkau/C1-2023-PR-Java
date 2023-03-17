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
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GetBookByIdUsecaseTest {

    @Mock
    IBookRepository repoMock;

    ModelMapper modelMapper;

    GetBookByIdUsecase getBookByIdUsecase;

    @BeforeEach
    void init() {
        modelMapper = new ModelMapper();
        getBookByIdUsecase = new GetBookByIdUsecase(repoMock, modelMapper);
    }

    @Test
    @DisplayName("getBookById_Success")
    void getBookById(){
        var book = new Book("1", "title1", 2020);
        book.setId("1");

        Mockito.when(repoMock.findById(Mockito.any(String.class))).thenReturn(Mono.just(book));

        var service = getBookByIdUsecase.apply("1");

        StepVerifier.create(service)
                .expectNext(modelMapper.map(book, BookDTO.class))
                .expectComplete()
                .verify();

        Mockito.verify(repoMock).findById("1");
    }

    @Test
    @DisplayName("getBookById_Failed")
    void getBookById_Failed() {

        Mockito.when(repoMock.findById(Mockito.any(String.class)))
                .thenReturn(Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())));

        var service = getBookByIdUsecase.apply("1");

        StepVerifier.create(service)
                .expectError()
                .verify();

        Mockito.verify(repoMock).findById("1");

    }
}