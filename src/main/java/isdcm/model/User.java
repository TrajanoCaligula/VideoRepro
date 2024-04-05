/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isdcm.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import isdcm.model.DataForDataBase;
import java.sql.PreparedStatement;

/**
 *
 * @author name
 */

public class User {

    private int id;
    private String name;
    private String surname;
    private String email;
    private String userName;
    private String password;
    
    private static final String DB_HOST = DataForDataBase.DB_HOST;
    private static final String DB_USER = DataForDataBase.DB_USER;
    private static final String DB_PASSWORD = DataForDataBase.DB_PASSWORD;
    private static final String TABLENAME = DataForDataBase.USERS_TABLENAME;
    
    public User() {
        this.id = -1;
        this.name = null;
        this.surname = null;
        this.email = null;
        this.userName = null;
        this.password = null;
    }
    
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public User(int id, String name, String surname, String email, String userName, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.userName = userName;
        this.password = password;
    }

    public User(String name, String surname, String email, String userName, String password) {
        this.id = -1;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.userName = userName;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public boolean existsUser(String userName){
        boolean existsUser = false;
        try {
            Connection conn = DriverManager.getConnection(DB_HOST, DB_USER, DB_PASSWORD);
            
            PreparedStatement statement;
            String query = "select * from " + TABLENAME + " where username=?";
            statement = conn.prepareStatement(query);
            statement.setString(1, userName); 
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                existsUser = true;
            }
            
            return existsUser;            
        } catch (SQLException err) {
            System.out.println(err.getMessage());       
        }
        return existsUser;
    }
    
    public boolean passwordIsCorrect(String userName, String password){
        boolean passwordCorrect = false;
        try {
            Connection conn = DriverManager.getConnection(DB_HOST, DB_USER, DB_PASSWORD);
            
            PreparedStatement statement;
            String query = "select * from " + TABLENAME + " where username=?";
            statement = conn.prepareStatement(query);
            statement.setString(1, userName); 
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                passwordCorrect = (password.equals(rs.getString("PASSWORD")));
            }
            
            return passwordCorrect;            
        } catch (SQLException err) {
            System.out.println(err.getMessage());       
        }
        return passwordCorrect;
    }
    
    
    public User getUser(String userN){
        User user = null;
        try {
            Connection conn = DriverManager.getConnection(DB_HOST, DB_USER, DB_PASSWORD);
            
            PreparedStatement statement;
            String query = "select * from " + TABLENAME + " where username=?";
            statement = conn.prepareStatement(query);
            statement.setString(1, userN); 
            ResultSet rs = statement.executeQuery();
            
            if (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("NAME");
                String surname = rs.getString("SURNAME");
                String email = rs.getString("EMAIL");
                String userName = rs.getString("USERNAME");
                String password = rs.getString("PASSWORD");
                                
                user = new User(id, name, surname, email, userName, password);
            }            
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return user;
    }

    public boolean addUser(){
        boolean result = false;
        try {
            Connection conn = DriverManager.getConnection(DB_HOST, DB_USER, DB_PASSWORD);
            
            PreparedStatement statement;
            String query = "INSERT INTO "+ TABLENAME +" (NAME, SURNAME, EMAIL, USERNAME, PASSWORD) VALUES (?,?,?,?,?)";
            statement = conn.prepareStatement(query);
            statement.setString(1, this.name); 
            statement.setString(2, this.surname); 
            statement.setString(3, this.email); 
            statement.setString(4, this.userName); 
            statement.setString(5, this.password);          
           
            statement.executeUpdate();
            
            result = true;
        } catch (SQLException err) {
            var a = err.getMessage();
            System.out.println(err.getMessage());
        }
        return result;
    }
}
