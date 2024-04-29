package isdcm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import isdcm.DTO.VideoDTO;
import isdcm.model.Video;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.SecretKeySpec;

@WebServlet(name = "ServWachVid", urlPatterns = {"/ServWachVid"})
public class ServWatchVid extends HttpServlet {

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
            String idString = request.getParameter("id");
            int id = -1;
            try {
                id = Integer.parseInt(idString);
                // Operaciones con el número entero convertido
            } catch (NumberFormatException e) {
                // Manejo de la excepción si el String no es un número entero válido
                System.err.println("El Identificador del video no es un número entero válido.");
            }
            Video vid = new Video().getVideo(id);
            VideoDTO videoData = new VideoDTO(vid.getId(),
                         vid.getTitle(),
                         vid.getAuthor(),
                         vid.getCreationDate(),
                         vid.getDuration(),
                         vid.getViews(),
                         vid.getDescription(),
                         vid.getFormat(),
                         vid.getUserName(),
                         vid.getUrl());
            
            URL url = new URL(vid.getUrl());
            String fileName = url.getFile(); // Obtiene la parte del archivo de la URL
            String[] parts = fileName.split("/"); // Divide la parte del archivo en partes separadas por "/"
            String finalFileName = parts[parts.length - 1]; // Obtiene el último elemento que es el nombre del archivo
            
            decrypt(finalFileName);
            
            String decryptedURL = "";
            for(int i = 0; i < parts.length;++i){
                if(i == parts.length - 1){
                    decryptedURL  = decryptedURL+"decrypted_"+parts[i];
                }
                else decryptedURL  = decryptedURL+parts[i]+"/";
            }
            videoData.setURL(decryptedURL);
            String json = new ObjectMapper().writeValueAsString(videoData);
           
            if(null != videoData && -1 != vid.getId()){
                response.setContentType("application/json");
                response.getWriter().write(json);
            } else {
                request.setAttribute("errorEmptyVideo", "No existe este video en nuestra pagina");
            }  
    }

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

        if(request.getSession().getAttribute("USER_LOGGED") != null && !request.getSession().getAttribute("USER_LOGGED").toString().equals("false"))processRequest(request, response);
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
        request.setAttribute("Error", "This URL is not operational");
        if(request.getSession().getAttribute("USER_LOGGED") != null && !request.getSession().getAttribute("USER_LOGGED").toString().equals("false"))request.getRequestDispatcher("/listadoVid.jsp").forward(request, response);
        else request.getRequestDispatcher("/login.jsp").forward(request, response);
        
    }
    protected void decrypt(String fileName) {
        try {
            String encryptedFileName = "/encrypted_"+ fileName;
            System.out.println("encryptedFileName:"+encryptedFileName);
            String uploadLocation = getServletContext().getInitParameter("upload.location");
            File encryptedFile = new File(uploadLocation + encryptedFileName);

            byte[] keyBytes = retrieveKeyBytes(uploadLocation,fileName);
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] retrieveKeyBytes(String uploadLocation, String fileName) throws IOException {
        File keyFile = new File(uploadLocation + File.separator+fileName+".key");
        try (FileInputStream keyFis = new FileInputStream(keyFile)) {
            byte[] keyBytes = new byte[keyFis.available()];
            keyFis.read(keyBytes);
            return keyBytes;
        }
    }

}
