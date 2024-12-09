package com.example.Library_DB.model;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
@Table
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int personalNumber;
    private String firstName;
    private String secondName;
    private String middleName;


    @OneToMany(mappedBy = "reader")
    private Collection<Book> books;

    @Override
    public String toString() {
        return "Reader{" +
                "id=" + id +
                ", personalNumber=" + personalNumber +
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
        return id == reader.id && personalNumber == reader.personalNumber && Objects.equals(firstName, reader.firstName) && Objects.equals(secondName, reader.secondName) && Objects.equals(middleName, reader.middleName) && Objects.equals(books, reader.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, personalNumber, firstName, secondName, middleName, books);
    }
}
