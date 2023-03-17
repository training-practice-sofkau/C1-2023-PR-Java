package ec.com.books.sofka.api.usecases;

import ec.com.books.sofka.api.domain.collection.Book;
import ec.com.books.sofka.api.domain.dto.BookDTO;
import ec.com.books.sofka.api.repository.IBookRepository;
import org.junit.jupiter.api.BeforeEach;
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
class SaveBookUsecaseTest {

    @Mock
    IBookRepository repoMock;

    ModelMapper modelMapper;

    SaveBookUsecase saveBookUseCase;

    @BeforeEach
    void init(){
        modelMapper = new ModelMapper();
        saveBookUseCase = new SaveBookUsecase(repoMock, modelMapper);
    }

    @Test
    void saveBook(){

        var book = new Book("2", "Principles", 2017);
        var bookDto = new BookDTO("2", "Principles", 2017);

        Mockito.when(repoMock.save(ArgumentMatchers.any(Book.class))).thenReturn(Mono.just(book));

        var response = saveBookUseCase.save(bookDto);

        StepVerifier.create(response)
                .expectNextCount(1)
                .verifyComplete();

        Mockito.verify(repoMock).save(ArgumentMatchers.any(Book.class));

    }
}