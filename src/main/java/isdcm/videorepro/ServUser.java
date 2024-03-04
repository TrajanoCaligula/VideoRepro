/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author alumne
 */
@WebServlet(name = "ServUser", urlPatterns = {"/ServUser"})

public class ServUser extends HttpServlet {

    public static String attributeErrorInvalidName = "ERROR_INVALID_NAME";         
    public static String attributeErrorInvalidEmail = "ERROR_INVALID_EMAIL";      
    public static String attributeErrorInvalidPassword = "ERROR_INVALID_PASSWORD";    
    public static String attributeUserExists = "ERROR_USER_AlREADY_EXISTS";
    public static String attributeUserRegisteredOK = "REGISTERED_OK";


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
        try (PrintWriter out = response.getWriter()) {
        
        // Read form
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        boolean nameOK = name.isEmpty();
        boolean emailOK = email.isEmpty();
        boolean passwordOK = password.isEmpty();
        // Validate not empty

        request.setAttribute(attributeErrorInvalidName, name.isEmpty());
        request.setAttribute(attributeErrorInvalidEmail, email.isEmpty());
        request.setAttribute(attributeErrorInvalidPassword, password.isEmpty());

        //Validate correctness?

        if (nameOK && emailOK && passwordOK) {
            User user = new User(name, email, password); 

            if(user.existsUser()){

                request.setAttribute(attributeUserExists, true);
                request.getRequestDispatcher("/RegistroUsu.jsp").forward(request, response);
                return;

            } else{
                //DB?
                System.out.println("Usuario registrado OK");
                boolean registered = user.createUser();
                request.getRequestDispatcher("/RegistroUsu.jsp").forward(request, response);
                return;
            }
        }

        else{
            request.setAttribute("error", "Fill all feilds");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        response.sendRedirect("/login.jsp");

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet NewServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NewServlet at " + request.getContextPath() + "</h1>");
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