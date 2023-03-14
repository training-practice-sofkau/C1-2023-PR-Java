package ec.com.books.sofka.api.repository;

import ec.com.books.sofka.api.domain.collection.Book;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookRepository extends ReactiveMongoRepository<Book, String> {
}
