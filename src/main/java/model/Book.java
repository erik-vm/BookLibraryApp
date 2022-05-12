package model;


import java.sql.Date;

public class Book {
    private int bookId;
    private String title;
    private String author;
    private genre genre;
    private Date returnDate;
    private int memberId;

    public enum genre {
        COMEDY,
        DRAMA,
        CRIME,
        HISTORY
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Book.genre getGenre() {
        return genre;
    }

    public void setGenre(Book.genre genre) {
        this.genre = genre;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    @Override
    public String toString() {
        return "-------------------------------------------------------------------------------------------------------" +
                "\nID: " + bookId + " | Title: " + title + " | Author: " + author + " | Genre: " + genre + " | Return date: " + returnDate + " | Member ID : " + memberId
                + "\n-------------------------------------------------------------------------------------------------------";
    }

    public boolean isBookAvailable(){
        return memberId != 0;
    }
}
