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
import isdcm.model.*;

/**
 *
 * @author alumne
 */
public class ServLogUser extends HttpServlet {

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

        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        request.setAttribute("errorUserNameInvalid", "");
        //Attributes validation
        boolean validUsername = !userName.isEmpty();
        if(!validUsername)request.setAttribute("errorUserNameInvalid", "USERNAME IS EMPTY");
        boolean validPassword = !password.isEmpty();
        if(!validPassword)request.setAttribute("errorUserNameInvalid", "PASSWORD IS EMPTY");

        if (validUsername && validPassword) {
            User user = new User(userName, password);
            boolean existsUser = user.existsUser();

            request.getSession().setAttribute("USER_LOGGED", false);
            request.getSession().setAttribute("attributeUserExists", false); //TODO:REVISAAARRR
            request.getSession().setAttribute("name", "");
            request.getSession().setAttribute("USERID", -1);
            request.getSession().setAttribute("surname", "");
            request.getSession().setAttribute("email", "");
            request.getSession().setAttribute("userName", userName);

            if (existsUser) {
                user = user.getUser();
                if(user == null) request.getSession().setAttribute("DB ERROR", true);
                else {
                    request.getSession().setAttribute("name", user.getName());
                    request.getSession().setAttribute("surname", user.getSurename());
                    request.getSession().setAttribute("email", user.getEmail());
                    request.getSession().setAttribute("userName", user.getUserName());
                    request.getSession().setAttribute("PASSWORD FIELD IS EMPTY", !validPassword);

                    request.getSession().setAttribute("USER_LOGGED", true);
                    request.getSession().setAttribute("USERID", user.getId());
                    request.getRequestDispatcher("/listadoVid.jsp").forward(request, response);
                }
            } else {
                request.getSession().setAttribute("USER NOT EXISTS", existsUser);
            }
                //request.getSession().setAttribute(attributeUserExists, true);  //Check
        }
        request.getRequestDispatcher("/login.jsp").forward(request, response);

        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServLogUser</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServLogUser at " + request.getContextPath() + "</h1>");
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
