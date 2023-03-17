package ec.com.books.sofka.api.usecases;

import ec.com.books.sofka.api.domain.collection.Book;
import ec.com.books.sofka.api.repository.IBookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteBookUseCaseTest {

    @Mock
    IBookRepository repoMock;

    @InjectMocks
    DeleteBookUseCase deleteBookUseCase;



    @Test
    @DisplayName("deleteBookTest_Success")
    void deleteBook() {
        //Build the scenario you need
        String id = "6413683efa74e77204d881f0";
        Book newBook = new Book("1", "title1", 2020);
        newBook.setId(id);
        var monoBook = Mono.just(newBook);

        when(repoMock.findById(id)).thenReturn(monoBook);
        when(repoMock.delete(newBook)).thenReturn(Mono.empty());


        Mono<String> response = deleteBookUseCase.apply(id);

        StepVerifier.create(response)
                .expectNext("6413683efa74e77204d881f0")
                .verifyComplete();

        Mockito.verify(repoMock, times(1)).findById(ArgumentMatchers.anyString());
        Mockito.verify(repoMock, times(1)).delete(ArgumentMatchers.any(Book.class));

    }

    @Test
    @DisplayName("testDeleteBookNotFound")
    public void testDeleteBookNotFound() {
        String id = "6413683efa74e77204d881f0";
        when(repoMock.findById(id)).thenReturn(Mono.empty());

        Mono<String> result = deleteBookUseCase.apply(id);

        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();

        Mockito.verify(repoMock, times(1)).findById(ArgumentMatchers.anyString());
        Mockito.verify(repoMock, never()).delete(ArgumentMatchers.any(Book.class));
    }

}