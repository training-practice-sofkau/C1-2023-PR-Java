package ec.com.books.sofka.api;

import ec.com.books.sofka.api.domain.collection.Book;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class RepoMocking {

    public static Mono<Book> getMonoBooks() {
        Book mono = new Book("mono", "title mono", 2020);
        return Mono.just(mono);
    }

    public static Mono<Book> createMonoBook(Book book) {
        return Mono.just(book);
    }

    public static Flux<Book> getFluxBooks(Book argument) {
        Book a = new Book("1", "title1", 2021);
        Book b = new Book("2", "title2", 2022);
        Book c = new Book("3", "title3", 2023);
        Book d = new Book("4", "title4", 2024);
        Book e = new Book("5", "title5", 2025);
        return Flux.just(argument, a, b, c, d, e);
    }
}
