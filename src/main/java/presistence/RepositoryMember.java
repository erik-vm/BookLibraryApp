package presistence;

import model.Book;
import model.Member;
import util.DBUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
        repositoryBook = new RepositoryBook();
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

    public void deleteMember(int memberId) throws SQLException{
        String sql = "DELETE FROM members WHERE memberId = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, memberId);
        int result = preparedStatement.executeUpdate();
        if (result>0){
            System.out.println("Member deleted.");
        }
    }

    public void showAllMembers() throws SQLException {

        for (Member member : memberList()){
            System.out.println(member);
        }
    }

    public List<Member> memberList() throws SQLException {
        List<Member> memberList = new ArrayList<>();
        String sql = "SELECT * FROM members";
        preparedStatement =connection.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            Member member = new Member();
            member.setMemberId(resultSet.getInt("memberId"));
            member.setFirstName(resultSet.getString("firstName"));
            member.setLastName(resultSet.getString("lastName"));
            member.setDateOfBirth(resultSet.getDate("dateOfBirth"));
            memberList.add(member);
        }
        return memberList;
    }

    public boolean doesMemberExist(int memberId) throws SQLException {
        boolean doesExist = false;
        for (Member member : memberList()) {
            if (member.getMemberId() == memberId){
                doesExist = true;
            }
        }
        return doesExist;
    }

    public void loanBook(int memberId) throws SQLException {
        System.out.println("Enter book id to loan: ");
        int bookId = scanner.nextInt();
        Book book = null;
        Member member = null;
        scanner.nextLine();
        if (repositoryBook.doesBookExist(bookId)) {
            String sql = "SELECT memberId, returnDate FROM books WHERE bookId = ? ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bookId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                book = new Book();
                book.setMemberId(resultSet.getInt("memberId"));
                book.setReturnDate(resultSet.getDate("returnDate"));

                if (book.getMemberId() != 0) {
                    System.out.println("Sorry, but book is loaned out. It will be returned: " + book.getReturnDate());
                } else {
                    String sql2 = "SELECT firstName, lastName FROM members WHERE memberId = ?";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, memberId);
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        member = new Member();
                        member.setMemberId(resultSet.getInt("memberId"));
                        member.setFirstName(resultSet.getString("firstName"));
                        member.setLastName(resultSet.getString("lastName"));
                    }
                    String sql3 = "UPDATE books SET memberId = ? , returnDate = str_to_date(?, '%Y-%m-%d') WHERE bookId = ?";
                    preparedStatement = connection.prepareStatement(sql3);
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

    public void returnBook(int memberId) throws SQLException {
        String sql = "UPDATE books SET memberId = NULL, returnDate = NULL WHERE bookId = ? ";
        System.out.println("Enter id of a book you want to return: ");
        memberCurrentBooksPrint(memberId);
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, scanner.nextInt());
        int result = preparedStatement.executeUpdate();
        if (result > 0) {
            System.out.println("Book returned.");
        }
    }

    private List<Book> showMembersCurrentBooks(int memberId) throws SQLException {
        List<Book> bookList = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE memberId = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, memberId);
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

    public void memberCurrentBooksPrint(int memberId) throws SQLException {
        for (Book book : showMembersCurrentBooks(memberId)) {
            System.out.println(book);
        }
    }

}
