package ec.com.books.sofka.api.usecases;

import ec.com.books.sofka.api.RepoMocking;
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
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;


@ExtendWith(MockitoExtension.class)
class GetAllBooksUsecaseTest {
    @Mock
    IBookRepository repository;
    ModelMapper modelMapper;
    GetAllBooksUsecase getAllBooksUsecase;

    @BeforeEach
    void init(){
        modelMapper = new ModelMapper();
        getAllBooksUsecase = new  GetAllBooksUsecase(repository, modelMapper);
    }

    @Test
    @DisplayName("getAllBooks_Success")
    void getAllBooks() {

        Mockito.when(repository.findAll()).
                thenAnswer(InvocationOnMock -> {
                    return RepoMocking.getFluxBooks(new Book("1", "1", 1));
                });

        Flux<BookDTO> response = getAllBooksUsecase.get();

        StepVerifier.create(response)
                .expectNextCount(6)
                .verifyComplete();

        Mockito.verify(repository).findAll();
    }

}