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

import java.util.List;

@ExtendWith(MockitoExtension.class)
class SaveBookUsecaseTest {

    @Mock
    IBookRepository bookRepository;

    ModelMapper modelMapper;
    SaveBookUsecase saveBookUsecase;

    @BeforeEach
    void init() {
        modelMapper = new ModelMapper();
        saveBookUsecase = new SaveBookUsecase(bookRepository, modelMapper);
    }

    @Test
    @DisplayName("Save book successfully")
    void successfullyScenario() {

        Book b1 = new Book("testId", "2", "title2", List.of(), List.of(), 2022, true);
        var monoBook = Mono.just(b1);

        Mockito.when(bookRepository.save(new Book("testId", "2", "title2", List.of(), List.of(), 2022, true))).thenReturn(monoBook);

        var response = saveBookUsecase.save(modelMapper.map(b1, BookDTO.class));

        StepVerifier.create(response)
                .expectNext(new BookDTO("testId", "2", "title2", List.of(), List.of(), 2022, true))
                .verifyComplete();
    }

}