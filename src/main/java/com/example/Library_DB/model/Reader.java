package com.example.Library_DB.model;

import com.example.Library_DB.services.BookServiceImpl;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int personNumber;
    private String firstName;
    private String secondName;
    private String middleName;

    @OneToMany(mappedBy = "reader")
    private Collection<Book> books;

    @Override
    public String toString() {
        return "Reader{" +
                "id=" + id +
                ", personNumber=" + personNumber +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", books=" + books +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reader reader = (Reader) o;
        return id == reader.id && personNumber == reader.personNumber && Objects.equals(firstName, reader.firstName) && Objects.equals(secondName, reader.secondName) && Objects.equals(middleName, reader.middleName) && Objects.equals(books, reader.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, personNumber, firstName, secondName, middleName, books);
    }
}
