package com.example.Library_DB.controllers;

import com.example.Library_DB.model.Book;
import com.example.Library_DB.services.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(path = "/book")
public class BookController {
    private final BookServiceImpl bookService;

    @Autowired
    public BookController(BookServiceImpl bookService){
        this.bookService = bookService;
    }

    //Get all
    @GetMapping
    public ResponseEntity getAll(@RequestParam(required = false) String name,
                                   @RequestParam(required = false) String author,
                                   @RequestParam(required = false) String part){
        if (name != null){return ResponseEntity.ok(bookService.findByName(name));}
        if (author != null){return ResponseEntity.ok(bookService.findByAuthor(author));}
        if (part != null){return ResponseEntity.ok(bookService.findByNamePart(part));}

        return ResponseEntity.ok(bookService.getAllBooks());}

    //GET
    @GetMapping("/{id}")
    public ResponseEntity<Book> get(
            @PathVariable("id") long id){
        Book book = bookService.findById(id);
        if (book == null){return ResponseEntity.notFound().build();}
        return ResponseEntity.ok(book);
    }

    //POST
    @PostMapping
    public ResponseEntity<Book> create(
            @RequestBody Book book){
        return ResponseEntity.ok(bookService.createBook(book));
    }

    //PUT
    @PutMapping("/{id}")
    public ResponseEntity<Book> edit(
            @PathVariable("id") long id,
            @RequestBody Book book){
        Book bookForEdit = bookService.editBook(id, book);
        if (bookForEdit == null){return ResponseEntity.notFound().build();}
        return ResponseEntity.ok(bookService.editBook(id, book));
    }

    //DELETE
    @DeleteMapping("{id}")
    public ResponseEntity<Book> delete(
            @PathVariable("id") long id) {
        Book book = bookService.deleteBook(id);
        if (book == null){return ResponseEntity.notFound().build();}
        return ResponseEntity.ok(bookService.deleteBook(id));
    }

    //Delete all
    @RequestMapping("/clear")
    public void clearDB(){
        bookService.deleteAll();
    }
}
