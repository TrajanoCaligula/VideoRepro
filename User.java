package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author name
 */

public class User {

    private int id;
    private String name;
    private String surename;
    private String email;
    private String userName;
    private String password;

    private static final String DB_HOST = "";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";
    private static final String TABLENAME = "USERS";

    public User() {
        this.id = -1;
        this.name = null;
        this.surename = null;
        this.email = null;
        this.userName = null;
        this.password = null;
    }

    public User(int id, String name, String surename, String email, String userName, String password) {
        this.id = id;
        this.name = name;
        this.surename = surename;
        this.email = email;
        this.userName = userName;
        this.password = password;
    }

    public User(String name, String surename, String email, String userName, String password) {
        this.id = -1;
        this.name = name;
        this.surename = surename;
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

    public String getSurename() {
        return surename;
    }

    public void setSurename(String surename) {
        this.surename = surename;
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
                ", surename='" + surename + '\'' +
                ", email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public boolean existsUser(){
        boolean existsUser = true;
        try {
            Connection conn = DriverManager.getConnection(DB_HOST, DB_USER, DB_PASSWORD);
            Statement stmt = conn.createStatement();
            
            String sql = "SELECT COUNT(*) as COUNT FROM " + TABLENAME + " WHERE username='" + this.userName + "' OR email=" + this.email;
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                existsUser = (rs.getInt("COUNT") > 0);
            }
            
            return existsUser;            
        } catch (SQLException err) {
            System.out.println(err.getMessage());       
        }
        return existsUser;
    }

    public boolean createUser(){
        boolean result = false;
        try {
            Connection conn = DriverManager.getConnection(DB_HOST, DB_USER, DB_PASSWORD);
            Statement stmt = conn.createStatement();
            
            String sql = "INSERT INTO " + TABLENAME
                    + "(NAME, SURNAME, EMAIL, USERNAME, PASSWORD)"
                   + " VALUES ('" + this.name + "', '" + this.surename + "', '" + this.email + "', '" + this.userName + "', '" + this.password + "')";
            System.out.println("Createuser SQL: " + sql);
            stmt.executeUpdate(sql);
            
            result = true;
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return result;
    }
    
    public User getUser(){
        User user = null;
        try {
            Connection conn = DriverManager.getConnection(DB_HOST, DB_USER, DB_PASSWORD);
            Statement stmt = conn.createStatement();
            
            String sql = "SELECT * FROM " + TABLENAME + " WHERE username='" + this.userName + "' AND password='" + this.password + "'";
            System.out.println("Getuser SQL: " + sql);
            ResultSet rs = stmt.executeQuery(sql);
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
}
