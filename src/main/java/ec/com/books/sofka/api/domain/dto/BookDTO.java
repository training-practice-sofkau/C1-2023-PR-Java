package ec.com.books.sofka.api.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private String id;

    @NotNull(message = "ISBN can't be null")
    @Size(min = 10, max = 12, message = "ISBN can have from 10 to 12 characters")
    private String isbn;

    @NotNull(message = "Title can't be null")
    private String title;

    private List<String> authors;
    private List<String> categories;

    @NotNull(message = "year can't be null")
    private Integer year;

    private Boolean available;

}
