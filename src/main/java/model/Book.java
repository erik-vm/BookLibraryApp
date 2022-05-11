package model;



public class Book {
    private int bookId;
    private String title;
    private String author;
    private genre genre;
    private int quantity;
    public enum genre{
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "-------------------------------------------------------------------------------------------------------" +
                "\nID: " + bookId + " | Title: " + title +" | Author: " + author + " | Genre: " + genre + " | Quantity: " + quantity
                +"\n-------------------------------------------------------------------------------------------------------";
    }
}
