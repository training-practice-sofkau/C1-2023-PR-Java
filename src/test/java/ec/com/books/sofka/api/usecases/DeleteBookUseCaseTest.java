package ec.com.books.sofka.api.usecases;

import ec.com.books.sofka.api.domain.collection.Book;
import ec.com.books.sofka.api.repository.IBookRepository;
import org.junit.jupiter.api.BeforeEach;
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
class DeleteBookUseCaseTest {

    @Mock
    IBookRepository repoMock;

    ModelMapper modelMapper;

    DeleteBookUseCase deleteBookUseCase;

    @BeforeEach
    void init(){
        modelMapper = new ModelMapper();
        deleteBookUseCase = new DeleteBookUseCase(repoMock);
    }

    @Test
    void deleteBook(){

        var book = Mono.just(new Book("15958432975", "Arabian Nights", 1998));

        Mockito.when(repoMock.findById(ArgumentMatchers.anyString())).thenReturn(book);

        Mockito.when(repoMock.deleteById(ArgumentMatchers.anyString())).thenReturn(Mono.empty());

        var response = deleteBookUseCase.delete(ArgumentMatchers.anyString());

        StepVerifier.create(response)
                .expectNextCount(0)
                .verifyComplete();

        Mockito.verify(repoMock).deleteById(ArgumentMatchers.anyString());
    }
}