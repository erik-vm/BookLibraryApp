package main;

import model.Admin;
import presistence.RepositoryAdmin;

import java.sql.SQLException;
import java.util.List;

public class MainTest {
    public static void main(String[] args) throws SQLException {

        RepositoryAdmin repositoryAdmin = new RepositoryAdmin();

       //repositoryAdmin.showAllAdmins();
       ///repositoryAdmin.login();
        repositoryAdmin.deleteAdmin();
    }
}
