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
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Set;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.wso2.balana.Balana;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.wso2.balana.Indenter;
import org.wso2.balana.PDP;
import org.wso2.balana.PDPConfig;
import org.wso2.balana.ParsingException;
import org.wso2.balana.ctx.ResponseCtx;
import org.wso2.balana.ctx.xacml3.Result;
import org.wso2.balana.finder.AttributeFinder;
import org.wso2.balana.finder.AttributeFinderModule;
import org.wso2.balana.finder.impl.FileBasedPolicyFinderModule;
/**
 *
 * @author Alvaro
 */
@WebServlet(name = "ServEncrypt", urlPatterns = {"/ServEncrypt"})
public class ServBalana extends HttpServlet {

    private static Balana balana;
    private static void initBalana() {

        try{
            // Using file based policy repository. so set the policy location as a system property
            String policyLocation = "D:\\GIT\\VideoRepro\\uploadsXACML\\XACMLPolicy1.xml";
            System.setProperty(FileBasedPolicyFinderModule.POLICY_DIR_PROPERTY, policyLocation);
        } catch (Exception e) {
            System.err.println("Can not locate policy repository");
        }
        // Create default instance of Balana
        balana = Balana.getInstance();
    }
    
        private static PDP initPDP(){
        PDPConfig pdpConfig = balana.getPdpConfig();

        return new PDP(new PDPConfig(pdpConfig.getAttributeFinder(), pdpConfig.getPolicyFinder(), null, true));
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
        try {
            initBalana();
            PDP pdp = initPDP();        
            
            String uploadXACMLLocation = getServletContext().getInitParameter("uploadXACML.location");
            Part policyXACML = request.getPart("policyXACML");
            Part requestXACML = request.getPart("requestXACML");
            
            String responsePDP = pdp.evaluate(parseFromXMLToString(uploadXACMLLocation+File.separator+"XACMLRequest1.xml"));
            ResponseCtx responseCtx = ResponseCtx.getInstance(getXacmlResponse(responsePDP));

            System.out.println(responseCtx.encode());

            // Guardamos en XML el resultado
            String pathToSave = uploadXACMLLocation+File.separator+"XACMLRESPUESTAAARequest1.xml";
            OutputStream outputStream = new FileOutputStream(pathToSave);
            printResult(outputStream, new Indenter(), responseCtx.getResults());
            
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
    
     public static String parseFromXMLToString(String path) throws Exception {
        DocumentBuilderFactory builder = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = builder.newDocumentBuilder();
        Document document = docBuilder.parse(path);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(document), new StreamResult(writer));
        
        return writer.getBuffer().toString().replaceAll("\n|\r", "");
    }
     
      public static Element getXacmlResponse(String response) {
        ByteArrayInputStream inputStream;
        DocumentBuilderFactory dbf;
        Document doc;

        inputStream = new ByteArrayInputStream(response.getBytes());
        dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);

        try {
            doc = dbf.newDocumentBuilder().parse(inputStream);
        } catch (Exception e) {
            System.err.println("DOM of request element can not be created from String");
            return null;
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
               System.err.println("Error in closing input stream of XACML response");
            }
        }
        return doc.getDocumentElement();
    }
      
      public static void printResult(OutputStream output, Indenter indenter, Set results) {
        // Make a PrintStream for a nicer printing interface
        PrintStream out = new PrintStream(output);

        // Prepare the indentation string
        String indent = indenter.makeString();

        // Now write the XML...
        out.println(indent + "<Response>");

        // Go through all results
        Iterator it = results.iterator();
        indenter.in();
        while (it.hasNext()) {
            Result result = (Result)(it.next());
            out.append(result.encode());
        }
        indenter.out();

        // Finish the XML for a response
        out.println(indent + "</Response>");
    }
}
