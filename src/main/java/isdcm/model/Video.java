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
import java.util.Date;
import java.sql.Time;

/**
 *
 * @author name
 */

public class Video {
    
    private int id;
    private String title;
    private String author;
    private Date creationDate;
    private Time duration;
    private long views;
    private String description;
    //private URL videoUrl;
    //private URL imageUrl;
    private String format;

    private static final String DB_HOST = "jdbc:derby://localhost:1527/VideosRepository";
    private static final String DB_USER = "JAUVARO";
    private static final String DB_PASSWORD = "AJ1234";
    private static final String TABLENAME = "VIDEOS";

    public Video(int id, String title, String author, Date creationDate, Time duration, long views, String description, String format) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.creationDate = creationDate;
        this.duration = duration;
        this.views = views;
        this.description = description;
        this.format = format;
    }

    public Video(String title, String author, Date creationDate, Time duration, long views, String description, String format) {
        this.title = title;
        this.author = author;
        this.creationDate = creationDate;
        this.duration = duration;
        this.views = views;
        this.description = description;
        this.format = format;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", duration='" + duration + '\'' +
                ", views='" + views + '\'' +
                ", description='" + description + '\'' +
                ", format='" + format + '\'' +
                '}';
    }

    public Video getVideo() {
        Video video = null;
        try {
            Connection conn = DriverManager.getConnection(DB_HOST, DB_USER, DB_PASSWORD);
            Statement stmt = conn.createStatement();
            
            String sql = "SELECT * FROM " + TABLENAME + " WHERE username='" + this.userName + "' AND password='" + this.password + "'"; // TODO: quin es lidentificador de video?
            System.out.println("Getvideo SQL: " + sql);
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                int id = rs.getInt("ID");
                String title = rs.getString("TITLE");
                String author = rs.getString("AUTHOR");
                String creationDate = rs.getString("CREATIONDATE");
                String duration = rs.getString("DURATION");
                String views = rs.getString("VIEWS");
                String description = rs.getString("DESCRIPTION");
                String format = rs.getString("FORMAT");
                                
                video = new User(id, title, author, creationDate, duration, views, description, format);
            }            
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return video;
    }

    public List <Video> getAllVideos() {
        List<Video> listVideos = new ArrayList<>();
        Video video = null;
        try {
            Connection conn = DriverManager.getConnection(DB_HOST, DB_USER, DB_PASSWORD);
            Statement stmt = conn.createStatement();
            
            String sql = "SELECT * FROM " + TABLENAME;
            System.out.println("Getlistofvideos SQL: " + sql);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("ID");
                String title = rs.getString("TITLE");
                String author = rs.getString("AUTHOR");
                String creationDate = rs.getString("CREATIONDATE");
                String duration = rs.getString("DURATION");
                String views = rs.getString("VIEWS");
                String description = rs.getString("DESCRIPTION");
                String format = rs.getString("FORMAT");
                                
                video = new User(id, title, author, creationDate, duration, views, description, format);
                listVideos.add(video);
            }            
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return listVideos;
    }

    public boolean addVideo(){
        boolean result = false;
        try {
            Connection conn = DriverManager.getConnection(DB_HOST, DB_USER, DB_PASSWORD);
            Statement stmt = conn.createStatement();
            
            String sql = "INSERT INTO " + TABLENAME
                    + "(TITLE, AUTHOR, CREATIONDATE, DURATION, VIEWS, DESCRIPTION, FORMAT)"
                   + " VALUES ('" + this.title + "', '" + this.author + "', '" + this.creationDate + "', '" + this.duration + "', '" + this.views + "', '" + this.description + "', '" + this.format + "')";
            System.out.println("addVideo SQL: " + sql);
            stmt.executeUpdate(sql);
            
            result = true;
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return result;
    }
}
