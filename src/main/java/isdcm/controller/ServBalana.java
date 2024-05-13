/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package isdcm.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;
import java.util.Set;
import javax.xml.parsers.DocumentBuilderFactory;
import org.wso2.balana.Balana;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.wso2.balana.ConfigurationStore;
import org.wso2.balana.Indenter;
import org.wso2.balana.PDP;
import org.wso2.balana.PDPConfig;
import org.wso2.balana.ctx.ResponseCtx;
import org.wso2.balana.ctx.xacml3.Result;
import org.wso2.balana.finder.impl.FileBasedPolicyFinderModule;

/**
 *
 * @author Alvaro
 */
@WebServlet(name = "ServBalana", urlPatterns = {"/ServBalana"})
public class ServBalana extends HttpServlet {

    private Balana balana;
    private PDPConfig pdpConfigOG;
    private void setPolicy(String policy, String config) {       
        try{       
            System.setProperty(ConfigurationStore.PDP_CONFIG_PROPERTY, config);

            System.setProperty(FileBasedPolicyFinderModule.POLICY_DIR_PROPERTY, policy);
            balana = Balana.getInstance();
        } catch (Exception e) {
            System.err.println("Can not locate policy repository");
        }
    }
    
    private PDP initializePDP(){  
        PDPConfig pdpConfig = balana.getPdpConfig();
        return new PDP(new PDPConfig(pdpConfig.getAttributeFinder(), pdpConfig.getPolicyFinder(), null, true));
    }

    private void savefilesXACML(String path, Part policy, Part request){

        try(InputStream input = policy.getInputStream()){
            Path targetPath = Paths.get(path,policy.getSubmittedFileName());
            System.out.println("File saved to:"+targetPath);
            Files.copy(input, targetPath,StandardCopyOption.REPLACE_EXISTING);

        }catch(IOException e){
            System.out.println("Error saving the policy");
        }

        try(InputStream input = policy.getInputStream()){
            Path targetPath = Paths.get(path,request.getSubmittedFileName());
            System.out.println("File saved to:"+targetPath);
            Files.copy(input, targetPath,StandardCopyOption.REPLACE_EXISTING);

        }catch(IOException e){
            System.out.println("Error saving the request");
        }
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
        Part policyXACML = request.getPart("policyXACML");
        Part requestXACML = request.getPart("requestXACML");
        if(isXML(policyXACML.getSubmittedFileName()) && isXML(requestXACML.getSubmittedFileName())){
        try {

            String uploadXACMLLocation = getServletContext().getInitParameter("uploadXACML.location");

            savefilesXACML(uploadXACMLLocation, policyXACML, requestXACML );
            
            String pathPolicy = uploadXACMLLocation + File.separator + policyXACML.getSubmittedFileName();
            String pathRequest = uploadXACMLLocation + File.separator + requestXACML.getSubmittedFileName();
            String pathToSave = uploadXACMLLocation+ File.separator + "XACMLContextResponse.xml";
            String pathconfig = uploadXACMLLocation + File.separator + "config_rbac.xml";
            System.setProperty(FileBasedPolicyFinderModule.POLICY_DIR_PROPERTY, pathPolicy);
                
            setPolicy(pathPolicy, pathconfig);
            PDP pdp = initializePDP();        
            long startTime = System.currentTimeMillis();
            
            String responsePDP = pdp.evaluate(requestXACML.getInputStream()).toString();

            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            ResponseCtx responseCtx = ResponseCtx.getInstance(getXacmlResponse(responsePDP));

            // Guardamos en XML el resultado
            OutputStream outputStream = new FileOutputStream(pathToSave);
            printResult(outputStream, new Indenter(), responseCtx.getResults());
            request.setAttribute("Success", "The authorization has been executed correctly. The executionm time for teh evaluation was: "+elapsedTime+" ms");
            request.getRequestDispatcher("/balanaXACML.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
        else{
            request.setAttribute("Error", "ONE OF THE FILES IS NOT A XML FILE");
            request.getRequestDispatcher("/balanaXACML.jsp").forward(request, response);
        }
    }
    public boolean isXML(String fileName) {
        // Verificar si el nombre del archivo termina con la extensión ".xml"
        return fileName != null && fileName.toLowerCase().endsWith(".xml");
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
    
    /* public static String parseFromXMLToString(String path) throws Exception {
        DocumentBuilderFactory builder = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = builder.newDocumentBuilder();
        Document document = docBuilder.parse(path);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(document), new StreamResult(writer));
        
        return writer.getBuffer().toString().replaceAll("\n|\r", "");
    }*/
     
      public static Element getXacmlResponse(String response) {
        ByteArrayInputStream inputStream;
        DocumentBuilderFactory dbf;
        Document doc;
        inputStream = new ByteArrayInputStream(response.getBytes());
        dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        try {
            doc = dbf.newDocumentBuilder().parse(inputStream); 
            inputStream.close();
        } catch (Exception e) {
            System.err.println("Error geting the response");
            return null;
        } 
        return doc.getDocumentElement();
    }
      
      public static void printResult(OutputStream output, Indenter indenter, Set results) {
        PrintStream out = new PrintStream(output);
        String indent = indenter.makeString();
        out.println(indent + "<Response>");
        Iterator it = results.iterator();
        indenter.in();
        while (it.hasNext()) {
            Result result = (Result)(it.next());
            out.append(result.encode());
        }
        indenter.out();
        out.println(indent + "</Response>");
    }
}
