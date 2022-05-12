package presistence;

import model.Book;
import model.Member;
import util.DBUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class RepositoryMember {

    PreparedStatement preparedStatement;
    ResultSet resultSet;
    Connection connection;
    Scanner scanner;
    RepositoryBook repositoryBook;

    public RepositoryMember() {
        connection = DBUtil.getDBConnection();
        scanner = new Scanner(System.in);
    }

    public void createMember() throws SQLException {
        String sql = "INSERT INTO members (firstName, lastName, dateOfBirth) VALUES(?, ?, str_to_date(?, '%Y-%m-%d'))";
        preparedStatement = connection.prepareStatement(sql);
        System.out.println("Please enter your name: ");
        preparedStatement.setString(1, scanner.nextLine());
        System.out.println("Enter your lastname: ");
        preparedStatement.setString(2, scanner.nextLine());
        System.out.println("Your date of birth YYYY-MM-DD");
        preparedStatement.setDate(3, Date.valueOf(scanner.nextLine()));
        int result = preparedStatement.executeUpdate();
        if (result > 0) {
            System.out.println("Member created.");
        }
    }

    public void loanBook() throws SQLException {
        System.out.println("Enter book id to loan: ");
        int bookId = scanner.nextInt();
        int memberId = 0;
        scanner.nextLine();
        if (repositoryBook.doesBookExist(bookId)) {
            String sql = "SELECT * FROM books b LEFT OUTER JOIN members m ON b.memberId = m.memberId ";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                Member member = new Member();
                book.setMemberId(resultSet.getInt("b.memberId"));
                book.setReturnDate(resultSet.getDate("returnDate"));
                member.setMemberId(resultSet.getInt("m.memberId"));
                memberId = member.getMemberId();
                if (book.getMemberId() != 0) {
                    System.out.println("Sorry, but book is loaned out. It will be returned: " + book.getReturnDate());
                } else {
                    String sql2 = "UPDATE books SET memberId = ? , returnDate = str_to_date(?, '%Y-%m-%d') WHERE bookId = ?";
                    preparedStatement = connection.prepareStatement(sql2);
                    LocalDate localDate = LocalDate.now();
                    localDate = localDate.plusDays(14);
                    preparedStatement.setInt(1, memberId);
                    preparedStatement.setString(2, String.valueOf(localDate));
                    preparedStatement.setInt(3, bookId);
                    int result = preparedStatement.executeUpdate();
                    if (result > 0) {
                        System.out.println("Book loaned");
                    }
                }
            }
        } else {
            System.out.println("Book with id " + bookId + " doesn't exist in our library.");
        }
    }


}
