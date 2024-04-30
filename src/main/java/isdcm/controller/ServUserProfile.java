/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package isdcm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import isdcm.DTO.UserDTO;
import isdcm.model.User;
import isdcm.model.Video;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Alvaro
 */
@WebServlet(name = "ServUserProfile", urlPatterns = {"/ServUserProfile"})
public class ServUserProfile extends HttpServlet {

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
        String userN = (String) request.getSession().getAttribute("USERNAME");
        User us = new User().getUser(userN);
       
       UserDTO userData = new UserDTO(
                        us.getName(),
                        us.getSurname(),
                        us.getEmail(),
                        us.getUserName());
       int num = new Video().getNumVid(userN);
       userData.setNumVidUploaded(num);
       
       String json = new ObjectMapper().writeValueAsString(userData);
           
        if(null != userData){
            response.setContentType("application/json");
            response.getWriter().write(json);
        } else {
            request.setAttribute("errorEmptyVideo", "No existe este usuario en nuestra pagina");
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
        if(request.getSession().getAttribute("USER_LOGGED") != null && !request.getSession().getAttribute("USER_LOGGED").toString().equals("false"))processRequest(request, response);
        else{
            request.setAttribute("Error", "MUST LOG IN TO ACCES HERE");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
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
        request.setAttribute("Error", "This URL is not operational");
        if(request.getSession().getAttribute("USER_LOGGED") != null && !request.getSession().getAttribute("USER_LOGGED").toString().equals("false"))request.getRequestDispatcher("/listadoVid.jsp").forward(request, response);
        else request.getRequestDispatcher("/login.jsp").forward(request, response);
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
