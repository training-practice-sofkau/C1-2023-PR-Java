package ec.com.books.sofka.api.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private String id;

    private String isbn;

    private String title;
    private List<String> authors;
    private List<String> categories;

    private Integer year;
    private Boolean available;

    public BookDTO (String isbn, String title, Integer year){
        this.isbn = isbn;
        this.title = title;
        this.year = year;
    }

}
