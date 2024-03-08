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
import java.util.regex.*;
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
        String username = request.getParameter("surname");
        String email = request.getParameter("email");
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String passwordConfirm = request.getParameter("passwordConfirm");

        //Attributes validation
        boolean validName = !name.isEmpty();
        if(!validName)request.setAttribute("errorRegUsuFail","NAME FIELD IS EMPTY");
        boolean validSurname = !username.isEmpty();
        if(!validSurname)request.setAttribute("errorRegUsuFail","SURNAME FIELD IS EMPTY");
        boolean validEmail = !email.isEmpty();
        if(validEmail)request.setAttribute("errorRegUsuFail","EMAIL FIELD IS EMPTY");
        boolean validUsername = !userName.isEmpty();
        if(!validUsername)request.setAttribute("errorRegUsuFail", "USERNAME IS EMPTY");
        boolean validPassword = !password.isEmpty();
        
        if(validPassword){
            //Pasword confirmation
            String regexNum = ".*\\d.*";
            String regexYUppercase = ".*[A-Z].*";
            String regexSpecial = ".*[!@#$%^&*()].*";

            // Comprobar si la contraseña cumple con los requisitos
            boolean haveNumbers = Pattern.matches(regexNum, password);
            boolean haveUppercase = Pattern.matches(regexYUppercase, password);
            boolean haveSpecial = Pattern.matches(regexSpecial, password);

            // Combinar los resultados
            validPassword = haveNumbers && haveUppercase && haveSpecial;
            
            if(validPassword){
                validPassword = !passwordConfirm.isEmpty();
                if(validPassword){
                    validPassword = password.equals(passwordConfirm);
                    if(!validPassword)request.setAttribute("errorRegUsuFail", "PASSWORD AND CONFIRMATION PASSWORD DON'T mATCH");
                }
                else request.setAttribute("errorRegUsuFail", "CONFIRMATION PASSWORD IS EMPTY");
            }
            else request.setAttribute("errorRegUsuFail", "PASSWORD NEED A SPECIAL CHARACTER, AN UPPPERCASE LETTER AND A NUMBER");
        }
        else request.setAttribute("errorRegUsuFail","PASSWORD FIELD IS EMPTY" );
        
        

        if (validName && validSurname && validEmail && validUsername && validPassword){
            
            User user = new User();
            boolean existsUser = user.existsUser(username);
            if(existsUser) {
                request.setAttribute("errorRegUsuFail", "ALREADY EXISTING USER");
                request.getRequestDispatcher("/registro.jsp").forward(request, response);
            } else {
                user = new User(name, username, email, userName, password);
                boolean created = user.addUser();
                if(created)request.setAttribute("userRegistered", "USER REGISTERED");
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
