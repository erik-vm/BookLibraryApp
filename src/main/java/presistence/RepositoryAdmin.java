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
    Scanner scanner = new Scanner(System.in);

    public RepositoryAdmin() {
        connection = DBUtil.getDBConnection();
    }

    public void createAdminAccount() {
        System.out.println("Please enter your name: ");
        String firstName = scanner.nextLine();
        System.out.println("Enter your lastname: ");
        String lastName = scanner.nextLine();
        System.out.println("Your date of birth YYYY-MM-DD");
        Date dateOfBirth = Date.valueOf(scanner.nextLine());
        System.out.println("Set your password: ");
        String password = scanner.nextLine();

        String sql = "INSERT INTO admins (firstName, lastName, dateOfBirth, password) VALUES(?, ?, str_to_date(?, '%Y-%m-%d'), ?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setDate(3, dateOfBirth);
            preparedStatement.setString(4, password);
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Admin created.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Admin> adminList() {
        String sql = "SELECT * FROM admins";
        List<Admin> adminList = new ArrayList<>();
        try {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adminList;
    }

    public void showAllAdmins() {
        List<Admin> adminList = adminList();
        for (Admin admin : adminList) {
            System.out.println(admin);
        }
    }


    private boolean doesAdminExist(int adminId) {
        List<Admin> adminList = adminList();
        boolean result = false;
        for (Admin admin : adminList) {
            if (admin.getAdminId() == adminId) {
                result = true;
            }
        }
        return result;
    }

    public boolean login() {
        boolean validUser = false;
        System.out.println("Enter your id: ");
        int adminId = scanner.nextInt();
        scanner.nextLine();
        if (doesAdminExist(adminId)) {
            System.out.println("Password: ");
            String password = scanner.nextLine();

            String sql = "SELECT * FROM admins WHERE adminId = ? ";
            Admin admin = new Admin();
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, adminId);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    admin.setAdminId(resultSet.getInt("adminId"));
                    admin.setFirstName(resultSet.getString("firstName"));
                    admin.setLastName(resultSet.getString("lastName"));
                    admin.setDateOfBirth(resultSet.getDate("dateOfBirth"));
                    admin.setPassword(resultSet.getString("password"));
                }
                if (admin.getPassword().equals(password)) {
                    validUser = true;
                    System.out.println("Login successful.");
                } else {
                    System.out.println("Invalid password");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Admin with " + adminId + " does not exist.");
        }
        return validUser;
    }

    public Admin returnAdminById(int adminId) throws SQLException {
        String sql = "SELECT * FROM admins WHERE adminId = ?";
        Admin admin = new Admin();
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, adminId);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            admin.setAdminId(resultSet.getInt("adminId"));
            admin.setFirstName(resultSet.getString("firstName"));
            admin.setLastName(resultSet.getString("lastName"));
        }
        return admin;
    }

    public void deleteAdmin() throws SQLException {
        System.out.println("Enter ID of admin to delete: ");
        int adminId = scanner.nextInt();
        scanner.nextLine();
        String sql = "DELETE FROM admins WHERE adminId = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, adminId);
        int result = preparedStatement.executeUpdate();
        if (result>0){
            System.out.println("Admin deleted.");
        }
    }

    public String showPassword(){
        String sql = "SELECT password FROM admins WHERE adminId = ?";



    }
}
