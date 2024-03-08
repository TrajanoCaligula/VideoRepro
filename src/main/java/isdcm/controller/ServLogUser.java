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



//HttpSession session = request.getSession();
//session.invalidate();

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

        String userName = request.getParameter("USERNAME");
        String password = request.getParameter("PASSWORD");

        request.setAttribute("errorUserNameInvalid", "");
        //Attributes validation
        boolean validUsername = !userName.isEmpty();
        if(!validUsername)request.setAttribute("errorUserNameInvalid", "USERNAME IS EMPTY");
        boolean validPassword = !password.isEmpty();
        if(!validPassword)request.setAttribute("errorUserNameInvalid", "PASSWORD IS EMPTY");

        if (validUsername && validPassword) {
            User user = new User(userName, password);
            request.getSession().setAttribute("USER_LOGGED", false);
            request.getSession().setAttribute("attributeUserExists", false); //TODO:REVISAAARRR
            request.getSession().setAttribute("NAME", "");
            request.getSession().setAttribute("USERID", -1);
            request.getSession().setAttribute("SURNAME", "");
            request.getSession().setAttribute("EMAIL", "");
            request.getSession().setAttribute("USERNAME", userName);

            if (user.existsUser()) {
                user = user.getUser();
                if(user == null) request.setAttribute("DB ERROR", true);
                else {
                    if(user.getPassword().equals(password)){
                        request.getSession().setAttribute("NAME", user.getName());
                        request.getSession().setAttribute("SURNAME", user.getSurname()); 
                        request.getSession().setAttribute("EMAIL", user.getEmail());
                        request.getSession().setAttribute("USERNAME", user.getUserName());
                        request.getSession().setAttribute("USER_LOGGED", true);
                        request.getSession().setAttribute("USERID", user.getId());
                        request.getRequestDispatcher("/listadoVid.jsp").forward(request, response);
                    }
                    else request.setAttribute("errorUserNameInvalid", "PASSWORD OR USERNAME INCORRECT");
                }
            } else {
                request.setAttribute("errorUserNameInvalid", "El usuario no existe");
            }

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
        if(request.getSession(false) != null) request.getRequestDispatcher("/listadoVid.jsp").forward(request, response);
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