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
class SaveBookUseCaseTest {

    @Mock
    IBookRepository repoMock;

    ModelMapper modelMapper;

    SaveBookUseCase saveBookUseCase;

    @BeforeEach
    void init(){
        modelMapper = new ModelMapper();
        saveBookUseCase = new SaveBookUseCase(repoMock, modelMapper);
    }

    @Test
    @DisplayName("saveBook_Success")
    void saveBook(){

        var book = new Book("1526894733", "Principles", 2017);

        Mockito.when(repoMock.save(ArgumentMatchers.any(Book.class))).thenReturn(Mono.just(book));

        var response = saveBookUseCase.save(modelMapper.map(book, BookDTO.class));

        StepVerifier.create(response)
                .expectNextCount(1)
                .verifyComplete();

        Mockito.verify(repoMock).save(ArgumentMatchers.any(Book.class));

    }
}