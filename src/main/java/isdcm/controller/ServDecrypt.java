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
import javax.crypto.CipherInputStream;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Alvaro
 */
@WebServlet(name = "ServDecrypt", urlPatterns = {"/ServDecrypt"})
public class ServDecrypt extends HttpServlet {

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
         try {
            Part filePart = request.getPart("xmlEncryptedFile");
            String fileName = filePart.getSubmittedFileName();
            String uploadLocation = getServletContext().getInitParameter("upload.location")+"XML";
            File encryptedFile = new File( uploadLocation + File.separator + fileName);
                               
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

                byte[] keyBytes = retrieveKeyBytes(uploadLocation, fileName);
                SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");

                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.DECRYPT_MODE, secretKey);

                FileInputStream fis = new FileInputStream(encryptedFile);
                CipherInputStream cis = new CipherInputStream(fis, cipher);
                File decryptedFile = new File(encryptedFile.getParent(), "decrypted_" + fileName);
                FileOutputStream fos = new FileOutputStream(decryptedFile);

                byte[] bytes = new byte[1024];
                int numBytes;
                while ((numBytes = cis.read(bytes)) != -1) {
                    fos.write(bytes, 0, numBytes);
                }

                fos.flush();
                fos.close();
                cis.close();
                fis.close();

                request.setAttribute("Success", "Video decrypted successfully, you can find it in /uploadsXML as: "+ "decrypted_" + fileName +"!");
                request.getRequestDispatcher("/encryptorXML.jsp").forward(request, response);
            }else{
                request.setAttribute("Error", "The file is not an XML");
                request.getRequestDispatcher("/encryptorXML.jsp").forward(request, response);
            }
        }catch(IOException e){
            response.getWriter().write("We couldn't find the key for that file in our system!\nThat's because file hasn't been encrypted by us, you have modified the name or something has happend to the key");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean isXML(String fileName) {
        // Verificar si el nombre del archivo termina con la extensión ".xml"
        return fileName != null && fileName.toLowerCase().endsWith(".xml");
    }
    
    private byte[] retrieveKeyBytes(String uploadLocation, String fileName) throws IOException {
        int charactersEncrypt = 10;
        String result = fileName.substring(charactersEncrypt);
        File keyFile = new File(uploadLocation+ File.separator +result+".key");
        try (FileInputStream keyFis = new FileInputStream(keyFile)) {
            byte[] keyBytes = new byte[keyFis.available()];
            keyFis.read(keyBytes);
            return keyBytes;
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
        if(request.getSession().getAttribute("USER_LOGGED") != null && !request.getSession().getAttribute("USER_LOGGED").toString().equals("false"))processRequest(request, response);
        else{
            request.setAttribute("Error", "MUST LOG IN TO ACCES HERE");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }


}
