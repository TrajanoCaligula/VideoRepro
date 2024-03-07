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
import isdcm.model.User;

/**
 *
 * @author alumne
 */
public class ServRegUser extends HttpServlet {

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

        String name = request.getParameter("name");
        String username = request.getParameter("surename");
        String email = request.getParameter("email");
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String passwordConfirm = request.getParameter("passwordConfirm");

        //Attributes validation
        boolean validName = !name.isEmpty();
        request.setAttribute("NAME FIELD IS EMPTY", !validName);
        boolean validSurname = !username.isEmpty();
        request.setAttribute("SURENAME FIELD IS EMPTY", !validSurname);
        boolean validEmail = !email.isEmpty();
        request.setAttribute("EMAIL FIELD IS EMPTY", !validEmail);
        boolean validUsername = !userName.isEmpty();
        request.setAttribute("USERNAME IS EMPTY", !validUsername);
        boolean validPassword = !password.isEmpty() && !passwordConfirm.isEmpty();
        request.setAttribute("PASSWORD FIELD IS EMPTY", !validPassword);
        validPassword = password.equals(passwordConfirm);
        request.setAttribute("PASSWORDS DO NOT MATCH", !validPassword);

        if (validName && validSurname && validEmail && validUsername && validPassword){
            User user = new User(name, username, email, userName, password);

            boolean existsUser = user.existsUser();
            if(existsUser) {
                request.setAttribute("ALREADY EXISTING USER", existsUser);
                request.getRequestDispatcher("/registro.jsp").forward(request, response);
            } else {
                boolean created = user.addUser();
                request.setAttribute("USER REGISTERED", created);
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }

        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServRegUser</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServRegUser at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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

}
