/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author jaume
 */
public class video {
    
    private static final String DB_HOST = DataForDataBase.DB_HOST;
    private static final String DB_USER = DataForDataBase.DB_USER;
    private static final String DB_PASSWORD = DataForDataBase.DB_PASSWORD;
    private static final String TABLENAME = DataForDataBase.VIDEOS_TABLENAME;
    
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

    public video() {
        this.id = -1;
        this.title = null;
        this.author = null;
        this.creationDate = null;
        this.duration = null;
        this.views = -1;
        this.description = null;
        this.videoUrl = null;
        this.format = null;
        this.userName = null;
    }
    
    public video(int id, String title, String author, Date creationDate, Time duration, long views, String description, String videoUrl, String format, String userName) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.creationDate = creationDate;
        this.duration = duration;
        this.views = views;
        this.description = description;
        this.videoUrl = videoUrl;
        this.format = format;
        this.userName = userName;
    }

    public video(String title, String author, Date creationDate, Time duration, long views, String description, String videoUrl, String format, String userName) {
        this.title = title;
        this.author = author;
        this.creationDate = creationDate;
        this.duration = duration;
        this.views = views;
        this.description = description;
        this.videoUrl = videoUrl;
        this.format = format;
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Time getDuration() {
        return duration;
    }

    public long getViews() {
        return views;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getFormat() {
        return format;
    }

    public String getUserName() {
        return userName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
    
    public video getVideo(int id) {
        video video = null;
        try {
            Connection conn = DriverManager.getConnection(DB_HOST, DB_USER, DB_PASSWORD);
            
            PreparedStatement statement;
            String query = "select * from " + TABLENAME + " where ID=?";
            statement = conn.prepareStatement(query);
            statement.setString(1, String.valueOf(id)); 
            ResultSet rs = statement.executeQuery();
            
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
                                
                video = new video(idV, title, author, creationDate, duration, views, description, videoUrl, format, userName);
            }            

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return video;
    }
    
    public boolean updateVideo(int id) {
        int result = 0;
        try {
            Connection conn = DriverManager.getConnection(DB_HOST, DB_USER, DB_PASSWORD);
            
            PreparedStatement statement;
            String query = "UPDATE "+ TABLENAME + " SET VIEWS=VIEWS+1 WHERE ID=?";
            statement = conn.prepareStatement(query);
            statement.setString(1, String.valueOf(id)); 
            result = statement.executeUpdate();
            
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return (result > 0);
    }
    
    
    
    public List <video> getVideosByFilter(String titleParam, String authorParam ,String day,String month, String year){
        List<video> listVideos = new ArrayList<>();
        video video = null;
        if((titleParam != null && !titleParam.isEmpty() ) || (authorParam != null && !authorParam.isEmpty()) || (day != null && !day.equals("----")) || (month != null && !month.equals("----")) || (year != null && !year.equals("----"))){      
            try {
                Connection conn = DriverManager.getConnection(DB_HOST, DB_USER, DB_PASSWORD);         
                PreparedStatement statement;
                String query = "select * from " + TABLENAME;
                Boolean whereAdded = false;
                if(titleParam != null && !titleParam.isEmpty()){
                    whereAdded = true;
                    query= query + " where title=?";
                }
                if(authorParam != null && !authorParam.isEmpty()){
                    if(whereAdded){
                        query = query + " AND author=?";
                    }
                    else{
                        whereAdded = true;
                        query = query + " where author=?";
                    }   
                }
                statement = conn.prepareStatement(query);
                if(titleParam != null && !titleParam.isEmpty()){
                    statement.setString(1, titleParam );
                    if(authorParam != null && !authorParam.isEmpty())statement.setString(2, authorParam );
                }
                else if(authorParam != null && !authorParam.isEmpty())statement.setString(1, authorParam );

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

                    video = new video(idV, title, author, creationDate, duration, views, description, videoUrl, format, userName);
                    listVideos.add(video);
                }   
            }catch (SQLException err) {
                System.out.println(err.getMessage());
            }

            List<video> listVideosFiltered = new ArrayList<>();

            for (video vide : listVideos){
                boolean add = true;
                if(day != null && !day.equals("----") && vide.getCreationDate().getDate() != Integer.parseInt(day)) add = false;
                if(month != null && !month.equals("----") && (vide.getCreationDate().getMonth()+1) != Integer.parseInt(month))add = false;
                if(year != null && !year.equals("----") && (vide.getCreationDate().getYear()+1900) != Integer.parseInt(year))add = false;
                if(add)listVideosFiltered.add(vide);
            }

            return listVideosFiltered;
        }
        else return null; 
    }
            
    
    
}
