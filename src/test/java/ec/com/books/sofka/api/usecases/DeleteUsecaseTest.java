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
class DeleteUsecaseTest {

    @Mock
    IBookRepository repoMock;

    ModelMapper modelMapper;

    DeleteUsecase deleteUsecase;

    @BeforeEach
    void init(){
        modelMapper = new ModelMapper();
        deleteUsecase = new DeleteUsecase(repoMock);
    }

    @Test
    void deleteBook(){

        var book1 = new Book("1", "Nice Book", 2000);
        var book = Mono.just(book1);

        Mockito.when(repoMock.findById(ArgumentMatchers.anyString())).thenReturn(book);
        Mockito.when(repoMock.delete(ArgumentMatchers.any(Book.class))).thenReturn(Mono.empty());

        var response = deleteUsecase.delete(ArgumentMatchers.anyString());

        StepVerifier.create(response)
                .expectNextCount(0)
//                .expectNext(modelMapper.map(book, Book.class))
                .verifyComplete();

        Mockito.verify(repoMock).findById(ArgumentMatchers.anyString());
        Mockito.verify(repoMock).delete(ArgumentMatchers.any(Book.class));
    }
}