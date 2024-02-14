package org.example;

public class User {
    private String email;
    private String password;
    private String name;


    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public void setLogin(String login) {
        this.email = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String firstName) {
        this.name = firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}