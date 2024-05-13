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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author Alvaro
 */
public class SunXACML {
    private static final String PATH_PROJECT = "D:\\GIT\\VideoRepro";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("Esta aplicación utiliza la configuracion del directorio uploadsBalanaXACML.\nIntroduce la dirección de la 'policy' que desea utilizar:\n");
        String policyPath = scanner.nextLine();
        System.out.print("Ahora introduzaca el directorio de la 'request' que desea evaluar:\n");
        String requestPath = scanner.nextLine();
        scanner.close();
        PDP pdp = buildPDP(policyPath);
        RequestCtx request = loadRequestContextFromFile(requestPath);
        ResponseCtx response = pdp.evaluate(request);
        saveResponse(response);

    }
    
    private static PDP buildPDP(String policyPath){
        FilePolicyModule policyModule = new FilePolicyModule();
        policyModule.addPolicy(policyPath);

        CurrentEnvModule envModule = new CurrentEnvModule();
        PolicyFinder policyFinder = new PolicyFinder();
        Set policyModules = new HashSet();
        policyModules.add(policyModule);
        policyFinder.setModules(policyModules);

        AttributeFinder attrFinder = new AttributeFinder();
        List attrModules = new ArrayList();
        attrModules.add(envModule);
        attrFinder.setModules(attrModules);
        
        PDP pdp = new PDP(new PDPConfig(attrFinder, policyFinder, null));
        return pdp;
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
