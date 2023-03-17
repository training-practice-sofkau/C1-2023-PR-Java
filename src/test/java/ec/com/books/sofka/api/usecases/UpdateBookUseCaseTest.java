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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UpdateBookUseCaseTest {

    @Mock
    IBookRepository repoMock;
    ModelMapper mapper;
    UpdateBookUseCase service;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        service = new UpdateBookUseCase(repoMock, mapper);
    }

    @Test
    @DisplayName("updateStudent_Success")
    void updateStudent(){
        var bookID = "ID1";
        var newBook = new Book("1", "title1", 2020);
        newBook.setId("AnyID");
        var oldBook = new Book("1", "title1", 2020);
        oldBook.setId("AnyID");
        var oldMonoBook = Mono.just(oldBook);
        var newMonoBook = Mono.just(newBook);

        Mockito.when(repoMock.findById(ArgumentMatchers.anyString())).thenReturn(oldMonoBook);
        Mockito.when(repoMock.save(ArgumentMatchers.any(Book.class))).thenReturn(newMonoBook);

        var response = service.apply(bookID,
                mapper.map(oldBook, BookDTO.class));

        StepVerifier.create(response)
                .expectNextCount(1)
                .verifyComplete();
        Mockito.verify(repoMock).findById(ArgumentMatchers.anyString());
        Mockito.verify(repoMock).save(ArgumentMatchers.any(Book.class));
    }



}