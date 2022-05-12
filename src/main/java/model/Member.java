package model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Member {
    private int memberId;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;


    public void setMemberId(int memberId) {
        this.memberId = memberId;
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

    public int getMemberId() {
        return memberId;
    }

    @Override
    public String toString() {
        return "Member id: " + memberId + " | Firstname " + firstName + " | Lastname" + lastName + " | DOB: " + dateOfBirth;
    }
}
