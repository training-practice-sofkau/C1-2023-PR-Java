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
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SaveBookUsecaseTest {

    @Mock
    IBookRepository repoMock;

    ModelMapper modelMapper;

    SaveBookUsecase service;

    @BeforeEach
    void init(){
        modelMapper = new ModelMapper();
        service = new SaveBookUsecase(repoMock, modelMapper);
    }

    @Test
    @DisplayName("saveBook_Success")
    void saveBook(){

        var book = new Book("1", "Test title", 2020);
        book.setId("testId");

        Mockito.when(repoMock.save(book)).thenReturn(Mono.just(book));

        var response = service.save(modelMapper.map(book, BookDTO.class));

        StepVerifier.create(response)
                .expectNext(modelMapper.map(book, BookDTO.class))
                .expectComplete()
                .verify();

        Mockito.verify(repoMock).save(book);
    }

}