package ec.com.books.sofka.api.usecases;

import ec.com.books.sofka.api.domain.collection.Book;
import ec.com.books.sofka.api.domain.dto.BookDTO;
import ec.com.books.sofka.api.repository.IBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetBookByIdUseCaseTest {

    @Mock
    IBookRepository repoMock;
    ModelMapper mapper;

    @InjectMocks
    GetBookByIdUseCase getBookByIdUseCase;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        getBookByIdUseCase = new GetBookByIdUseCase(repoMock, mapper);
    }

    @Test
    @DisplayName("getBookBiId_successfully")
    void getBookId() {
        //Build the scenario you need
        String id = "6413683efa74e77204d881f0";
        Book newBook = new Book("1", "title1", 2020);
        newBook.setId(id);
        var monoBook = Mono.just(newBook);
        BookDTO newBookDTO = new BookDTO("1", "title1", 2020);
        newBookDTO.setId(id);
        newBookDTO.setAuthors(new ArrayList<>());
        newBookDTO.setCategories(new ArrayList<>());
        newBookDTO.setAvailable(true);


        when(repoMock.findById(id)).thenReturn(monoBook);


        Mono<BookDTO> response = getBookByIdUseCase.apply(id);

        StepVerifier.create(response)
                .expectNext(newBookDTO)
                .verifyComplete();

        Mockito.verify(repoMock, times(1)).findById(ArgumentMatchers.anyString());

    }

    @Test
    @DisplayName("testBookNotFound")
    public void testBookNotFound() {
        String id = "6413683efa74e77204d881f0";
        when(repoMock.findById(id)).thenReturn(Mono.empty());

        Mono<BookDTO> result = getBookByIdUseCase.apply(id);

        StepVerifier.create(result)
                .expectError(Throwable.class)
                .verify();

        Mockito.verify(repoMock, times(1)).findById(ArgumentMatchers.anyString());
    }

}