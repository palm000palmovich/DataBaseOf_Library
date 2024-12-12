package com.example.Library_DB.repositories;

import com.example.Library_DB.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByNameIgnoreCase(String name);
    Collection<Book> findBooksByAuthorContainsIgnoreCase(String author);


    //Collection<Book> findAllByNameContains(String part);

    //IgnoreCase - регистро незав.
    Collection<Book> findAllByNameContainsIgnoreCase(String part);

    /*//Совпадает и по имени и по атвору
    Collection<Book> findBooksByNameAndAuthor(String name, String author);

    //Поиск по имени или автору
    Collection<Book> findBooksByNameOrAuthor(String name, String author);

    //Поиск с учетом значение id
    Collection<Book> findBooksByNameOrAuthorAndIdGreaterThan(String name, String author, Long number);*/
}
