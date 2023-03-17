package ec.com.books.sofka.api.usecases;

import ec.com.books.sofka.api.domain.collection.Book;
import ec.com.books.sofka.api.domain.dto.BookDTO;
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
class UpdateBookUseCaseTest{

    @Mock
    IBookRepository bookRepository;

    ModelMapper modelMapper;

    private UpdateBookUseCase updateBookUseCase;

    @BeforeEach
    void setUp() {
        this.modelMapper = new ModelMapper();
        this.updateBookUseCase = new UpdateBookUseCase(bookRepository, modelMapper);
    }
    @Test
    void testUpdateBook() {
        // Create a test book
        BookDTO bookDTO = new BookDTO("12345", "Test Book", "Test Author", new ArrayList<>(), new ArrayList<>(), 2021);

        // Mock the book repository
        Book book = new Book("12345", "Test Book", "Test Author", new ArrayList<>(), new ArrayList<>(), 2021);
        Mockito.when(bookRepository.findById("123456789")).thenReturn(Mono.just(book));
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(Mono.just(book));

        // Call the use case
        Mono<BookDTO> result = updateBookUseCase.apply("123456789", bookDTO);

        // Verify the result
        StepVerifier.create(result)
                .expectNextMatches(updatedBook -> updatedBook.getId().equals(book.getId()))
                .verifyComplete();

    }


}