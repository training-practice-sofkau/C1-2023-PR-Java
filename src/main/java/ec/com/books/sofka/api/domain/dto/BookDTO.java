package ec.com.books.sofka.api.domain.dto;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Validated
public class BookDTO {
    private String id;

    @NotNull//(message = "ISBN can't be null")
    @NotBlank//(message = "ISBN can't be blank")
    @Size(min = 10, max = 12, message = "ISBN can have from 10 to 12 characters")
    private String isbn;

    @NotNull //(message = "Title can't be null")
    @NotBlank//(message = "Title can't be blank")
    private String title;

    private List<String> authors;
    private List<String> categories;

    @NotNull//(message = "can't be null - must be between 1900 and 3000")
    @Min(1900)
    @Max(3000)
    private Integer year;

    private Boolean available;

    public BookDTO (String isbn, String title, Integer year){
        this.isbn = isbn;
        this.title = title;
        this.authors = new ArrayList<>();
        this.categories = new ArrayList<>();
        this.year = year;
        this.available = true;
    }

}
