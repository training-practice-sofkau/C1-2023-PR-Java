package ec.com.books.sofka.api.usecases;

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
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

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

        var book = new Book("71392864528", "Arabian Nights", 1998);
        var updatedBook = new Book("15155959488", "Atomic Habits", 2019);

        Mockito.when(repoMock.findById(ArgumentMatchers.anyString())).thenReturn(Mono.just(book));

        Mockito.when(repoMock.save(ArgumentMatchers.any(Book.class))).thenReturn(Mono.just(updatedBook));

        var response = updateBookUseCase.update("bookId",
                modelMapper.map(updatedBook, BookDTO.class));

        StepVerifier.create(response)
                .expectNextCount(1)
                .verifyComplete();

        Mockito.verify(repoMock).findById(ArgumentMatchers.anyString());
        Mockito.verify(repoMock).save(ArgumentMatchers.any(Book.class));
    }
}