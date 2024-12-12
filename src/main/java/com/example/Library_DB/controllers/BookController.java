package com.example.Library_DB.controllers;

import com.example.Library_DB.model.Book;
import com.example.Library_DB.model.BookCover;
import com.example.Library_DB.services.BookCoverService;
import com.example.Library_DB.services.BookServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
@RestController
@RequestMapping(path = "/book")
public class BookController {
    private final BookServiceImpl bookService;
    private final BookCoverService bookCoverService;

    @Autowired
    public BookController(BookServiceImpl bookService, BookCoverService bookCoverService){
        this.bookService = bookService;
        this.bookCoverService = bookCoverService;
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


    //Загрузка обложки
    @PostMapping(value = "/{id}/cover", consumes = MediaType.MULTIPART_FORM_DATA_VALUE) //большие данные => делятся на части
    public ResponseEntity<String> uploadCover(@PathVariable Long id,
                                              @RequestParam MultipartFile cover) throws IOException{
        if (cover.getSize() > 1024*300){return ResponseEntity.badRequest().body("File is too big!");}
        bookCoverService.uploadCover(id, cover);
        return ResponseEntity.ok().build();
    }

    //Возвращает уменьшенную копию обложки
    @GetMapping(value = "/{id}/cover/preview")
    public ResponseEntity<byte[]> downloadCover(@PathVariable Long id){
        BookCover bookCover = bookCoverService.findBookCover(id);

        //Записываем в заголовки, чтобы ввод/вывод был правильным
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(bookCover.getMediaType()));
        headers.setContentLength(bookCover.getPreview().length);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(bookCover.getPreview());
    }

    //Возвращает оригинальную версию обложки
    @GetMapping(value = "/{id}/cover")
    public void downloadCover(@PathVariable Long id,
                              HttpServletResponse response) throws IOException{
        BookCover bookCover = bookCoverService.findBookCover(id);

        Path path = Path.of(bookCover.getFilePath());

        try(
                InputStream is = Files.newInputStream(path);
                OutputStream os = response.getOutputStream();){

            response.setStatus(200);
            response.setContentType(bookCover.getMediaType());
            response.setContentLength((int) bookCover.getFileSize());
            is.transferTo(os);
        }
    }

}
