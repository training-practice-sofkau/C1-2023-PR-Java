package ec.com.books.sofka.api.usecases;

import ec.com.books.sofka.api.domain.dto.BookDTO;
import ec.com.books.sofka.api.repository.IBookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Service
public class UpdateBookUseCase implements BiFunction<String, BookDTO, Mono<BookDTO>> {

    IBookRepository bookRepository;
    ModelMapper modelMapper;
    SaveBookUsecase saveBookUsecase;
    public UpdateBookUseCase(IBookRepository bookRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
        this.saveBookUsecase = new SaveBookUsecase(bookRepository, modelMapper);
    }

    @Override
    public Mono<BookDTO> apply(String id, BookDTO bookDTO) {
        return bookRepository
                .findById(id)
                .switchIfEmpty(Mono.empty())
                .flatMap(book -> {
                    bookDTO.setId(book.getId());
                    return saveBookUsecase.save(bookDTO);
                });
    }

}
