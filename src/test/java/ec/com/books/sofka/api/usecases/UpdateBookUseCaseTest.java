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
class UpdateBookUseCaseTest {

    @Mock
    IBookRepository bookRepository;

    ModelMapper modelMapper;
    UpdateBookUseCase updateBookUseCase;

    @BeforeEach
    void init() {
        modelMapper = new ModelMapper();
        updateBookUseCase = new UpdateBookUseCase(bookRepository, modelMapper);
    }

    @Test
    @DisplayName("Update book successfully")
    void successfullyScenario() {

        Book b1 = new Book("testId", "2", "title2", List.of(), List.of(), 2022, true);
        var monoBook = Mono.just(b1);

        Mockito.when(bookRepository.findById("testId")).thenReturn(monoBook);

        Book b2 = new Book("testId", "3", "title3", List.of(), List.of(), 2023, false);
        var monoBook2 = Mono.just(b2);

        Mockito.when(bookRepository.save(b2)).thenReturn(monoBook2);

        var response = updateBookUseCase.update(
                "testId",
                new BookDTO("testId", "3", "title3", List.of(), List.of(), 2023, false)
        );


        StepVerifier.create(response)
                .expectNext(new BookDTO("testId", "3", "title3", List.of(), List.of(), 2023, false))
                .verifyComplete();
    }

    @Test
    @DisplayName("Update book - book not found")
    void failedScenario() {

        Mockito.when(bookRepository.findById("testId")).thenReturn(Mono.empty());

        var response = updateBookUseCase.update("testId", new BookDTO("testId", "3", "title3", List.of(), List.of(), 2023, false));

        StepVerifier.create(response)
                .expectErrorMatches(throwable -> throwable.getMessage().equals("Book not found"))
                .verify();
    }

}