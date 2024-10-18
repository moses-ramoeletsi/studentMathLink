package com.example.studentmathlink;

public class UserDetails {
    private String name;
    private String email;
    private String address;
    private String phoneNumber;

    private String schoolName;


    public UserDetails (String name, String email, String address, String schoolName,String phoneNumber) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.schoolName =schoolName;

    }

    public String getName () {
        return name;
    }


    public void setName (String name) {
        this.name = name;
    }

    public void setUsername (String name) {
        this.name = name;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getAddress () {
        return address;
    }

    public void setAddress (String address) {
        this.address = address;
    }
    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
    public String getPhoneNumber () {
        return phoneNumber;
    }

    public void setPhoneNumber (String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
