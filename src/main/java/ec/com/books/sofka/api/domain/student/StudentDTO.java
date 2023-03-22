package ec.com.books.sofka.api.domain.student;

import ec.com.books.sofka.api.domain.dto.BookDTO;
import lombok.Data;

import java.util.List;

@Data
public class StudentDTO {
    private String id;
    private String idNum;
    private String name;
    private String lastName;
    private Boolean blocked;
    private List<BookDTO> booksDTO;
}
