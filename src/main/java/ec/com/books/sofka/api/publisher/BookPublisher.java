package ec.com.books.sofka.api.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ec.com.books.sofka.api.domain.dto.BookDTO;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class BookPublisher {
    private final RabbitTemplate rabbitTemplate;
    //private final DirectExchange exchange;
    private final ObjectMapper objectMapper;


    public BookPublisher(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
        //this.exchange = exchange;
    }

    public void publish(BookDTO bookDTO, String id) throws JsonProcessingException {
        //String message = objectMapper.writeValueAsString("Book with id "+bookDTO.getId()+" has been lended to student with id "+id);
        String message = objectMapper.writeValueAsString(new BookEvent(id, bookDTO, "lend"));
        rabbitTemplate.convertAndSend("books-exchange-events", "events.book.routing.key", message);

    }


    public void publishError(Throwable errorEvent){
    }
}
