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
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author jaume
 */
@WebServlet(name = "ServREST", urlPatterns = {"/ServREST"})
public class ServREST extends HttpServlet {

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
            
            String addressAPI = "http://localhost:8080/REST/resources/jakartaee9";  
            
            // Create a URLConnection
            URLConnection connection = new URL(addressAPI).openConnection();

            // Cast to HttpURLConnection (assuming it's a HTTP connection)
            HttpURLConnection httpConnection = (HttpURLConnection) connection;

            // Set the request method
            httpConnection.setRequestMethod("PUT");
            // Send the request and get the response code
            int responseCode = httpConnection.getResponseCode();
            
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Request successful, proceed to get the response body
                // Get the response body as an InputStream
                InputStream inputStream = httpConnection.getInputStream();

                // Read the response body as a String (assuming it's text-based)
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder responseBody = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                  responseBody.append(line);
                }

                // Close the streams
                reader.close();
                inputStream.close();

                // Process the response body (parse JSON, XML, etc.)
                System.out.println("Response body: " + responseBody.toString());

            } else {
                // Handle error based on response code (e.g., throw an exception)
                throw new RuntimeException("Error accessing API: " + responseCode);
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
