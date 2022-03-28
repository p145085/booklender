package se.lexicon.emiljohansson.booklender.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import static se.lexicon.emiljohansson.booklender.model.constants.EntityConstants.GENERATOR;
import static se.lexicon.emiljohansson.booklender.model.constants.EntityConstants.UUID_GENERATOR;

@Entity
public class LibraryUser {
    @Id
    @GeneratedValue(generator = GENERATOR)
    @GenericGenerator(name = GENERATOR, strategy = UUID_GENERATOR)
    @Column(updatable = false)
    UUID userID;
    LocalDate regDate;
    String name;

    @Column(unique = true)
    String email;

    public LibraryUser() {
    }

    public LibraryUser(LocalDate regDate, String name, String email) {
        this.regDate = regDate;
        this.name = name;
        this.email = email;
    }

    public UUID getUserID() {
        return userID;
    }

    public LocalDate getRegDate() {
        return regDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LibraryUser that = (LibraryUser) o;
        return userID == that.userID && Objects.equals(regDate, that.regDate) && Objects.equals(name, that.name) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, regDate, name, email);
    }

    @Override
    public String toString() {
        return "LibraryUser{" +
                "userID=" + userID +
                ", regDate=" + regDate +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
