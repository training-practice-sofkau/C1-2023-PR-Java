package ec.com.books.sofka.api.usecases;

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
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UpdateBookUseCaseTest {

    @Mock
    IBookRepository repoMock;

    ModelMapper modelMapper;

    UpdateBookUseCase updateBookUseCase;

    @BeforeEach
    void init(){
        modelMapper = new ModelMapper();
        updateBookUseCase = new UpdateBookUseCase(repoMock, modelMapper);
    }

    @Test
    @DisplayName("updateBook_Success")
    void updateBook(){

        var book = new Book("1", "title1", 2020);
        book.setId("1");

        var monoBook = Mono.just(book);

        Mockito.when(repoMock.findById(Mockito.any(String.class))).thenReturn(monoBook);

        var updatedBook = new Book("1", "title3", 2023);
        updatedBook.setId("1");

        Mockito.when(repoMock.save(Mockito.any(Book.class))).thenReturn(Mono.just(updatedBook));

        var service = updateBookUseCase.updateBook("1", modelMapper.map(updatedBook, BookDTO.class));

        StepVerifier.create(service)
                .expectNext( modelMapper.map(updatedBook, BookDTO.class))
                .expectComplete()
                .verify();

        Mockito.verify(repoMock).findById("1");
        Mockito.verify(repoMock).save(updatedBook);

    }

    @Test
    @DisplayName("updateBook_Failed")
    void updateBook_Failed(){

        var updatedBook = new Book("1", "title3", 2023);
        updatedBook.setId("1");

        Mockito.when(repoMock.findById(Mockito.any(String.class)))
                .thenReturn(Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())));

        var service = updateBookUseCase.updateBook("1", modelMapper.map(updatedBook, BookDTO.class));

        StepVerifier.create(service)
                .expectError()
                .verify();

        Mockito.verify(repoMock).findById("1");
    }

}