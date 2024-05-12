/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package isdcm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import jakarta.ws.rs.core.Request;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.naming.spi.DirStateFactory.Result;

/**
 *
 * @author Alvaro
 */
@WebServlet(name = "ServEncrypt", urlPatterns = {"/ServEncrypt"})
public class ServSun extends HttpServlet {

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
        /*try {
            Part requestXACML = request.getPart("requestXACML");
            Part policyXACML = request.getPart("policyXACML");

            if (requestXACML == null || policyXACML == null ||  isXML(policyXACML.getSubmittedFileName())) {
                request.setAttribute("Error", "The file is not an XML");
                request.getRequestDispatcher("/sunXACML.jsp").forward(request, response);
            }else{
                            // Evaluar la petición XACML usando la política XACML
            Result result = evaluateXACMLRequest(requestXACML, policyXACML);

            // Generar el fichero XACMLContextResponse.xml
            String responseXML = generateXACMLContextResponse(result);

            // Enviar el fichero XACMLContextResponse.xml como respuesta HTTP
            response.setContentType("application/xml");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(responseXML);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
    private boolean isXML(String fileName) {
        // Verificar si el nombre del archivo termina con la extensión ".xml"
        return fileName != null && fileName.toLowerCase().endsWith(".xml");
    }
    private String generateXACMLContextResponse(Result result) throws Exception {
    /*    Decision decision = result.getDecision();
        String decisionString = decision.toString(); // Ej: Permit, Deny, Indeterminate
    
        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        responseBuilder.append("<ResponseContext xmlns=\"urn:oasis:names:tc:xacml:3.0:context:xacml-context-schema.xsd\">");
        responseBuilder.append("  <RequestID>...</RequestID>"); // Insertar ID de la petición
        responseBuilder.append("  <Result>");
        responseBuilder.append("    <Decision>");
        responseBuilder.append("      <DecisionType>" + decisionString + "</DecisionType>");
        responseBuilder.append("    </Decision>");
        responseBuilder.append("  </Result>");
        responseBuilder.append("</ResponseContext>");

        return responseBuilder.toString();*/
        return "";
      }
    /*private Result evaluateXACMLRequest(Part requestXACML, Part policyXACML) throws Exception {
        
        PDP pdp = PDPFactory.newInstance().getPDP(new File(configPath + "config_rbac.xml"));
        CTX ctx = (CTX) STAXUtil.unmarshal(new File(requestPath + requestXACML.getSubmittedFileName()));
        Request request = ctx.getRootObject();

        // Evalúa la petición XACML usando el motor de evaluación XACML
        Result result = pdp.evaluate(request);
 
        return result;
    }*/


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
        response.setContentType("text/html;charset=UTF-8");

        if(request.getSession().getAttribute("USER_LOGGED") != null && !request.getSession().getAttribute("USER_LOGGED").toString().equals("false"))processRequest(request, response);
        else{
            request.setAttribute("Error", "MUST LOG IN TO ACCES HERE");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}
