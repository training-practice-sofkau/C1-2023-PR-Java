package ec.com.books.sofka.api.usecases;

import ec.com.books.sofka.api.domain.collection.Book;
import ec.com.books.sofka.api.domain.dto.BookDTO;
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

import java.util.List;


@ExtendWith(MockitoExtension.class)
class UpdateBookUsecaseTest {
    @Mock
    IBookRepository iBookRepository;
    ModelMapper modelMapper;
    UpdateBookUsecase updateBookUsecase;

    @BeforeEach
    void init(){
        modelMapper = new ModelMapper();
        updateBookUsecase = new UpdateBookUsecase(iBookRepository, modelMapper);
    }


    @Test
    @DisplayName("Update book successfully")
    void successfullyScenario() {
        var book = new Book("1", "book title", 2022);
        var bookUpdated = new Book("1", "book updated", 2012);
        var bookDTO = new BookDTO("1", "book updated", 2012);

        Mockito.when(iBookRepository.findById(ArgumentMatchers.any(String.class))).thenReturn(Mono.just(book));
        Mockito.when(iBookRepository.save(ArgumentMatchers.any(Book.class))).thenReturn(Mono.just(bookUpdated));


        var response = updateBookUsecase.update("testBook",bookDTO);

        StepVerifier.create(response)
                .expectNextCount(1)
                .verifyComplete();

        Mockito.verify(iBookRepository).findById(ArgumentMatchers.any(String.class));
        Mockito.verify(iBookRepository).save(ArgumentMatchers.any(Book.class));


    }


}