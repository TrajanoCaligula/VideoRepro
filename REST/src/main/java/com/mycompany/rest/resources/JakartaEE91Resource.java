package com.mycompany.rest.resources;

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
import model.video;

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
    @Produces("text/html")
    public void getInfo (@QueryParam("videoID") String videoID){
        
        video video = new video();
        video = video.getVideo(Integer.parseInt(videoID));
    }

    /**
     * Sample of POST method
     * 
     * @param filter
     * @param author
     * @param date
     * @return 
     */
    @Path("postInfo")   
    @POST    
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/html")
    public void postInfo (@FormParam("POSTinput") String POSTinput,
                            @FormParam("filter") String filter) 
    {                
        video video = new video();
        List<video> listVideos = new ArrayList<>();
        switch (filter){
            case "Title":
                listVideos = video.getVideosbyTitle(POSTinput);
                break;
            case "Author":
                listVideos = video.getVideosbyAuthor(POSTinput);
                break;
            case "Date":
                listVideos = video.getVideosbyDate(POSTinput);
                break;
            default:
                break; //TODO: reenvii a la pagina?
        }
    } 
    
    /**
     * Sample of PUT method
     * 
     * @return 
     */
    @Path("putInfo")   
    @PUT    
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/html")
    public boolean putInfo () 
    {     
        video video = new video();
        return video.updateVideo(0);
        //TODO: tenir en compte que a part de cridar a la API , si aquesta retorna correcte, augmentem en 1 el html de visites
            
        }
    }
