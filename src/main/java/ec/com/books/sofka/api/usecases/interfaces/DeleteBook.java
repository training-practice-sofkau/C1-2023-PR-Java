package ec.com.books.sofka.api.usecases.interfaces;

import reactor.core.publisher.Mono;

@FunctionalInterface
public interface DeleteBook {

    Mono<Void> delete(String id);
}
