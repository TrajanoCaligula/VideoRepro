/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package isdcm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author jaume
 */
@WebServlet(name = "ServREST", urlPatterns = {"/ServREST"})
public class ServREST extends HttpServlet {


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

        if(request.getSession().getAttribute("USER_LOGGED") != null && !request.getSession().getAttribute("USER_LOGGED").toString().equals("false")){
             // URL del servicio REST que quieres llamar
            String idString = request.getParameter("id");
    
            String url = "http://localhost:8080/REST/resources/jakartaee9/getInfo?videoID="+idString;
            
            // Crear un cliente para hacer la llamada
            Client client = ClientBuilder.newClient();
            
            // Hacer la llamada al servicio REST
            Response restResponse = client.target(url).request().get();
            
            // Obtener el código de estado de la respuesta
            int statusCode = restResponse.getStatus();
            
            // Si la respuesta es exitosa (código 200)
            if (statusCode == 200) {
                // Leer el cuerpo de la respuesta
                String responseBody = restResponse.readEntity(String.class);
                if(!responseBody.isEmpty()){
                    response.setContentType("application/json");
                    response.getWriter().write(responseBody);
                } else {
                    request.setAttribute("errorEmptyListVideos", "There is no video");
                }
            } else {
                // Si la respuesta no es exitosa, manejar el error adecuadamente
                response.getWriter().println("Error al llamar al servicio REST. Código de estado: " + statusCode);
            }
            
            // Cerrar el cliente
            client.close();
        }
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
        if(request.getSession().getAttribute("USER_LOGGED") != null && !request.getSession().getAttribute("USER_LOGGED").toString().equals("false")){
            // Obtener los parámetros del formulario que invoca al servlet
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String day = request.getParameter("day");
            String month = request.getParameter("month");
            String year = request.getParameter("year");
    
            // Obtener más parámetros según sea necesario
            
            // Crear un objeto MultivaluedMap para contener los parámetros del formulario
            MultivaluedMap<String, String> formParams = new MultivaluedHashMap<>();
            formParams.add("title", title);
            formParams.add("author", author);
            formParams.add("day", day);
            formParams.add("month", month);
            formParams.add("year", year);
    
            // Agregar más parámetros según sea necesario
            
            // Crear un cliente para hacer la llamada al servicio REST
            Client client = ClientBuilder.newClient();
            
            // URL del servicio REST que quieres llamar
            String url = "http://localhost:8080/REST/resources/jakartaee9/postSearch";
            
            // Hacer la llamada al servicio REST
            Response restResponse = client.target(url)
                                         .request()
                                         .post(Entity.form(formParams));
            
            // Obtener el código de estado de la respuesta
            int statusCode = restResponse.getStatus();
            
            // Si la respuesta es exitosa (código 200)
            if (statusCode == 200) {
                // Leer el cuerpo de la respuesta
                String responseBody = restResponse.readEntity(String.class);
                if(!responseBody.isEmpty()){
                    response.setContentType("application/json");
                    response.getWriter().write(responseBody);
                } else {
                    request.setAttribute("errorEmptyListVideos", "There are no videos for that search");
                }
            } else {
                // Si la respuesta no es exitosa, manejar el error adecuadamente
                response.getWriter().println("Error al llamar al servicio REST. Código de estado: " + statusCode);
            }
            
            // Cerrar el cliente
            client.close();
        }
        else{            
            request.setAttribute("Error", "MUST LOG IN TO SEARCH A VIDEO"); 
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }

    }
     /**
     * Handles the HTTP <code>PUT</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getSession().getAttribute("USER_LOGGED") != null && !request.getSession().getAttribute("USER_LOGGED").toString().equals("false")){

            String idString = request.getParameter("videoID");
    
            String url = "http://localhost:8080/REST/resources/jakartaee9/updateViews";
            
            // Crear un cliente para hacer la llamada
          Client client = ClientBuilder.newClient();
    
          // Crear los parámetros que deseas enviar en el cuerpo de la solicitud
          Form form = new Form();
          form.param("videoID", idString);
    
          // Hacer la llamada al servicio REST
          Response restResponse = client.target(url)
                                        .request()
                                        .put(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
            
            // Obtener el código de estado de la respuesta
            int statusCode = restResponse.getStatus();
            
            // Si la respuesta es exitosa (código 200)
            if (statusCode == 200) {
                // Leer el cuerpo de la respuesta
                String responseBody = restResponse.readEntity(String.class);
                
                // Hacer algo con el cuerpo de la respuesta, por ejemplo:
                response.getWriter().println("Respuesta del servicio REST: " + responseBody);
            } else {
                // Si la respuesta no es exitosa, manejar el error adecuadamente
                response.getWriter().println("Error al llamar al servicio REST. Código de estado: " + statusCode+"      "+ restResponse.readEntity(String.class));
            }
            
            // Cerrar el cliente
            client.close();
        }
        else{
            request.setAttribute("Error", "MUST LOG IN TO INCREASE THE VIEWS");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }

    }

}
