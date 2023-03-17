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
    IBookRepository iBookRepository;

    ModelMapper modelMapper;

    DeleteUsecase deleteUsecase;

    @BeforeEach
    void init(){
        modelMapper = new ModelMapper();
        deleteUsecase = new DeleteUsecase(iBookRepository);
    }

    @Test
    void deleteBook(){

        var book1 = new Book("1", "Nice Book", 2000);
        var book = Mono.just(book1);

        Mockito.when(iBookRepository.findById(ArgumentMatchers.anyString())).thenReturn(book);
        Mockito.when(iBookRepository.delete(ArgumentMatchers.any(Book.class))).thenReturn(Mono.empty());

        var response = deleteUsecase.delete(ArgumentMatchers.anyString());

        StepVerifier.create(response)
                .expectNextCount(0)
                .verifyComplete();

        Mockito.verify(iBookRepository).findById(ArgumentMatchers.anyString());
        Mockito.verify(iBookRepository).delete(ArgumentMatchers.any(Book.class));
    }
}