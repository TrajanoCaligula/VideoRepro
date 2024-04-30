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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 *
 * @author Alvaro
 */
@WebServlet(name = "ServEncrypt", urlPatterns = {"/ServEncrypt"})
public class ServEncrypt extends HttpServlet {

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
        try {
            Part filePart = request.getPart("xmlFile");
            String fileName = filePart.getSubmittedFileName();
            String uploadLocation = getServletContext().getInitParameter("upload.location")+"XML";
                    
            if(isXML(fileName)){
                // Crear una ruta completa para el archivo
                String filePath = uploadLocation + File.separator + fileName;

                // Escribir el contenido del archivo en la ruta especificada
                try (InputStream inputStream = filePart.getInputStream();
                     OutputStream outputStream = new FileOutputStream(filePath)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    System.out.println("Archivo guardado en: " + filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                    // Manejo de errores
                }


                File xmlFile = new File(uploadLocation + File.separator + fileName);
                SecretKey secretKey = encryptXmlFile(xmlFile);
                saveSecretKey(uploadLocation,secretKey, fileName);

                String path =xmlFile.getAbsolutePath();
                response.getWriter().write("Video encrypted successfully, you can find it in /uploadsXML as: "+ "encrypted_" + fileName +"!");
            }else{
                 request.setAttribute("Error", "The file is not an XML");
                request.getRequestDispatcher("/encryptorXML.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean isXML(String fileName) {
        // Verificar si el nombre del archivo termina con la extensión ".xml"
        return fileName != null && fileName.toLowerCase().endsWith(".xml");
    }

    private SecretKey encryptXmlFile(File xmlFile) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);  // Or 256 for stronger encryption, make sure to have the Unlimited Strength Jurisdiction Policy Files installed if using 256
        SecretKey secretKey = keyGenerator.generateKey();

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        FileInputStream fis = new FileInputStream(xmlFile);
        File encryptedFile = new File(xmlFile.getParent(), "encrypted_" + xmlFile.getName());
        FileOutputStream fos = new FileOutputStream(encryptedFile);
        CipherOutputStream cos = new CipherOutputStream(fos, cipher);

        byte[] bytes = new byte[1024];
        int numBytes;
        while ((numBytes = fis.read(bytes)) != -1) {
            cos.write(bytes, 0, numBytes);
        }

        cos.flush();
        cos.close();
        fis.close();
        fos.close();

        return secretKey;
    }

    private void saveSecretKey(String uploadLocation,SecretKey secretKey, String fileName) throws IOException {
        File keyFile = new File(uploadLocation+ File.separator + fileName + ".key");
        try (FileOutputStream keyFos = new FileOutputStream(keyFile)) {
            keyFos.write(secretKey.getEncoded());
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
