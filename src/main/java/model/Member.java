package model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Member {
    private int memberId;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private List<Book> booksCurrentlyLoaned = new ArrayList<>();


    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<Book> getBooksCurrentlyLoaned() {
        return booksCurrentlyLoaned;
    }

    public void setBooksCurrentlyLoaned(List<Book> booksCurrentlyLoaned) {
        this.booksCurrentlyLoaned = booksCurrentlyLoaned;
    }
}
