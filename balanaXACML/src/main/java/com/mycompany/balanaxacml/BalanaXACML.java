/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.balanaxacml;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.wso2.balana.Balana;
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
public class BalanaXACML {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Esta aplicación utiliza la configuracion del directorio uploadsBalanaXACML.\n Introduce la dirección de la 'policy' que desea utilizar:\n");
        String policyPath = scanner.nextLine();
        System.out.print("Ahora introduzaca el directorio de la 'request' que desea evaluar:\n");
        String requestPath = scanner.nextLine();
        scanner.close();
        evaluateRequest(policyPath,requestPath);  
    }
    
    private static Balana balana;
    private static final String uploadXACMLLocation = "D:\\GIT\\VideoRepro\\uploadsBalanaXACML";
    
    private static void initializeBalana(String policy, String config) {       
        try{       
            System.setProperty(ConfigurationStore.PDP_CONFIG_PROPERTY, config);
            System.setProperty(FileBasedPolicyFinderModule.POLICY_DIR_PROPERTY, policy);
            
        } catch (Exception e) {
            System.err.println("Can not locate policy repository");
        }
        balana = Balana.getInstance();

    }
    
    private static PDP initializePDP(){  
        PDPConfig pdpConfig = balana.getPdpConfig();
        return new PDP(new PDPConfig(pdpConfig.getAttributeFinder(), pdpConfig.getPolicyFinder(), null, true));
    }
    
    private static void evaluateRequest(String policyPath, String requestPath) {            
        try{            
            String pathconfig = uploadXACMLLocation + File.separator + "Config" + File.separator +  "config_rbac.xml";

            initializeBalana(policyPath, pathconfig);
            PDP pdp = initializePDP();  
            File requestFile = new File(requestPath);

            long startTime = System.currentTimeMillis();

            String responsePDP = pdp.evaluate(fileContentToString(requestFile));

            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;

            saveResponse(responsePDP);
            
            System.out.print("Success, The authorization has been executed correctly. The executionm time for teh evaluation was: "+elapsedTime+" ms");

        }catch(Exception e){
            System.err.printf(e.toString());
        }
    }
    private static void saveResponse(String responsePDP){
        String pathToSave = uploadXACMLLocation+ File.separator + "Responses" + File.separator + "XACMLContextResponse.xml";
        try{
            ResponseCtx responseCtx = ResponseCtx.getInstance(getResponseData(responsePDP));
            OutputStream outputStream = new FileOutputStream(pathToSave);
            printResult(outputStream, new Indenter(), responseCtx.getResults());
        }catch(Exception e){
            System.out.print("Something has gone wrong trying to save the response in a file");
            System.err.printf(e.toString());
        }
    }
    
    public static Element getResponseData(String response) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(response.getBytes());
        DocumentBuilderFactory docbuild = DocumentBuilderFactory.newInstance();
        docbuild.setNamespaceAware(true);
        Document responseDoc;

        try {
            responseDoc = docbuild.newDocumentBuilder().parse(inputStream); 
            inputStream.close();
        } catch (Exception e) {
            System.err.println("Error getting the response");
            return null;
        } 
        return responseDoc.getDocumentElement();
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

    private static String fileContentToString(File file){
        StringBuilder content = new StringBuilder();
        try{
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator()); // Agregar un salto de línea después de cada línea
            }
            bufferedReader.close();
        }catch(Exception e){
            System.err.printf(e.toString());
        }
        return content.toString();
    }

}
