package com.example.Library_DB.services;


import com.example.Library_DB.model.Book;
import com.example.Library_DB.model.BookCover;
import com.example.Library_DB.repositories.BookCoverRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional  //Чтобы работало @Lob
public class BookCoverService {
    @Value("${books.cover.dir.path}")
    private String coversDir;  //Название папки с названием обложки

    private final BookServiceImpl bookService;
    private final BookCoverRepository bookCoverRepository;


    @Autowired
    public BookCoverService(BookServiceImpl bookService,
                            BookCoverRepository bookCoverRepository){
        this.bookService = bookService;
        this.bookCoverRepository = bookCoverRepository;
    }

    public void uploadCover(long bookId, MultipartFile file) throws IOException{
        Book book = bookService.findById(bookId);
        Path filePath = Path.of(coversDir, bookId + "." + getExtension(file.getOriginalFilename()));

        Files.createDirectories(filePath.getParent());  //Проверяет, есть ли папки, указнные по данному пути
        Files.deleteIfExists(filePath);  //Удаление файла, если он уже существует


        //Работа с потоками
        try (InputStream is = file.getInputStream();  //С помощью этой конструкции все созданные потоки данных закроются автоматически
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
             ){
            bis.transferTo(bos);
        }

        BookCover bookCover = findBookCover(bookId);
        //Сохранение данных на локальный диск
        bookCover.setBook(book);
        bookCover.setFilePath(filePath.toString());
        bookCover.setFileSize(file.getSize());
        bookCover.setMediaType(file.getContentType());
        bookCover.setPreview(generateImagePreview(filePath));

        bookCoverRepository.save(bookCover);
    }

    public BookCover findBookCover(long bookId){
        return bookCoverRepository.findByBookId(bookId).orElse(new BookCover());
    }

    //Сжатие картинки, которую прислал пользователь
    private byte[] generateImagePreview(Path filePath) throws IOException{
        try (InputStream is = Files.newInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream();){
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics = preview.createGraphics();
            graphics.drawImage(image, 0, 0, 100, height, null);
            graphics.dispose();

            ImageIO.write(preview, getExtension(filePath.getFileName().toString()), baos);
            return baos.toByteArray();
        }
    }


    //Метод определяет расширение файла по его названию
    public String getExtension(String fileName){
        return fileName.substring(fileName.lastIndexOf(" " + 1));
    }


}
