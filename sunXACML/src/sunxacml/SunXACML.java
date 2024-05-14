/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sunxacml;

import com.sun.xacml.Indenter;
import com.sun.xacml.PDP;
import com.sun.xacml.PDPConfig;
import com.sun.xacml.attr.AttributeValue;
import com.sun.xacml.ctx.Attribute;
import com.sun.xacml.ctx.RequestCtx;
import com.sun.xacml.ctx.ResponseCtx;
import com.sun.xacml.ctx.Result;
import com.sun.xacml.ctx.Subject;
import com.sun.xacml.finder.AttributeFinder;
import com.sun.xacml.finder.PolicyFinder;
import com.sun.xacml.finder.impl.CurrentEnvModule;
import com.sun.xacml.finder.impl.FilePolicyModule;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Alvaro
 */
public class SunXACML {
    private static final String PATH_PROJECT = "D:\\GIT\\VideoRepro";
    private static final String configFilePath = "D:\\GIT\\VideoRepro";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("Esta aplicación utiliza la configuracion del directorio uploadsBalanaXACML y la 'policy'especioficada en este\n");
        System.out.print("Ahora introduzaca el directorio de la 'request' que desea evaluar:\n");
        String requestPath = scanner.nextLine();
        scanner.close();
        PDP pdp = buildPDP();
        RequestCtx request = loadRequestContextFromFile(requestPath);
        long startTime = System.currentTimeMillis();
        
        ResponseCtx response = pdp.evaluate(request);

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
           
        saveResponse(response);            
        
        System.out.print("Success, The authorization has been executed correctly. The executionm time for teh evaluation was: "+elapsedTime+" ms");


    }
    
private static PDP buildPDP() {
    try {
        // Crea un analizador de XML
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
        // Lee el archivo XML de configuración
        Document doc = dBuilder.parse(new File(configFilePath));
        doc.getDocumentElement().normalize();
        
        // Obtiene la lista de rutas de políticas desde el archivo XML
        NodeList policyPaths = doc.getElementsByTagName("string");
        
        // Crea un FilePolicyModule para cargar las políticas
        FilePolicyModule policyModule = new FilePolicyModule();
        
        // Agrega cada ruta de política al FilePolicyModule
        for (int i = 0; i < policyPaths.getLength(); i++) {
            Node node = policyPaths.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                String policyPath = node.getTextContent().trim();
                policyModule.addPolicy(policyPath);
            }
        }
        
        // Configura otros módulos necesarios
        CurrentEnvModule envModule = new CurrentEnvModule();
        PolicyFinder policyFinder = new PolicyFinder();
        Set<FilePolicyModule> policyModules = new HashSet<>();
        policyModules.add(policyModule);
        policyFinder.setModules(policyModules);
        
        AttributeFinder attrFinder = new AttributeFinder();
        attrFinder.setModules(Collections.singletonList(envModule));
        
        // Crea y devuelve el PDP
        PDPConfig pdpConfig = new PDPConfig(attrFinder, policyFinder, null);
        return new PDP(pdpConfig);
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}

    
    private static RequestCtx loadRequestContextFromFile(String requestFilePath) {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(requestFilePath));
            return RequestCtx.getInstance(fileInputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static void saveResponse(ResponseCtx response){
        String pathToSave = PATH_PROJECT+ File.separator + "uploadsSunXACML" +File.separator + "Responses" + File.separator + "XACMLContextResponse.xml";
        try{
            OutputStream outputStream = new FileOutputStream(pathToSave);
            printResult(outputStream, new Indenter(), response.getResults());
        }catch(Exception e){
            System.out.print("Something has gone wrong trying to save the response in a file");
            System.err.printf(e.toString());
        }
    }
    
    public static void printResult(OutputStream output, Indenter indenter, Set results) {
        PrintStream out = new PrintStream(output);
        String indent = indenter.makeString();
        out.println(indent + "<Response>");
        Iterator it = results.iterator();
        indenter.in();
        while (it.hasNext()) {
            Result result = (Result)(it.next());
            result.encode(out, indenter);
        }
        indenter.out();
        out.println(indent + "</Response>");
    }

    
}
