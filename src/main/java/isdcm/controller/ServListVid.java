/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package isdcm.controller;

import isdcm.DTO.VideoDTO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.ArrayList;
import isdcm.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;

/**
 *
 * @author alumne
 */
@WebServlet(name = "ServListVid", urlPatterns = {"/ServListVid"})
public class ServListVid extends HttpServlet {

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

        response.setContentType("text/html;charset=UTF-8");

        if(request.getSession(false) != null){
            List <Video> listVideos = new ArrayList<>();
            Video video = new Video();
            listVideos = video.getAllVideos();
            
            /*TESTING
            
            String addressAPI = "http://localhost:8080/REST/resources/jakartaee9/getVideos";  
            
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
                InputStream inputStream = httpConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder responseBody = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                  responseBody.append(line);
                }
                reader.close();
                inputStream.close();

            } else {
                // Handle error based on response code (e.g., throw an exception)
                throw new RuntimeException("Error accessing API: " + responseCode);
            }
            
            */
            
            List <VideoDTO> listVideosDTO = new ArrayList<>();
            for (Video vide : listVideos) {
                VideoDTO vid = new VideoDTO(vide.getId(),
                                    vide.getTitle(),
                                    vide.getAuthor(),
                                    vide.getCreationDate(),
                                    vide.getDuration(),
                                    vide.getViews(),
                                    vide.getDescription(),
                                    vide.getFormat(),
                                    vide.getUserName(),
                                    vide.getUrl());
                listVideosDTO.add(vid);
            }
            String json = new ObjectMapper().writeValueAsString(listVideosDTO);
            if(!listVideosDTO.isEmpty()){
                response.setContentType("application/json");
                response.getWriter().write(json);
            } else {
                request.setAttribute("errorEmptyListVideos", "There are no videos");
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
