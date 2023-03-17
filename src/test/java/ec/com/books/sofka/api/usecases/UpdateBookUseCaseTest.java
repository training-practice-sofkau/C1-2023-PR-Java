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
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UpdateBookUseCaseTest {

    @Mock
    IBookRepository repoMock;

    @Mock
    SaveBookUsecase saveBookUsecase;

    ModelMapper modelMapper;

    UpdateBookUseCase updateBookUseCase;

    @BeforeEach
    void init(){
        modelMapper = new ModelMapper();
        saveBookUsecase = new SaveBookUsecase( repoMock, modelMapper);
        updateBookUseCase = new UpdateBookUseCase(repoMock);
    }

    @Test
    @DisplayName("updateBook_Success")
    void updateBook(){

        var book = new Book("1", "title1", 2020);
        book.setId("1");

        var monoBook = Mono.just(book);

        Mockito.when(repoMock.findById(Mockito.any(String.class))).thenReturn(monoBook);

        var updatedBook = new Book("1", "title3", 2023);
        book.setId("1");

        var monoUpdatedBookDTO = Mono.just(modelMapper.map(updatedBook, BookDTO.class));

        //Mockito.when(repoMock.save(Mockito.any(Book.class))).thenReturn(monoUpdatedBook);

        //Mockito.when(repoMock.save(updatedBook)).thenReturn(Mono.just(updatedBook));

        Mockito.when(saveBookUsecase.save(modelMapper.map(updatedBook, BookDTO.class))
                .thenReturn(monoUpdatedBookDTO));

        Mockito.when(repoMock.save(updatedBook)).thenReturn(Mono.just(updatedBook));

        var service =
                updateBookUseCase.updateBook("1", modelMapper.map(updatedBook, BookDTO.class));

        StepVerifier.create(service)
                .expectNext(modelMapper.map(updatedBook, BookDTO.class))
                .expectComplete()
                .verify();

        Mockito.verify(repoMock).save(updatedBook);
    }

}