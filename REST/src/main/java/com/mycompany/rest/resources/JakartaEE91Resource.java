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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import jakarta.json.Json;
import jakarta.json.JsonObject;
/**
 *
 * @author 
 */
@Path("jakartaee9")
public class JakartaEE91Resource {
        private static final String SECRET_KEY = "AJ123456789"; // Replace with your actual secret key
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
    @Produces("application/json")
    public String updateViews (@FormParam("videoID") String id) 
    {     
        video video = new video();
        boolean R= video.updateVideo( Integer.parseInt(id));
        try{
            String json = new ObjectMapper().writeValueAsString(R);
            return json;
        }catch(JsonProcessingException e){
            return "error";
        }
    }
    
    
    /**
 * POST method to login in the application
 * @param username
 * @param password
 * @return
 */
 @Path("login")
 @POST
 @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
 @Produces(MediaType.APPLICATION_JSON)
 public Response Login(@FormParam("username") String username, 
                       @FormParam("password") String password){
     
        // Validar credenciales de usuario (aquí debes implementar tu lógica de autenticación)

        // Si las credenciales son válidas, genera un JWT
        String jwt = Jwts.builder()
                .setSubject(username) // Nombre de usuario como sub
                .setIssuedAt(new Date()) // Fecha de emisión del token
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24 horas de validez
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // Firma con clave secreta
                .compact();

        // Devolver el JWT como respuesta
        return Response.ok().entity(jwt).build();
 }
}

