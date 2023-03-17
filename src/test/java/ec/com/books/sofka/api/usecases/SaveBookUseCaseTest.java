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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SaveBookUseCaseTest {

    @Mock
    IBookRepository repoMock;
    ModelMapper mapper;
    @InjectMocks
    SaveBookUseCase saveBookUseCase;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        saveBookUseCase = new SaveBookUseCase(repoMock, mapper);
    }

    @Test
    @DisplayName("saveBook_successfully")
    void saveBook() {
        //Build the scenario you need
        String id = "6413683efa74e77204d881f0";
        Book newBook = new Book("1", "title1", 2020);
        newBook.setId(id);
        BookDTO newBookDTO = new BookDTO("1", "title1", 2020);
        newBookDTO.setId(id);
        newBookDTO.setAuthors(new ArrayList<>());
        newBookDTO.setCategories(new ArrayList<>());
        newBookDTO.setAvailable(true);


        when(repoMock.save(newBook)).thenReturn(Mono.just(newBook));


        Mono<BookDTO> response = saveBookUseCase.save(newBookDTO);

        StepVerifier.create(response)
                .expectNext(newBookDTO)
                .verifyComplete();

        Mockito.verify(repoMock, times(1)).save(ArgumentMatchers.any(Book.class));
    }

    @Test
    @DisplayName("testSaveBookError")
    public void testSaveBookError() {
        String id = "6413683efa74e77204d881f0";
        Book newBook = new Book("1", "title1", 2020);
        newBook.setId(id);
        BookDTO newBookDTO = new BookDTO("1", "title1", 2020);
        newBookDTO.setId(id);
        newBookDTO.setAuthors(new ArrayList<>());
        newBookDTO.setCategories(new ArrayList<>());
        newBookDTO.setAvailable(true);

        when(repoMock.save(newBook)).thenReturn(Mono.empty());

        Mono<BookDTO> result = saveBookUseCase.save(newBookDTO);

        StepVerifier.create(result)
                .expectError(Throwable.class)
                .verify();

        Mockito.verify(repoMock, times(1)).save(ArgumentMatchers.any(Book.class));
    }

}