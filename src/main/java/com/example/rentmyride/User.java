package com.example.rentmyride;

public class User {
    private int userid; 
    private String firstname;
    private String lastname;
    private String email;
    private String username;
    private String password;

    //private static User instance;

    public User(String firstname, String lastname, String email, String username, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    /*public static User getInstance(String firstname, String lastname, String email, String username, String password) {
        if (instance == null) {
            instance = new User(firstname, lastname, email, username, password);
        }
        return instance;
    }*/

    //Setters  
    public void setUserId(int userid) {
        this.userid = userid;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    //Getters
    public int getUserId() {
        return userid;
    }
    public String getFullname() {
        return firstname + " " + lastname;
    }
    public String getFirstname() {
        return firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public String getEmail() {
        return email;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
}