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
class SaveBookUsecaseTest {

    @Mock
    IBookRepository repoMock;

    ModelMapper modelMapper;

    SaveBookUsecase saveBookUsecase;

    @BeforeEach
    void init() {
        modelMapper = new ModelMapper();
        saveBookUsecase = new SaveBookUsecase(repoMock, modelMapper);
    }

    @Test
    @DisplayName("saveBook_Success")
    void saveBook(){
        var book = new Book("1", "title1", 2020);
        //book.setId("1");

        var monoBook = Mono.just(book);

        Mockito.when(repoMock.save(Mockito.any(Book.class))).thenReturn(monoBook);

        var service = saveBookUsecase.save(modelMapper.map(book, BookDTO.class));

        StepVerifier.create(service)
                .expectNextCount(1)
                .expectComplete()
                .verify();

        Mockito.verify(repoMock).save(book);
    }

    @Test
    @DisplayName("saveBook_Failed")
    void saveBook_Failed(){

        var book = new Book("1", "title1", 2020);

        Mockito.when(repoMock.save(Mockito.any(Book.class)))
                .thenReturn(Mono.error(new Throwable(HttpStatus.BAD_REQUEST.toString())));

        var service = saveBookUsecase.save(modelMapper.map(book, BookDTO.class));

        StepVerifier.create(service)
                .expectErrorMatches(throwable -> throwable instanceof Throwable &&
                        throwable.getMessage().equals(HttpStatus.BAD_REQUEST.toString()))
                .verify();

        Mockito.verify(repoMock).save(book);
    }

}