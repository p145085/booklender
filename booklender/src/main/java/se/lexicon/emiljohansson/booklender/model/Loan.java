package se.lexicon.emiljohansson.booklender.model;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.UUID;

import static se.lexicon.emiljohansson.booklender.model.constants.EntityConstants.GENERATOR;
import static se.lexicon.emiljohansson.booklender.model.constants.EntityConstants.UUID_GENERATOR;

@Entity
public class Loan {
    @Id
    @GeneratedValue(generator = GENERATOR)
    @GenericGenerator(name = GENERATOR, strategy = UUID_GENERATOR)
    @Column(updatable = false)
    UUID loanID;

    @ManyToOne
    LibraryUser loanTaker;

    @ManyToOne
    Book book;

    LocalDate loanDate;
    boolean concluded;

    public Loan() {
    }

    public Loan(LibraryUser loanTaker, Book book, LocalDate loanDate, boolean concluded) {
        this.loanTaker = loanTaker;
        this.book = book;
        this.loanDate = loanDate;
        this.concluded = concluded;
    }

    public UUID getLoanID() {
        return loanID;
    }

    public LibraryUser getLoanTaker() {
        return loanTaker;
    }

    public void setLoanTaker(LibraryUser loanTaker) {
        this.loanTaker = loanTaker;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public boolean isOverdue() { //---------------------------------------------------------
        LocalDate due = getLoanDate().plus(book.getMaxLoanDays(), ChronoUnit.DAYS);
        if (LocalDate.now().isAfter(due)){
            return true;
        } else return false;
    }

    public BigDecimal getFine() { //---------------------------------------------------------
        // for every day past the duedate
        LocalDate foo = loanDate.plus(getBook().maxLoanDays, ChronoUnit.DAYS);
        int days = Period.between(foo, LocalDate.now()).getDays();
        return book.getFinePerDay().multiply(BigDecimal.valueOf(days));
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public boolean isConcluded() {
        return concluded;
    }

    public void setConcluded(boolean concluded) {
        this.concluded = concluded;
    }

    public boolean extendLoan(int days) { //---------------------------------------------------------
        try {
            book.setMaxLoanDays(book.getMaxLoanDays() + days);
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        } return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return loanID == loan.loanID && concluded == loan.concluded && Objects.equals(loanTaker, loan.loanTaker) && Objects.equals(book, loan.book) && Objects.equals(loanDate, loan.loanDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loanID, loanTaker, book, loanDate, concluded);
    }

    @Override
    public String toString() {
        return "Loan{" +
                "loanID=" + loanID +
                ", loanTaker=" + loanTaker +
                ", book=" + book +
                ", loanDate=" + loanDate +
                ", concluded=" + concluded +
                '}';
    }
}
