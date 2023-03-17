package ec.com.books.sofka.api.usecases;

import ec.com.books.sofka.api.repository.IBookRepository;
import ec.com.books.sofka.api.usecases.interfaces.DeleteBook;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DeleteBookUseCase implements DeleteBook {

    private final IBookRepository bookRepository;
    private final ModelMapper modelMapper;

    @Override
    public Mono<Void> delete(String id) {
        return bookRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Book not found")))
                .flatMap(book -> bookRepository.deleteById(book.getId()))
                .onErrorResume(error -> Mono.error(new RuntimeException("Book not found")));
    }
}
