package ec.com.books.sofka.api.usecases;

import ec.com.books.sofka.api.domain.collection.Book;
import ec.com.books.sofka.api.repository.IBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DeleteBookUseCaseTest {

    @Mock
    IBookRepository bookRepository;
    DeleteBookUseCase deleteBookUseCase;

    @BeforeEach
    void setUp() {
        deleteBookUseCase = new DeleteBookUseCase(bookRepository);
    }

    @Test
    void testDeleteBook() {
        // create a test book
        Book book = new Book("12345", "Test Book", "Test Author", new ArrayList<>(), new ArrayList<>(), 2021);

        // save the book to the repository
        Mockito.when(bookRepository.findById("12345")).thenReturn(Mono.just(book));
        Mockito.when(bookRepository.deleteById("12345")).thenReturn(Mono.empty());

        // delete the book using the use case
        Mono<Void> result = deleteBookUseCase.apply("12345");

        // verify that the book was deleted
        StepVerifier.create(result)
                .expectComplete()
                .verify();

    }

}