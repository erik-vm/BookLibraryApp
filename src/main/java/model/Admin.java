package model;

import java.sql.Date;

public class Admin {
    private int adminId;
    private String password;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "ID: " + adminId + " | Name: " + firstName + " | Surname: " + lastName;
    }
}
