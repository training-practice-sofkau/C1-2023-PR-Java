package ec.com.books.sofka.api.usecases;

import ec.com.books.sofka.api.domain.collection.Book;
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
class GetBookByIdUseCaseTest {

    @Mock
    IBookRepository repoMock;

    ModelMapper modelMapper;

    GetBookByIdUseCase getBookByIdUsecase;

    @BeforeEach
    void init(){
        modelMapper = new ModelMapper();
        getBookByIdUsecase = new GetBookByIdUseCase(repoMock, modelMapper);
    }

    @Test
    @DisplayName("getBookById_Success")
    void getBookById(){

        var book = Mono.just(new Book("1", "title1", 2020));

        Mockito.when(repoMock.findById(ArgumentMatchers.anyString())).thenReturn(book);

        var response = getBookByIdUsecase.apply("bookId");

        StepVerifier.create(response)
                .expectNextCount(1)
                .verifyComplete();

        Mockito.verify(repoMock).findById("bookId");
    }

}