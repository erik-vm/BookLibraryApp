package main;

import model.Admin;
import presistence.RepositoryAdmin;
import presistence.RepositoryBook;

import java.sql.SQLException;
import java.util.List;

public class MainTest {
    public static void main(String[] args) throws SQLException {

        RepositoryAdmin repositoryAdmin = new RepositoryAdmin();
        RepositoryBook repositoryBook = new RepositoryBook();

        repositoryAdmin.createAdminAccount();
        repositoryAdmin.showAllAdmins();
        repositoryAdmin.login();
        // repositoryAdmin.deleteAdmin();
        //repositoryBook.addBook();
    }
}
