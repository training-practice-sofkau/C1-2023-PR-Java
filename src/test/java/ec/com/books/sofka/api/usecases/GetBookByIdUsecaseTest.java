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
class GetBookByIdUsecaseTest {

    @Mock
    IBookRepository bookRepository;
    ModelMapper modelMapper;
    GetBookByIdUsecase getBookByIdUsecase;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        getBookByIdUsecase = new GetBookByIdUsecase(bookRepository, modelMapper);
    }

    @Test
    void testGetBookById() {

        Book book = new Book(
                "122333",
                "123456789",
                "Lord of the rings",
                new ArrayList<>(),
                new ArrayList<>(),
                1992
        );

        Mockito.when(bookRepository.findById("122333")).thenReturn(Mono.just(book));

        var result = getBookByIdUsecase.apply("122333");

        StepVerifier.create(result)
                .expectNextMatches(bookDTO -> {
                    assertEquals("122333", bookDTO.getId());
                    assertEquals("123456789", bookDTO.getIsbn());
                    assertEquals("Lord of the rings", bookDTO.getTitle());
                    assertEquals(1992, bookDTO.getYear());
                    return true;
                })
                .expectComplete()
                .verify();
    }

}