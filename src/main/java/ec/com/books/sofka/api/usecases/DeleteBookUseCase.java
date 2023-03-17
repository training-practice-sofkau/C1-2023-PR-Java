package ec.com.books.sofka.api.usecases;

import ec.com.books.sofka.api.domain.dto.BookDTO;
import ec.com.books.sofka.api.repository.IBookRepository;
import ec.com.books.sofka.api.usecases.interfaces.DeleteBook;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class DeleteBookUseCase implements DeleteBook {

    private final IBookRepository bookRepository;


    @Override
    public Mono<Void> delete(String id) {
        return bookRepository.findById(id)
                .switchIfEmpty(Mono.error(new Throwable("Book not found")))
                .flatMap(bookRepository::delete);
    }
}
