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
    ModelMapper mapper;
    SaveBookUsecase service;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        service = new SaveBookUsecase(repoMock, mapper);
    }

    @Test
    @DisplayName("saveBook_Success")
    void saveBook(){
        var bookDTO =mapper.map(new Book("1", "title1", 2020), BookDTO.class);
        bookDTO.setId("AnyID");

        Mockito.when(repoMock.save(mapper.map(bookDTO, Book.class)))
                .thenAnswer(invocationOnMock -> Mono.just(invocationOnMock.getArgument(0)));

        var response = service.save(bookDTO);

        StepVerifier.create(response)
                .expectNextCount(1)
                .verifyComplete();
        Mockito.verify(repoMock).save(mapper.map(bookDTO, Book.class));
    }
}