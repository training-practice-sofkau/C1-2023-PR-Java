package ec.com.books.sofka.api.publisher;

import ec.com.books.sofka.api.domain.dto.BookDTO;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class BookEvent {
    private String idEstudiante;
    private BookDTO bookLended;
    private String tipoEvento;
}
