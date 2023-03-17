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
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UpdateBookUseCaseTest {

    @Mock
    IBookRepository repoMock;

    ModelMapper modelMapper;

    UpdateBookUseCase service;

    @BeforeEach
    void init(){
        modelMapper = new ModelMapper();
        service = new UpdateBookUseCase(repoMock, modelMapper);
    }

    @Test
    @DisplayName("updateBook_Success")
    void updateBook(){

        Book book = new Book("1", "Test title", 2020);
        book.setId("testId");


        Mockito.when(repoMock.findById("testId")).thenReturn(Mono.just(book));
        Mockito.when(repoMock.save(book)).thenReturn(Mono.just(book));

        var response = service.update("testId", modelMapper.map(book, BookDTO.class));

        StepVerifier.create(response)
                .expectNext(modelMapper.map(book, BookDTO.class))
                .expectComplete()
                .verify();

        Mockito.verify(repoMock).save(book);
        Mockito.verify(repoMock).findById("testId");
    }

}