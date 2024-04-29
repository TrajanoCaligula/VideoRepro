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
import  isdcm.model.*;
import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.SecureRandom;

import java.util.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author alumne
 */
@WebServlet(name = "ServRegVid", urlPatterns = {"/ServRegVid"})
public class ServRegVid extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ServRegVid.class.getName());
    
    public static boolean inRange(String field, int value) {
        return (field.length() <= value);
    }
    public String processVideoFile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception{
        Part videoFilePart = request.getPart("videoFile");
        String uploadLocation = getServletContext().getInitParameter("upload.location");
        int id = new Video().getLastIndex()+1;
        String newFileName = "Id_"+String.valueOf(id)+"_Usr_"+request.getSession().getAttribute("USERNAME") +"_VID_"+videoFilePart.getSubmittedFileName();
        
        try(InputStream input = videoFilePart.getInputStream()){
            Path targetPath = Paths.get(uploadLocation,newFileName);
            System.out.println("File saved to:"+targetPath);
            Files.copy(input, targetPath,StandardCopyOption.REPLACE_EXISTING);
            
        }catch(IOException e){
            throw e;
        }
        
        try{ 
            File videoFile = new File(uploadLocation+ File.separator + newFileName);
            SecretKey secretKey = encryptVideoFile(videoFile);
            saveSecretKey(uploadLocation, secretKey, newFileName);
        }
        catch(Exception e){
            System.out.println(e);
        }
       
        return newFileName;
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response, String fileName)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String creationDateBeforeParse = request.getParameter("creationDate");
            String durationBeforeParse = request.getParameter("duration");
            String description = request.getParameter("description");
            String format = "uknown";
            String userName = (String)request.getSession().getAttribute("USERNAME");
            String nameFile = request.getPart("videoFile").getSubmittedFileName();
            int dotIndex = nameFile.lastIndexOf('.');
            if (dotIndex > 0 && dotIndex < nameFile.length() - 1) {
                format = nameFile.substring(dotIndex + 1);
            }
            
            
            java.util.Date utildate = new Date(Calendar.getInstance().getTimeInMillis());
            try {
                utildate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(creationDateBeforeParse);
            } catch (ParseException ex) {
                logger.log(Level.SEVERE, "Error Parse Fecha", ex);
            }
            Date creationDate = new Date(utildate.getTime());

            try {
                utildate = new SimpleDateFormat("hh:mm:ss", Locale.ENGLISH).parse(durationBeforeParse);
            } catch (ParseException ex) {
                 logger.log(Level.SEVERE, "Error Parse Duración", ex);
            }
            
            try {
                utildate = new SimpleDateFormat("hh:mm:ss", Locale.ENGLISH).parse(durationBeforeParse);
            } catch (ParseException ex) {
                 logger.log(Level.SEVERE, "Error Parse Duración", ex);
            }
            Time duration = new Time(utildate.getTime());

            boolean validTitle= !title.isEmpty();
            if(!validTitle)request.setAttribute("errorRegVidFail", "TITLE IS EMPTY");
            validTitle = validTitle && inRange(title,100);
            if(!validTitle) request.setAttribute("errorRegVidFail", "TITLE IS TOO LONG");
        
            boolean validAuthor = !author.isEmpty();
            if(!validAuthor)request.setAttribute("errorRegVidFail", "AUTHOR IS EMPTY");
            validAuthor = validAuthor && inRange(author,100);
            if(!validAuthor) request.setAttribute("errorRegVidFail", "AUTHOR IS TOO LONG");
            
            boolean validDescription = !description.isEmpty();
            if(!validDescription)request.setAttribute("errorRegVidFail", "DESCRIPTION IS EMPTY");
            validDescription = validDescription && inRange(description,255);
            if(!validDescription) request.setAttribute("errorRegVidFail", "DESCRIPTION IS TOO LONG");
            
            boolean validFormat = !format.isEmpty();
            if(!validFormat)request.setAttribute("errorRegVidFail", "FORMAT IS EMPTY");
            
            Video video = new Video(title, author, creationDate, duration, 0, description, format, userName,fileName);

            if(video.addVideo()){
                request.setAttribute("Success", "Video Added Successfully");
                request.getRequestDispatcher("/listadoVid.jsp").forward(request, response);
            }
            request.setAttribute("Error", "SOMETHING HAS GONE WRONG ADDING THE VIDEO");
            request.getRequestDispatcher("/registroVid.jsp").forward(request, response);
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

        if(request.getSession().getAttribute("USER_LOGGED") != null && !request.getSession().getAttribute("USER_LOGGED").toString().equals("false")){
            String fileName;
            try {
                fileName = processVideoFile(request, response);
                String urlVideo = "http://localhost:8080/VideoRepro/uploads/"+ fileName;
                processRequest(request, response, urlVideo);
            } catch (Exception ex) {
                Logger.getLogger(ServRegVid.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("ERROR", "ERROR SAVING THE VIDEO");
                request.getRequestDispatcher("/listadoBusqueda.jsp").forward(request, response);
            }
            
        } else{
            request.setAttribute("errorRegVidFail", "MUST LOG IN TO REGISTER A VIDEO");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }   
            
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
    
   

    private SecretKey encryptVideoFile(File videoFile) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        FileInputStream fis = new FileInputStream(videoFile);
        File encryptedFile = new File(videoFile.getParent(), "encrypted_" + videoFile.getName());
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

    private void saveSecretKey(String uploadLocation, SecretKey secretKey, String fileName) throws IOException {
        File keyFile = new File(uploadLocation + File.separator+fileName + ".key");
        try (FileOutputStream keyFos = new FileOutputStream(keyFile)) {
            keyFos.write(secretKey.getEncoded());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
