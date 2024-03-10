/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package isdcm.controller;

import isdcm.DTO.VideoDTO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.ArrayList;
import  isdcm.model.*;
/**
 *
 * @author alumne
 */
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

        List <Video> listVideos = new ArrayList<>();
        Video video = new Video();
        listVideos = video.getAllVideos();
        List <VideoDTO> listVideosDTO = new ArrayList<>();
        for(int i = 0; i < listVideos.size();++i){
            VideoDTO vid = new VideoDTO(listVideos.get(i).getId(),
                                    listVideos.get(i).getTitle(),
                                    listVideos.get(i).getAuthor(),
                                    listVideos.get(i).getCreationDate(),
                                    listVideos.get(i).getDuration(),
                                    listVideos.get(i).getViews(),
                                    listVideos.get(i).getDescription(),
                                    listVideos.get(i).getFormat(),
                                    listVideos.get(i).getUserName());
            listVideosDTO.add(vid);
        }
        if(!listVideosDTO.isEmpty()){
            request.setAttribute("videoList", listVideos);
            request.getRequestDispatcher("/listadoVid.jsp").forward(request, response);
        } else {
            request.setAttribute("errorEmptyListVideos", "There are no videos");
        }

        request.getRequestDispatcher("/listadoVid.jsp").forward(request, response);     //TODO: comprovar si va be

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
