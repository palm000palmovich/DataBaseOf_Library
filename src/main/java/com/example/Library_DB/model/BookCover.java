package com.example.Library_DB.model;

import jakarta.persistence.*;

@Entity
public class BookCover {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String filePath;  //Путь к файлу на диске
    private long fileSize;  //Размер файла
    private String mediaType; //Тип файла

    @Lob
    private byte[] preview;  //Тут хранится обложка в умеьшенном размере

    @OneToOne
    private Book book;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public byte[] getPreview() {
        return preview;
    }

    public void setPreview(byte[] preview) {
        this.preview = preview;
    }

    public com.example.Library_DB.model.Book getBook() {
        return book;
    }

    public void setBook(com.example.Library_DB.model.Book book) {
        book = book;
    }
}
