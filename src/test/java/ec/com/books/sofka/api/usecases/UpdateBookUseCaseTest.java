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

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateBookUseCaseTest {

    @Mock
    IBookRepository repoMock;
    ModelMapper mapper;
    @InjectMocks
    UpdateBookUseCase updateBookUseCase;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        updateBookUseCase = new UpdateBookUseCase(repoMock, mapper);
    }

    @Test
    @DisplayName("updateBook_successfully")
    void updateBook() {
        //Build the scenario you need
        String id = "6413683efa74e77204d881f0";
        Book book = new Book("1", "title1", 2020);
        book.setId(id);
        Book bookUpdated = new Book("2", "title2", 1999);
        bookUpdated.setId(id);
        BookDTO bookUpdatedDTO = new BookDTO("2", "title2", 1999);
        bookUpdatedDTO.setAuthors(new ArrayList<>());
        bookUpdatedDTO.setCategories(new ArrayList<>());
        bookUpdatedDTO.setAvailable(true);


        when(repoMock.findById(id)).thenReturn(Mono.just(book));
        when(repoMock.save(bookUpdated)).thenReturn(Mono.just(bookUpdated));


        Mono<BookDTO> response = updateBookUseCase.update(id, bookUpdatedDTO);

        StepVerifier.create(response)
                .expectNext(bookUpdatedDTO)
                .verifyComplete();

        Mockito.verify(repoMock, times(1)).findById(ArgumentMatchers.anyString());
        Mockito.verify(repoMock, times(1)).save(ArgumentMatchers.any(Book.class));
    }

    @Test
    @DisplayName("testUpdateBookError")
    public void testUpdateBookError() {
        String id = "6413683efa74e77204d881f0";
        Book book = new Book("1", "title1", 2020);
        book.setId(id);
        Book bookUpdated = new Book("2", "title2", 1999);
        bookUpdated.setId(id);
        BookDTO bookUpdatedDTO = new BookDTO("2", "title2", 1999);
        bookUpdatedDTO.setAuthors(new ArrayList<>());
        bookUpdatedDTO.setCategories(new ArrayList<>());
        bookUpdatedDTO.setAvailable(true);

        when(repoMock.findById(id)).thenReturn(Mono.empty());

        Mono<BookDTO> result = updateBookUseCase.update(id, bookUpdatedDTO);

        StepVerifier.create(result)
                .expectError(Throwable.class)
                .verify();

        Mockito.verify(repoMock, times(1)).findById(ArgumentMatchers.anyString());
    }

}