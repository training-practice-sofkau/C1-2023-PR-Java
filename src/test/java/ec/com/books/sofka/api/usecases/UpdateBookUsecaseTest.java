package ec.com.books.sofka.api.usecases;

import ec.com.books.sofka.api.RepoMocking;
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

@ExtendWith(MockitoExtension.class)
class UpdateBookUsecaseTest {

    @Mock
    IBookRepository repository;
    ModelMapper modelMapper;
    UpdateBookUsecase updateBookUsecase;

    @BeforeEach
    void init() {
        modelMapper = new ModelMapper();
        updateBookUsecase = new UpdateBookUsecase(repository, modelMapper);
    }

    @Test
    @DisplayName("getAllBooks_Success")
    void getAllBooks() {

        Book book = new Book("1", "1", 1);
        book.setId("1");
        Mockito.when(repository.findById("1")).
                thenAnswer(InvocationOnMock -> {
                    return Mono.just(book);
                });
        Mockito.when(repository.save(book)).
                thenAnswer(InvocationOnMock -> {
                    return Mono.just(book);
                });

        Mono<BookDTO> response = updateBookUsecase.update("1", modelMapper.map(book, BookDTO.class));

        StepVerifier.create(response)
                .expectNextCount(1)
                .verifyComplete();

        Mockito.verify(repository).save(book);
        Mockito.verify(repository).findById("1");
    }
}