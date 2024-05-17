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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Alvaro
 */
public class SunXACML {
    private static final String PATH_PROJECT = "D:\\GIT\\VideoRepro";
    private static final String configFilePath = "D:\\GIT\\VideoRepro\\uploadsSunXACML\\Config\\config_rbac.xml";
    private static final String pathSign= "D:\\GIT\\VideoRepro\\uploadsSunXACML\\Responses\\XACMLContextResponseSigned.xml";
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
        
        signXML(PATH_PROJECT+ File.separator + "uploadsSunXACML" +File.separator + "Responses" + File.separator + "XACMLContextResponse.xml",pathSign);


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
