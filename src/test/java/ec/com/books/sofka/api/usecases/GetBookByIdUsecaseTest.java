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
class GetBookByIdUsecaseTest {

    @Mock
    IBookRepository iBookRepository;
    ModelMapper modelMapper;
    GetBookByIdUsecase getBookByIdUsecase;

    @BeforeEach
    void init(){
        modelMapper = new ModelMapper();
        getBookByIdUsecase = new GetBookByIdUsecase(iBookRepository, modelMapper);
    }

    @Test
    void getBookById(){

        var book = Mono.just(new Book("1", "title1", 2020));

        Mockito.when(iBookRepository.findById(ArgumentMatchers.anyString())).thenReturn(book);

        var response = getBookByIdUsecase.apply("bookId");

        StepVerifier.create(response)
                .expectNextCount(1)
                .verifyComplete();

        Mockito.verify(iBookRepository).findById("bookId");
    }

}