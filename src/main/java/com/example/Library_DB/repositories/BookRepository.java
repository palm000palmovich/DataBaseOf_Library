package com.example.Library_DB.repositories;

import com.example.Library_DB.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByName(String name);
    Collection<Book> findBooksByAuthor(String author);

    Collection<Book> findAllByBookContains(String part);
}
