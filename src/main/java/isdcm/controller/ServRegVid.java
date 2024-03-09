/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package isdcm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import  isdcm.model.*;
import java.util.Date;
import java.sql.Time;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 *
 * @author alumne
 */
public class ServRegVid extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ServRegVid.class.getName());

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
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String creationDateBeforeParse = request.getParameter("creationDate");
            String durationBeforeParse = request.getParameter("duration");
            String viewsBeforeParse = request.getParameter("views");
            String description = request.getParameter("description");
            String format = request.getParameter("format");
            String userName = (String)request.getSession().getAttribute("USERNAME");
            
            
            java.util.Date utildate = new Date(Calendar.getInstance().getTimeInMillis());
            try {
                utildate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(creationDateBeforeParse);
            } catch (ParseException ex) {
                logger.log(Level.SEVERE, "Error Parse Fecha", ex);
            }
            Date creationDate = new Date(utildate.getTime());

            try {
                utildate = new SimpleDateFormat("hh:mm:ss", Locale.ENGLISH).parse(durationBeforeParse);
            } catch (ParseException ex) {
                 logger.log(Level.SEVERE, "Error Parse Duración", ex);
            }
            
            try {
                utildate = new SimpleDateFormat("hh:mm:ss", Locale.ENGLISH).parse(durationBeforeParse);
            } catch (ParseException ex) {
                 logger.log(Level.SEVERE, "Error Parse Duración", ex);
            }
            Time duration = new Time(utildate.getTime());

            boolean validTitle= !title.isEmpty();
            if(!validTitle)request.setAttribute("errorUserNameInvalid", "TITLE IS EMPTY");
            boolean validAuthor = !author.isEmpty();
            if(!validAuthor)request.setAttribute("errorUserNameInvalid", "AUTHOR IS EMPTY");
            boolean validDescription = !description.isEmpty();
            if(!validDescription)request.setAttribute("errorUserNameInvalid", "DESCRIPTION IS EMPTY");
            boolean validFormat = !format.isEmpty();
            if(!validFormat)request.setAttribute("errorUserNameInvalid", "FORMAT IS EMPTY");

            Video video = new Video(title, author, creationDate, duration, 0, description, format, userName);

            if(video.addVideo()){
                request.setAttribute("errorRegisterVideo", "DB ERROR");
                request.getRequestDispatcher("/registroVid.jsp").forward(request, response);
            }
            request.getRequestDispatcher("/listadoVid.jsp").forward(request, response);

        } else{
            request.setAttribute("errorUserNotLogged", "MUST LOG IN TO UPLOAD A VIDEO");
        }

        request.getRequestDispatcher("/login.jsp").forward(request, response);

        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServRegVid</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServRegVid at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
