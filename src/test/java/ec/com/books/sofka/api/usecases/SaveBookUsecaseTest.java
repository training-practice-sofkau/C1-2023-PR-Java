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
class SaveBookUsecaseTest {

    @Mock
    IBookRepository bookRepository;
    ModelMapper modelMapper;
    SaveBookUsecase saveBookUsecase;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        saveBookUsecase = new SaveBookUsecase(bookRepository, modelMapper);
    }

    @Test
    void testSaveBook() {

        BookDTO bookDTO = new BookDTO(
                "122333",
                "123456789",
                "Lord of the rings",
                new ArrayList<>(),
                new ArrayList<>(),
                1992
        );

        Book book = new Book(
                "122333",
                "123456789",
                "Lord of the rings",
                new ArrayList<>(),
                new ArrayList<>(),
                1992
        );

        Mockito.when(bookRepository.save(book)).thenReturn(Mono.just(book));

        var result = saveBookUsecase.save(bookDTO);

        StepVerifier.create(result)
                .expectNextMatches(bookDTO1 -> {
                    assertEquals("122333", bookDTO1.getId());
                    assertEquals("123456789", bookDTO1.getIsbn());
                    assertEquals("Lord of the rings", bookDTO1.getTitle());
                    assertEquals(1992, bookDTO1.getYear());
                    return true;
                })
                .expectComplete()
                .verify();

    }

}