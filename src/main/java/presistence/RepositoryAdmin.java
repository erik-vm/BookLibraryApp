package presistence;

import model.Admin;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RepositoryAdmin {
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    Connection connection;
    Scanner scanner;

    public RepositoryAdmin() {
        connection = DBUtil.getDBConnection();
        scanner = new Scanner(System.in);
    }

    public void createAdmin() throws SQLException {
        String sql = "INSERT INTO admins (firstName, lastName, dateOfBirth, password) VALUES(?, ?, str_to_date(?, '%Y-%m-%d'), ?)";
        preparedStatement = connection.prepareStatement(sql);
        System.out.println("Please enter your name: ");
        preparedStatement.setString(1, scanner.nextLine());
        System.out.println("Enter your lastname: ");
        preparedStatement.setString(2, scanner.nextLine());
        System.out.println("Your date of birth YYYY-MM-DD");
        preparedStatement.setDate(3, Date.valueOf(scanner.nextLine()));
        System.out.println("Set your password: ");
        preparedStatement.setString(4, scanner.nextLine());
        int result = preparedStatement.executeUpdate();
        if (result > 0) {
            System.out.println("Admin created.");
        }
    }

    private List<Admin> adminList() throws SQLException {
        String sql = "SELECT * FROM admins";
        List<Admin> adminList = new ArrayList<>();
        preparedStatement = connection.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Admin admin = new Admin();
            admin.setAdminId(resultSet.getInt("adminId"));
            admin.setFirstName(resultSet.getString("firstName"));
            admin.setLastName(resultSet.getString("lastName"));
            admin.setDateOfBirth(resultSet.getDate("dateOfBirth"));
            admin.setPassword(resultSet.getString("password"));
            adminList.add(admin);
        }
        return adminList;
    }

    public void showAllAdmins() throws SQLException {
        List<Admin> adminList = adminList();
        for (Admin admin : adminList) {
            System.out.println(admin);
        }
    }

    private boolean doesAdminExist(int adminId) throws SQLException {
        boolean result = false;
        for (Admin admin : adminList()) {
            if (admin.getAdminId() == adminId) {
                result = true;
            }
        }
        return result;
    }

    public boolean login(int adminId) throws SQLException {
        boolean validUser = false;
        if (doesAdminExist(adminId)) {
            System.out.println("Password: ");
            String password = scanner.nextLine();
            String sql = "SELECT password, adminId FROM admins WHERE adminId = ? ";
            Admin admin = new Admin();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, adminId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                admin.setPassword(resultSet.getString("password"));
            }
            if (admin.getPassword().equals(password)) {
                validUser = true;
                System.out.println("Login successful.");
            } else {
                System.out.println("Invalid password");
            }
        } else {
            System.out.println("Admin with id " + adminId + " does not exist.");
        }
        return validUser;
    }

    public void deleteAdmin() throws SQLException {
        System.out.println("Enter ID of admin to delete: ");
        int adminId = scanner.nextInt();
        scanner.nextLine();
        if (doesAdminExist(adminId)) {
            String sql = "DELETE FROM admins WHERE adminId = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, adminId);
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Admin deleted.");
            }
        } else {
            System.out.println("Wrong ID input.");
        }
    }


}
