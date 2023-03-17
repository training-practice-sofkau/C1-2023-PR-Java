package ec.com.books.sofka.api.domain.collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "books")
public class Book {
    @Id
    private String id;
    @NonNull
    private String isbn;
    @NonNull
    private String title;
    private List<String> authors = new ArrayList<>();
    private List<String> categories = new ArrayList<>();
    @NonNull
    private Integer year;
    private Boolean available = true;

    public Book (String isbn, String title, Integer year){
        this.id = UUID.randomUUID().toString().substring(0, 10);
        this.isbn = isbn;
        this.title = title;
        this.authors = new ArrayList<>();
        this.categories = new ArrayList<>();
        this.year = year;
        this.available = true;
    }
    public void setId(String id) {
        this.id = id;
    }
}
