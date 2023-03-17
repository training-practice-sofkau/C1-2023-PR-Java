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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class SaveBookUsecaseTest {
    @Mock
    IBookRepository repository;
    ModelMapper modelMapper;
    SaveBookUsecase saveBookUsecase;

    @BeforeEach
    void init(){
        modelMapper = new ModelMapper();
        saveBookUsecase = new  SaveBookUsecase(repository, modelMapper);
    }

    @Test
    @DisplayName("getAllBooks_Success")
    void getAllBooks() {

        Book book = new Book("1", "1", 1);
        Mockito.when(repository.save(book)).
                thenAnswer(InvocationOnMock -> {
                    return RepoMocking.getMonoBooks();
                });

        Mono<BookDTO> response = saveBookUsecase.save(modelMapper.map(book, BookDTO.class));

        StepVerifier.create(response)
                .expectNextCount(1)
                .verifyComplete();

        Mockito.verify(repository).save(book);
    }


}