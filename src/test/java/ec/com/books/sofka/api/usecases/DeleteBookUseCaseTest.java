package ec.com.books.sofka.api.usecases;

import ec.com.books.sofka.api.domain.collection.Book;
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
class DeleteBookUseCaseTest {

    @Mock
    IBookRepository bookRepository;

    ModelMapper modelMapper;
    DeleteBookUseCase deleteBookUseCase;

    @BeforeEach
    void init() {
        modelMapper = new ModelMapper();
        deleteBookUseCase = new DeleteBookUseCase(bookRepository, modelMapper);
    }

    @Test
    @DisplayName("Delete book successfully")
    void successfullyScenario() {
        Book b1 = new Book("testId", "2", "title2", List.of(), List.of(), 2022, true);
        Mockito.when(bookRepository.findById("testId")).thenReturn(Mono.just(b1));
        Mockito.when(bookRepository.deleteById("testId")).thenReturn(Mono.empty());

        var response = deleteBookUseCase.delete("testId");

        StepVerifier.create(response)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Delete book fails")
    void failedScenario() {

        Mockito.when(bookRepository.findById("testId")).thenReturn(Mono.empty());

        var result = deleteBookUseCase.delete("testId");

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable.getMessage().equals("Book not found"))
                .verify();
    }

}