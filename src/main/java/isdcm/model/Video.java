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
import java.util.List;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import isdcm.model.DataForDataBase;
import java.sql.PreparedStatement;

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
    private String videoUrl;
    //private URL imageUrl;
    private String format;
    private String userName;

    private static final String DB_HOST = DataForDataBase.DB_HOST;
    private static final String DB_USER = DataForDataBase.DB_USER;
    private static final String DB_PASSWORD = DataForDataBase.DB_PASSWORD;
    private static final String TABLENAME = DataForDataBase.VIDEOS_TABLENAME;
    
    public Video() {
        this.id = -1;
        this.title = "";
        this.author = "";
        this.creationDate = null;
        this.duration = null;
        this.views = -1;
        this.description = "";
        this.format = "";
        this.userName = "";
        this.videoUrl = "";
    }

    public Video(int id, String title, String author, Date creationDate, Time duration, long views, String description, String format, String userName, String url) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.creationDate = creationDate;
        this.duration = duration;
        this.views = views;
        this.description = description;
        this.format = format;
        this.userName = userName;
        this.videoUrl = url;
    }

    public Video(int id, String title, String author, Date creationDate, Time duration, long views, String description, String format, String url) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.creationDate = creationDate;
        this.duration = duration;
        this.views = views;
        this.description = description;
        this.format = format;
        this.videoUrl = url;
    }

    public Video(String title, String author, Date creationDate, Time duration, long views, String description, String format, String userName, String url) {
        this.title = title;
        this.author = author;
        this.creationDate = creationDate;
        this.duration = duration;
        this.views = views;
        this.description = description;
        this.format = format;
        this.userName = userName;
        this.videoUrl = url;
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
    
    public String getUserName() {
        return userName;
    }
     public String getUrl() {
        return videoUrl;
    }
    public void setUrl(String url) {
        this.videoUrl = url;
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
                ", userName='" + userName+ '\'' +
                ", videoUrl='" + videoUrl+ '\'' +
                '}';
    }

    public Video getVideo(int id) {
        Video video = null;
        try {
            Connection conn = DriverManager.getConnection(DB_HOST, DB_USER, DB_PASSWORD);
            
            PreparedStatement statement;            

            String sql = "UPDATE " + TABLENAME + " SET VIEWS = VIEWS+1 WHERE ID=? ";
            statement = conn.prepareStatement(sql);
            statement.setString(1, String.valueOf(id)); 
            statement.executeUpdate();
            
            PreparedStatement statement2;
            sql = "SELECT * FROM " + TABLENAME + " WHERE ID=?"; 
            statement2 = conn.prepareStatement(sql);
            statement2.setString(1, String.valueOf(id)); 
            ResultSet rs = statement2.executeQuery();
            
            if (rs.next()) {
                int idV = rs.getInt("ID");
                String title = rs.getString("TITLE");
                String author = rs.getString("AUTHOR");
                Date creationDate = rs.getDate("CREADATE");
                Time duration = rs.getTime("DURATION");
                long views = rs.getLong("VIEWS");
                String description = rs.getString("DESCRIPTION");
                String format = rs.getString("FORMAT");
                String userName = rs.getString("USERNAME");
                String videoUrl = rs.getString("URL");
                                
                video = new Video(idV, title, author, creationDate, duration, views, description, format, userName, videoUrl);
            }            

        } catch (SQLException err) {
            var a =err.getMessage();
            System.out.println(err.getMessage());
        }
        return video;
    }

    public List <Video> getAllVideos() {
        List<Video> listVideos = new ArrayList<>();
        Video video = null;
        try {
            Connection conn = DriverManager.getConnection(DB_HOST, DB_USER, DB_PASSWORD);
            
            PreparedStatement statement;            
            String sql = "SELECT * FROM " + TABLENAME;
            statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int idV = rs.getInt("ID");
                String title = rs.getString("TITLE");
                String author = rs.getString("AUTHOR");
                Date creationDate = rs.getDate("CREADATE");
                Time duration = rs.getTime("DURATION");
                long views = rs.getLong("VIEWS");
                String description = rs.getString("DESCRIPTION");
                String format = rs.getString("FORMAT");
                String userName = rs.getString("USERNAME");
                String videoUrl = rs.getString("URL");
                                
                video = new Video(idV, title, author, creationDate, duration, views, description, format, userName, videoUrl);
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
            
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            // Convert the Date object to a String
            String formattedDate = formatter.format(creationDate);
            PreparedStatement statement; 
            String sql = "INSERT INTO " + TABLENAME + "(TITLE, AUTHOR, CREADATE, DURATION, VIEWS, DESCRIPTION, FORMAT, USERNAME, URL) VALUES (?,?,?,?,?,?,?,?,?)";
                       
            statement = conn.prepareStatement(sql);
            statement.setString(1, this.title); 
            statement.setString(2, this.author);
            statement.setString(3, formattedDate);
            statement.setString(4, this.duration.toString()); 
            statement.setString(5, String.valueOf(this.views));
            statement.setString(6, this.description); 
            statement.setString(7, this.format); 
            statement.setString(8, this.userName);
            statement.setString(9, this.videoUrl); 
            statement.executeUpdate();
            result = true;
        } catch (SQLException err) {
            var a = err.getMessage();
            System.out.println(err.getMessage());
            
        }
        return result;
    }
    
    
    public int getLastIndex(){
    int result = -1;
        try {
            Connection conn = DriverManager.getConnection(DB_HOST, DB_USER, DB_PASSWORD);
            Statement stmt = conn.createStatement();
            
            PreparedStatement statement;            
            String query = "SELECT MAX(ID) AS Max_Id FROM" + TABLENAME ;
            statement = conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                result = rs.getInt("Max_Id");
            }
            
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return result;
    }
}
