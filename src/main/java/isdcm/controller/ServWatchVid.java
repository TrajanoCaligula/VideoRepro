package isdcm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import isdcm.DTO.VideoDTO;
import isdcm.model.Video;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


@WebServlet(name = "ServWachVid", urlPatterns = {"/ServWachVid"})
public class ServWatchVid extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getSession(false) != null){
            String idString = request.getParameter("id");
            int id = -1;
            try {
                id = Integer.parseInt(idString);
                // Operaciones con el número entero convertido
            } catch (NumberFormatException e) {
                // Manejo de la excepción si el String no es un número entero válido
                System.err.println("El Identificador del video no es un número entero válido.");
            }
            Video vid = new Video().getVideo(id);
            VideoDTO videoData = new VideoDTO(vid.getId(),
                         vid.getTitle(),
                         vid.getAuthor(),
                         vid.getCreationDate(),
                         vid.getDuration(),
                         vid.getViews(),
                         vid.getDescription(),
                         vid.getFormat(),
                         vid.getUserName(),
                         vid.getUrl());
            String json = new ObjectMapper().writeValueAsString(videoData);
            if(null != videoData && -1 != vid.getId()){
                response.setContentType("application/json");
                response.getWriter().write(json);
            } else {
                request.setAttribute("errorEmptyVideo", "No existe este video en nuestra pagina");
            }
            
            //TESTING
            
            String addressAPI = "http://localhost:8080/REST/resources/jakartaee9/updateViews";  
            
            // Create a URLConnection
            URLConnection connection = new URL(addressAPI).openConnection();

            // Cast to HttpURLConnection (assuming it's a HTTP connection)
            HttpURLConnection httpConnection = (HttpURLConnection) connection;

            // Set the request method
            httpConnection.setRequestMethod("PUT");
            // Send the request and get the response code
            int responseCode = httpConnection.getResponseCode();
            
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Request successful, proceed to get the response body
                // Get the response body as an InputStream
                //TODO

            } else {
                // Handle error based on response code (e.g., throw an exception)
                throw new RuntimeException("Error accessing API: " + responseCode);
            }
            
            //
        }
       
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
