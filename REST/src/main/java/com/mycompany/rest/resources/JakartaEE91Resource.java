package com.mycompany.rest.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.video;
import DTO.VideoDTO;

/**
 *
 * @author 
 */
@Path("jakartaee9")
public class JakartaEE91Resource {
    
    /**
     * Sample of GET method
     * @param videoID
     * @return 
     */
    @Path("getInfo")
    @GET    
    @Produces("application/json")
    public String getInfo (@QueryParam("videoID") String videoID){
        
        video vide = new video();
        vide = vide.getVideo(Integer.parseInt(videoID));
        try { 
            VideoDTO vid = new VideoDTO(vide.getId(),
                                    vide.getTitle(),
                                    vide.getAuthor(),
                                    vide.getCreationDate(),
                                    vide.getDuration(),
                                    vide.getViews(),
                                    vide.getDescription(),
                                    vide.getFormat(),
                                    vide.getUserName(),
                                    vide.getVideoUrl());
            String json = new ObjectMapper().writeValueAsString(vid);
            return json;
        } catch (JsonProcessingException e) {
          System.err.println(e);
          return null;
        }
        catch (Exception e) {
          System.err.println(e);
          return null;
        }
    }

    /**
     * Sample of POST method
     * 
     * @param title
     * @param author
     * @param day
     * @param month
     * @param year
     * @return 
     */
    @Path("postSearch")   
    @POST    
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    public String postSearch (@FormParam("title") String title,  @FormParam("author") String author, @FormParam("day") String day, @FormParam("month") String month, @FormParam("year") String year) 
    {                
        video video = new video();
        List<video> listVideos = new ArrayList<>();
        listVideos = video.getVideosByFilter(title,author,day,month,year);
      
        try {
            List <VideoDTO> listVideosDTO = new ArrayList<>();
            for (video vide : listVideos) {
                VideoDTO vid = new VideoDTO(vide.getId(),
                                    vide.getTitle(),
                                    vide.getAuthor(),
                                    vide.getCreationDate(),
                                    vide.getDuration(),
                                    vide.getViews(),
                                    vide.getDescription(),
                                    vide.getFormat(),
                                    vide.getUserName(),
                                    vide.getVideoUrl());
                listVideosDTO.add(vid);
            }
          String json = new ObjectMapper().writeValueAsString(listVideosDTO);
          return json;
        } catch (JsonProcessingException e) {
          // Handle exception
          return null;
        }
    } 
    
    /**
     * Sample of PUT method
     * 
     * @param id
     * @return 
     */
    @Path("updateViews")   
    @PUT    
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/html")
    public boolean putInfo (@QueryParam("videoID") String id) 
    {     
        video video = new video();
        return video.updateVideo( Integer.parseInt(id)); //todo: canviar el parametre per el id del video
        //TODO: tenir en compte que a part de cridar a la API , si aquesta retorna correcte, augmentem en 1 el html de visites
            
        }
    }

