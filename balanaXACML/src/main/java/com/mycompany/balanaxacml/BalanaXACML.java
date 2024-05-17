/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.balanaxacml;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
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
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import javax.xml.crypto.AlgorithmMethod;
import javax.xml.crypto.KeySelector;
import javax.xml.crypto.KeySelectorException;
import javax.xml.crypto.KeySelectorResult;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.NodeList;


/**
 *
 * @author Alvaro
 */
public class BalanaXACML {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Esta aplicación utiliza la configuracion del directorio uploadsBalanaXACML.\nIntroduce la dirección de la 'policy' que desea utilizar:\n");
        String policyPath = scanner.nextLine();
        System.out.print("Ahora introduzaca el directorio de la 'request' que desea evaluar:\n");
        String requestPath = scanner.nextLine();
        scanner.close();
        evaluateRequest(policyPath,requestPath); 
        String response= uploadXACMLLocation+File.separator+"Responses"+File.separator+"XACMLContextResponse.xml";
        String sign= uploadXACMLLocation+File.separator+"Responses"+File.separator+"XACMLContextResponseSigned.xml";
        String pathKey = "D:\\GIT\\VideoRepro\\HTTPS\\.keystore";
        signXML(response,sign);
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
            
            String data = fileContentToString(requestFile);

            long startTime = System.currentTimeMillis();

            String responsePDP = pdp.evaluate(data);

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
    
    public static void signXML(String xmlFilePath, String outputFilePath) {
                 try{
                     // Genera un par de claves RSA
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            // Cargar el documento XML
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(Files.newInputStream(Paths.get(xmlFilePath)));

            // Crear un contexto de firma
            DOMSignContext dsc = new DOMSignContext(privateKey, doc.getDocumentElement());

            // Crear la fábrica de XMLSignature
            XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

            // Crear el método de referencia
            Reference ref = fac.newReference("", fac.newDigestMethod(DigestMethod.SHA256, null),
                    Collections.singletonList(fac.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null)),
                    null, null);

            // Crear SignedInfo
            SignedInfo si = fac.newSignedInfo(
                    fac.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE, (C14NMethodParameterSpec) null),
                    fac.newSignatureMethod(SignatureMethod.RSA_SHA256, null), Collections.singletonList(ref));

            // Crear KeyInfo
            KeyInfoFactory kif = fac.getKeyInfoFactory();
            KeyValue kv = kif.newKeyValue(publicKey);
            KeyInfo ki = kif.newKeyInfo(Collections.singletonList(kv));

            // Crear la firma XML
            XMLSignature signature = fac.newXMLSignature(si, ki);

            // Firmar el documento
            signature.sign(dsc);

            // Guardar el documento firmado
            try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
                TransformerFactory tf = TransformerFactory.newInstance();
                Transformer trans = tf.newTransformer();
                trans.transform(new DOMSource(doc), new StreamResult(fos));
            }

            System.out.println("Documento firmado con éxito.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}
