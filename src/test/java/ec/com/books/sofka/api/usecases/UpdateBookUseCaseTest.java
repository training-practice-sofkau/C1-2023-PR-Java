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
class UpdateBookUseCaseTest {

    @Mock
    IBookRepository repoMock;

    ModelMapper modelMapper;

    UpdateBookUseCase service;

    @BeforeEach
    void init(){
        modelMapper = new ModelMapper();
        service = new UpdateBookUseCase(repoMock,modelMapper);
    }

    @Test
    void updateBook(){

        var book = new Book("1", "title1", 2020);
        var bookUpdated = new Book("1", "titleUpdated", 2010);
        var bookDto = new BookDTO("1", "titleUpdated", 2010);

        Mockito.when(repoMock.findById(ArgumentMatchers.any(String.class))).thenReturn(Mono.just(book));
        Mockito.when(repoMock.save(ArgumentMatchers.any(Book.class))).thenReturn(Mono.just(bookUpdated));


        var response = service.update("testBook",bookDto);

        StepVerifier.create(response)
                .expectNextCount(1)
                .verifyComplete();

        Mockito.verify(repoMock).findById(ArgumentMatchers.any(String.class));
        Mockito.verify(repoMock).save(ArgumentMatchers.any(Book.class));

    }

}