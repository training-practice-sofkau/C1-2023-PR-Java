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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DeleteBookByIdUseCaseTest {
    @Mock
    IBookRepository repoMock;
    ModelMapper mapper;
    DeleteBookByIdUseCase service;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        service = new DeleteBookByIdUseCase(repoMock, mapper);
    }

    @Test
    @DisplayName("deleteByID_Success")
    void deleteByIdSuccess() {
        //Build the scenario you need
        var monoBook = Mono.just(new Book("1", "title1", 2020));

        Mockito.when(repoMock.findById(ArgumentMatchers.anyString())).thenReturn(monoBook);
        Mockito.when(repoMock.deleteById(ArgumentMatchers.anyString())).thenReturn(Mono.empty());

        var response = service.apply(ArgumentMatchers.anyString());

        StepVerifier.create(response)
                .expectNextCount(0)
                .verifyComplete();

        Mockito.verify(repoMock).findById(ArgumentMatchers.anyString());
        Mockito.verify(repoMock).deleteById(ArgumentMatchers.anyString());
    }
}