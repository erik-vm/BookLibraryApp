package presistence;

import model.Book;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RepositoryBook {
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    Connection connection;
    Scanner scanner ;

    public RepositoryBook() {
        connection = DBUtil.getDBConnection();
        scanner = new Scanner(System.in);
    }

    public void addBook() throws SQLException {
        String sql = "INSERT INTO books (title, author, genre, quantity) VALUES (?, ?, ?, ?) ";
        preparedStatement = connection.prepareStatement(sql);
        System.out.println("Enter the name of the book: ");
        preparedStatement.setString(1, scanner.nextLine());
        System.out.println("Enter the author of the book: ");
        preparedStatement.setString(2, scanner.nextLine());
        System.out.println("Enter the genre of the book (COMEDY, DRAMA, CRIME, HISTORY): ");
        preparedStatement.setString(3, scanner.nextLine().toUpperCase());
        System.out.println("Enter the quantity of the book: ");
        preparedStatement.setInt(4, scanner.nextInt());
        scanner.nextLine();
        int result = preparedStatement.executeUpdate();
        if (result > 0) {
            System.out.println("Book added to library.");
        }
    }

    public void removeBook() throws SQLException {
        System.out.println("Enter book id: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();
        if (doesBookExist(bookId)) {
            String sql = "DELETE FROM books WHERE bookId = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bookId);
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Book deleted.");
            }
        }
    }

    public boolean doesBookExist(int bookId) throws SQLException {
        boolean result = false;
        for (Book book : bookList()) {
            if (book.getBookId() == bookId) {
                result = true;
            }
        }
        return result;
    }

    public void showAllBooks() throws SQLException {
        for (Book book : bookList()) {
            System.out.println(book);
        }

    }

    private List<Book> bookList() throws SQLException {
        List<Book> bookList = new ArrayList<>();
        String sql = "SELECT * FROM books";
        preparedStatement = connection.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Book book = new Book();
            book.setBookId(resultSet.getInt("bookId"));
            book.setTitle(resultSet.getString("title"));
            book.setAuthor(resultSet.getString("author"));
            book.setGenre(Book.genre.valueOf(resultSet.getString("genre")));
            book.setReturnDate(resultSet.getDate("returnDate"));
            book.setMemberId(resultSet.getInt("memberId"));
            bookList.add(book);
        }
        return bookList;
    }

    private void modifyTitle(String newTitle, int bookId) throws SQLException {
        String sql = "UPDATE books SET title = ? WHERE bookId = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, newTitle);
        preparedStatement.setInt(2, bookId);
        preparedStatement.execute();
    }

    private void modifyAuthor(String newAuthor, int bookId) throws SQLException {
        String sql = "UPDATE books SET author = ? WHERE bookId = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, newAuthor);
        preparedStatement.setInt(2, bookId);
        preparedStatement.execute();
    }

    private void modifyGenre(String newGenre, int bookId) throws SQLException {
        String sql = "UPDATE books SET genre = ? WHERE bookId = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, newGenre);
        preparedStatement.setInt(2, bookId);
        preparedStatement.execute();
    }
    private void modifyReturnDate(String newDate, int bookId) throws SQLException {
        String sql = "UPDATE books SET returnDate = str_to_date(?, '%Y-%m-%d') where bookId = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, newDate);
        preparedStatement.setInt(2, bookId);
        preparedStatement.execute();
    }



    public void modifyBook() throws SQLException {
        showAllBooks();
        System.out.println("Enter books id: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();
        if (doesBookExist(bookId)) {
            Book book = new Book();
            String sql = "SELECT * FROM books WHERE bookId = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bookId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                book.setBookId(resultSet.getInt("bookId"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setGenre(Book.genre.valueOf(resultSet.getString("genre").toUpperCase()));
                System.out.println(book);
                System.out.println("Select attribute to change: \n\t1 - Title\n\t2 - Author\n\t3 - Genre\n\t4 - Return date");
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        System.out.println("Enter new title: ");
                        modifyTitle(scanner.nextLine(), bookId);
                        break;
                    case 2:
                        System.out.println("Enter new authors name:");
                        modifyAuthor(scanner.nextLine(), bookId);
                        break;
                    case 3:
                        System.out.println("Enter new genre (COMEDY, DRAMA, CRIME, HISTORY): ");
                        modifyGenre(scanner.nextLine(), bookId);
                        break;
                    case 4:
                        System.out.println("Enter new return date : YYYY-MM-DD");
                        modifyReturnDate(scanner.nextLine(), bookId);
                        scanner.nextLine();
                        break;
                    default:
                        System.out.println("Wrong input.");
                        break;
                }
                System.out.println("Book modified.");
            }
        } else {
            System.out.println("Wrong book id.");
        }
    }
}
