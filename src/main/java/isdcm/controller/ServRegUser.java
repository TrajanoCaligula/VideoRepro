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
import java.util.regex.*;
import isdcm.model.User;
import jakarta.servlet.annotation.WebServlet;

/**
 *
 * @author alumne
 */
@WebServlet(name = "ServRegUser", urlPatterns = {"/ServRegUser"})
public class ServRegUser extends HttpServlet {

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
    
    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        // Regular expression for a basic email format
        String regex = "^[\\w!#$%&'*+/=?^`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^`{|}~-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        return email.matches(regex);
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

        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String passwordConfirm = request.getParameter("passwordConfirm");

        //Attributes validation
        boolean validName = !name.isEmpty();
        if(!validName)request.setAttribute("errorRegUsuFail","NAME FIELD IS EMPTY");
        validName = validName && isAlphanumeric(name)&& inRange(name,100);
        if(!validName) request.setAttribute("errorRegUsuFail", "INVALID NAME");
        
        boolean validSurname = !surname.isEmpty();
        if(!validSurname)request.setAttribute("errorRegUsuFail","SURNAME FIELD IS EMPTY");
        validSurname = validSurname && isAlphanumeric(surname)&& inRange(surname,100);
        if(!validSurname) request.setAttribute("errorRegUsuFail", "INVALID SURNAME");
        
        boolean validEmail = !email.isEmpty();
        if(!validEmail)request.setAttribute("errorRegUsuFail","EMAIL FIELD IS EMPTY");
        validEmail = validEmail && isValidEmail(email)&& inRange(email,255);
        if(!validEmail) request.setAttribute("errorRegUsuFail", "INVALID EMAIL");
        
        boolean validUsername = !userName.isEmpty();
        if(!validUsername)request.setAttribute("errorRegUsuFail", "USERNAME FIELD IS EMPTY");
        validUsername = validUsername && isAlphanumeric(userName)&& inRange(userName,100);
        if(!validUsername) request.setAttribute("errorRegUsuFail", "INVALID USERNAME");
        
        boolean validPassword = !password.isEmpty()&& inRange(email,255);
        if(!validPassword)request.setAttribute("errorRegUsuFail", "PASSWORD FIELD IS EMPTY");
        boolean validPassword2 = !passwordConfirm.isEmpty()&& inRange(email,255);
        if(!validPassword2)request.setAttribute("errorRegUsuFail", "CONFIRMATION PASSWORD FIELD IS EMPTY");
        
        
        if(validName && validSurname && validEmail && validUsername){
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
            
            if(validPassword && validPassword2){
                validPassword = password.equals(passwordConfirm);
                if(!validPassword)request.setAttribute("errorRegUsuFail", "PASSWORD AND CONFIRMATION PASSWORD DON'T MATCH");
            }
            else request.setAttribute("errorRegUsuFail", "PASSWORD NEED A SPECIAL CHARACTER !@#$%^&*(), AN UPPPERCASE LETTER AND A NUMBER");
        }
        
        

        if (validName && validSurname && validEmail && validUsername && validPassword){
            
            User user = new User();
            boolean existsUser = user.existsUser(userName);
            if(existsUser) {
                request.setAttribute("errorRegUsuFail", "ALREADY EXISTING USER");
                request.getRequestDispatcher("/registroUsu.jsp").forward(request, response);
            } else {
                user = new User(name, surname, email, userName, password);
                boolean created = user.addUser();
                if(created){
                    request.setAttribute("Success", "USER REGISTERED");
                    request.getRequestDispatcher("/login.jsp").forward(request, response);
                }else{
                    request.setAttribute("Error", "THE EMAIL IS THE SAME THAN ANOTHER USER");
                    request.getRequestDispatcher("/registroUsu.jsp").forward(request, response);
                }
            }
        }
        request.getRequestDispatcher("/registroUsu.jsp").forward(request, response);

    
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
        request.setAttribute("Error", "This URL is not operational");
        if(request.getSession().getAttribute("USER_LOGGED") != null && !request.getSession().getAttribute("USER_LOGGED").toString().equals("false"))request.getRequestDispatcher("/listadoVid.jsp").forward(request, response);
        else request.getRequestDispatcher("/login.jsp").forward(request, response);
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
        if(request.getSession().getAttribute("USER_LOGGED") != null && request.getSession().getAttribute("USER_LOGGED").toString().equals("false")){
           request.setAttribute("Error", "MUST NOT LOG IN TO ACCES HERE");
            request.getRequestDispatcher("/listadoVid.jsp").forward(request, response);
        }
        else processRequest(request, response);
    }

}
