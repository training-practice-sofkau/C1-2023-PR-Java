package ec.com.books.sofka.api.usecases;

import com.fasterxml.jackson.core.JsonProcessingException;
import ec.com.books.sofka.api.domain.collection.Book;
import ec.com.books.sofka.api.domain.dto.BookDTO;
import ec.com.books.sofka.api.publisher.BookPublisher;
import ec.com.books.sofka.api.repository.IBookRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class LendBookUseCase {
    private final IBookRepository bookRepository;

    private final ModelMapper mapper;

    private final BookPublisher bookPublisher;


    public Mono<BookDTO> lend(String id, String idEst){
        System.out.println("Book id: " + id);
        System.out.println("Student id: " + idEst);
        return this.bookRepository
                .findById(id)
                .switchIfEmpty(Mono.empty())
                //.filter(Book::getAvailable)
                .switchIfEmpty(Mono.empty())
                .flatMap(book -> {
                    System.out.println("Book to be lended "+book.toString());
                    book.setAvailable(false);
                    return this.bookRepository.save(book);})
                .map(book -> mapper.map(book, BookDTO.class))
                .doOnSuccess(bookDTO -> {

                    try {
                        bookPublisher.publish(bookDTO, idEst);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });

    }
}
