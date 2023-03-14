package ec.com.books.sofka.api.domain.collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Data
//@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "books")
public class Book {
    @Id
    private String id = UUID.randomUUID().toString().substring(0, 10);

    @NotNull(message = "ISBN can't be null")
    @Size(min = 10, max = 12, message = "ISBN can have from 10 to 12 characters")
    private String isbn;

    @NotNull(message = "Title can't be null")
    private String title;

    private List<String> authors = new ArrayList<>();

    private List<String> categories = new ArrayList<>();

    @NotNull(message = "year can't be null")
    private Integer year;

    private Boolean available = true;

}
