/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package isdcm.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import isdcm.model.*;
import jakarta.servlet.annotation.WebServlet;



//HttpSession session = request.getSession();
//session.invalidate();

/**
 *
 * @author alumne
 */
@WebServlet(name = "ServLogUser", urlPatterns = {"/ServLogUser"})
public class ServLogUser extends HttpServlet {

    
    public static boolean isAlphanumeric(String str) {
        if (str == null || str.isEmpty()) {
          return false; // Empty string is not alphanumeric
        }
        for (char ch : str.toCharArray()) {
          if (!Character.isLetterOrDigit(ch)) {
            return false;
          }
        }
        return true;
    }
    
    public static boolean inRange(String field, int value) {
        return (field.length() <= value);
    }
    
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

        String userName = request.getParameter("USERNAME");
        String password = request.getParameter("PASSWORD");

        request.setAttribute("errorUserNameInvalid", "");
        //Attributes validation
        boolean validUsername = !userName.isEmpty();
        if(!validUsername)request.setAttribute("errorUserNameInvalid", "USERNAME IS EMPTY");
        validUsername = validUsername && isAlphanumeric(userName)&& inRange(userName,100);
        if(!validUsername) request.setAttribute("errorUserNameInvalid", "INVALID CHARACTERS");
        
        boolean validPassword = !password.isEmpty();
        if(!validPassword)request.setAttribute("errorUserNameInvalid", "PASSWORD IS EMPTY");

        if (validUsername && validPassword && inRange(password,255)) {
            User user = new User();
            request.getSession().setAttribute("USER_LOGGED", "false");
            request.getSession().setAttribute("attributeUserExists", false);
            request.getSession().setAttribute("NAME", "");
            request.getSession().setAttribute("USERID", -1);
            request.getSession().setAttribute("SURNAME", "");
            request.getSession().setAttribute("EMAIL", "");
            request.getSession().setAttribute("USERNAME", userName);

            if (user.existsUser(userName)) {
                user = user.getUser(userName);
                if(user == null) request.setAttribute("errorUserNameInvalid", "DB ERROR");
                else {
                    if(user.passwordIsCorrect(userName, password)){
                        request.getSession().setAttribute("NAME", user.getName());
                        request.getSession().setAttribute("SURNAME", user.getSurname()); 
                        request.getSession().setAttribute("EMAIL", user.getEmail());
                        request.getSession().setAttribute("USERNAME", user.getUserName());
                        request.getSession().setAttribute("USER_LOGGED", "true");
                        request.getSession().setAttribute("USERID", user.getId());
                        request.getRequestDispatcher("/listadoVid.jsp").forward(request, response);
                    }
                    else request.setAttribute("errorUserNameInvalid", "USERNAME OR PASSWORD INCORRECT");
                }
            } else {
                request.setAttribute("errorUserNameInvalid", "USERNAME OR PASSWORD INCORRECT");
            }
        }
        request.getRequestDispatcher("/login.jsp").forward(request, response);

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
        request.getSession().invalidate();
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
