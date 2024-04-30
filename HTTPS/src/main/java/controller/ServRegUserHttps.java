package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import model.User;

/**
 *
 * @author alumne
 */
@WebServlet(name = "ServRegUserHttps", urlPatterns = {"/ServRegUserHttps"})
public class ServRegUserHttps extends HttpServlet {
    
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
        if(!validName)request.setAttribute("Error","NAME FIELD IS EMPTY");
        validName = validName && isAlphanumeric(name)&& inRange(name,100);
        if(!validName) request.setAttribute("Error", "INVALID NAME");
        
        boolean validSurname = !surname.isEmpty();
        if(!validSurname)request.setAttribute("Error","SURNAME FIELD IS EMPTY");
        validSurname = validSurname && isAlphanumeric(surname)&& inRange(surname,100);
        if(!validSurname) request.setAttribute("Error", "INVALID SURNAME");
        
        boolean validEmail = !email.isEmpty();
        if(!validEmail)request.setAttribute("Error","EMAIL FIELD IS EMPTY");
        validEmail = validEmail && isValidEmail(email)&& inRange(email,255);
        if(!validEmail) request.setAttribute("Error", "INVALID EMAIL");
        
        boolean validUsername = !userName.isEmpty();
        if(!validUsername)request.setAttribute("Error", "USERNAME FIELD IS EMPTY");
        validUsername = validUsername && isAlphanumeric(userName)&& inRange(userName,100);
        if(!validUsername) request.setAttribute("Error", "INVALID USERNAME");
        
        boolean validPassword = !password.isEmpty()&& inRange(email,255);
        if(!validPassword)request.setAttribute("Error", "PASSWORD FIELD IS EMPTY");
        boolean validPassword2 = !passwordConfirm.isEmpty()&& inRange(email,255);
        if(!validPassword2)request.setAttribute("Error", "CONFIRMATION PASSWORD FIELD IS EMPTY");
        
        
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
                if(!validPassword)request.setAttribute("Error", "PASSWORD AND CONFIRMATION PASSWORD DON'T MATCH");
            }
            else request.setAttribute("Error", "PASSWORD NEED A SPECIAL CHARACTER !@#$%^&*(), AN UPPPERCASE LETTER AND A NUMBER");
        }
        
        

        if (validName && validSurname && validEmail && validUsername && validPassword){
            
            User user = new User();
            boolean existsUser = user.existsUser(userName);
            if(existsUser) {
                request.setAttribute("Error", "ALREADY EXISTING USER");
            } else {
                user = new User(name, surname, email, userName, password);
                boolean created = user.addUser();
                if(created){
                    request.setAttribute("Success", "USER REGISTERED");
                }else{
                    request.setAttribute("Error", "THE EMAIL IS THE SAME THAN ANOTHER USER");
                }
            }
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("testTomcat.jsp");
        dispatcher.forward(request, response);    
    }
    
    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void deGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    response.getWriter().write("hello!");
    }
}