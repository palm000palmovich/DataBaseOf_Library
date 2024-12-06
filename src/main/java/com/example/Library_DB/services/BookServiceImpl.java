package com.example.Library_DB.services;

import com.example.Library_DB.model.Book;
import com.example.Library_DB.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class BookServiceImpl {
    private BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    //Get all books
    public Collection<Book> getAllBooks(){return bookRepository.findAll();}

    //Find by id
    public Book findById(long id){
        return bookRepository.findById(id).orElse(null);
    }

    //Create
    public Book createBook(Book book){
        return bookRepository.save(book);
    }

    //Edit book
    public Book editBook(long id, Book book){
        Book bookForEdit = bookRepository.findById(id).get();
        if (bookForEdit != null){
            bookForEdit.setAuthor(book.getAuthor());
            bookForEdit.setName(book.getName());
            bookForEdit.setYear(book.getYear());
            bookRepository.save(bookForEdit);
            return bookForEdit;
        }return book;
    }

    //Delete book
    public Book deleteBook(long id){
        Book book = bookRepository.findById(id).get();
        if (book != null){bookRepository.deleteById(id); return book;}
        return null;
    }

    //Delete all
    public void deleteAll(){
        bookRepository.deleteAll();
    }

    public Book findByName(String name){
        return bookRepository.findByNameIgnoreCase(name);
    }

    public Collection<Book> findByAuthor(String author){
        return bookRepository.findBooksByAuthorContainsIgnoreCase(author);
    }

    public Collection<Book> findByNamePart(String part){
        return bookRepository.findAllByNameContainsIgnoreCase(part);
    }
}
