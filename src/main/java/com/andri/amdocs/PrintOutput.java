package com.andri.amdocs;

import java.io.IOException;
import java.io.PrintWriter;
import org.apache.log4j.Logger;

public class PrintOutput {
	
	private PrintWriter txtWriter = null;
    private PrintWriter xmlWriter = null;
    private String txtFileName = null;
    private String xmlFileName = null;
    
    static Logger log = Logger.getLogger(PrintOutput.class.getName());
    
    public PrintOutput() {
        super();
    }
    
    public void initOutput(Parameters apiParams, PrintOutput printOutput){
            
            
    }
    
    private void openFileFromExplorer(String fileName) {    
            try {
                    Runtime.getRuntime().exec("notepad " +  fileName);
            } catch (IOException e) {
                    System.out.println("Java IO Exception: " + e.getMessage());
            }
    }
    
    public void finishOutput (Parameters apiParams, PrintOutput printOutput){
           
    }
    
    public void printToShell (String txtIn, String xmlIn) {
        
        //System.out.println(txtIn);
        
        log.info(txtIn);
        
        
        /*
        switch (apiParams.getOutputMode()) { // 0 = console only, 1 = screen only, 2 = screen and print files, 3 = tool messages only
        case 0:
                System.out.println(txtIn);
                break;
        case 1:
                textToDisplay.append(txtIn + "\n");
                break;
        case 2:
                textToDisplay.append(txtIn + "\n");
                txtWriter.println(txtIn);
                
                if (xmlIn != null) {
                        xmlWriter.println(xmlIn);
                }
                break;
        }
*/
}
    
    public void printToShell (Parameters apiParams, String txtIn, String xmlIn) {
            
            System.out.println(txtIn);
            
            log.info(txtIn);
            
            
            /*
            switch (apiParams.getOutputMode()) { // 0 = console only, 1 = screen only, 2 = screen and print files, 3 = tool messages only
            case 0:
                    System.out.println(txtIn);
                    break;
            case 1:
                    textToDisplay.append(txtIn + "\n");
                    break;
            case 2:
                    textToDisplay.append(txtIn + "\n");
                    txtWriter.println(txtIn);
                    
                    if (xmlIn != null) {
                            xmlWriter.println(xmlIn);
                    }
                    break;
            }
*/
    }
    
    

}
