package presistence;

import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class RepositoryBook {
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    Connection connection;
    Scanner scanner =new Scanner(System.in);

    public RepositoryBook(){
        connection = DBUtil.getDBConnection();
    }
    public void addBook() throws SQLException {
        String sql = "INSERT INTO books (title, author, genre, quantity) VALUES (?, ?, ?, ?) ";
        preparedStatement = connection.prepareStatement(sql);
        System.out.println("Enter the name of the book: ");
        preparedStatement.setString(1, scanner.nextLine());
        System.out.println("Enter the author of the book: ");
       preparedStatement.setString(2, scanner.nextLine());
        System.out.println("Enter the genre of the book: ");
        preparedStatement.setString(3, scanner.nextLine());
        System.out.println("Enter the quantity of the book: ");
        preparedStatement.setInt(4, scanner.nextInt());
        int result = preparedStatement.executeUpdate();
        if (result>0){
            System.out.println("Book added to library.");
        }
    }
}
