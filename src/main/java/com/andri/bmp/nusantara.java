package com.andri.bmp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.net.URL;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import org.json.JSONObject;

import com.andri.amdocs.PrintOutput;

public class nusantara {
	
	private String fileName = "bom.properties";
	private Properties bomProperties = new Properties();
	private static String myURL = "";
	private static String myURLgetPaket = "";
	static PrintOutput printOutput = new PrintOutput();	
	
	public nusantara () {      
		
		
	}
	
	public void loadProperties () throws FileNotFoundException, IOException {

		ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        this.bomProperties.load(inputStream);
        
        nusantara.myURL = this.bomProperties.getProperty("URL").toString();
        nusantara.myURLgetPaket = this.bomProperties.getProperty("URLGETPAKET").toString();
        
		printOutput.printToShell( "Found URL = " + nusantara.myURL, "");
		printOutput.printToShell( "Found URL = " + nusantara.myURLgetPaket, "");
        
        
	}
	
	public JSONObject createPaket(String stb_id, String Paket_name, String Paket_Durasi) throws Exception{
		
		String jsonReturn = "";
		
		try {
			
			URL url = new URL(myURL);			
			URLConnection conn = url.openConnection();
			
			HttpURLConnection http = (HttpURLConnection)conn;
	        http.setRequestMethod("POST"); // PUT is another valid opt   
	        http.setDoOutput(true);			
			
			// Mapping Form
			Map<String, String> parameters = new HashMap<>();
			parameters.put("smartcard", stb_id);
			parameters.put("packagename", Paket_name);
			parameters.put("duration", Paket_Durasi);
			
			String writetoServer = ParameterStringBuilder.getParamsString(parameters);			
			printOutput.printToShell( "Parameter sent to Server = " + parameters.toString() , "");
			printOutput.printToShell( "Parameter sent to Server Formated = " + writetoServer , "");
			
			//setelah jadi string, jadikan string tsb byte, output nya untuk masuk ke dalam stream.
	        byte[] out = writetoServer.toString().getBytes(StandardCharsets.UTF_8);
	        int length = out.length;
	        
	        // send ke stream
	        http.setFixedLengthStreamingMode(length);
	        http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	        http.setRequestProperty("Trans-Api-Key", "d911deb1-04a1-496e-99f7-fe5e14c05e3d");
	        
	        printOutput.printToShell( "Connecting..", "");
	        http.connect();        
	        printOutput.printToShell( "Connected..", "");
			
	        try(OutputStream os = http.getOutputStream()) {
	            
	        	printOutput.printToShell( "Sending message..", "");
	            os.write(out);
	            printOutput.printToShell( "Success::message sent..", "");
	        }
	        
	        // get the input stream       
	        printOutput.printToShell( "Response reading...", ""); 
	        try(InputStream isKu = http.getInputStream() ) {
	            
	            //pake object nya input stream reader.
	            InputStreamReader isr = new InputStreamReader(isKu, Charset.forName("UTF-8"));
	            BufferedReader reader = new BufferedReader(isr); 
	            
	            //buat string builder yang membaca reader
	            StringBuilder sb = new StringBuilder();
	            int cp;
	            while ((cp = reader.read()) != -1) {
	              sb.append((char) cp);
	            }
	            
	            printOutput.printToShell( "Server Response = " + sb.toString(), "");            
	            printOutput.printToShell( "Disconnecting from http..", "");            
	            http.disconnect();            
	            printOutput.printToShell( "Disconnected", "");    
	            
	            jsonReturn = sb.toString();
	            
	        } catch (IOException exp)
	        
	        {
	        	printOutput.printToShell( "IO Exception.." + exp.hashCode(), "");
	        	printOutput.printToShell( "Detail" + exp.toString() +  exp.getMessage(), "");
	            exp.printStackTrace();   
	            
	            throw exp;
	            
	            
	        }	
			
			
		} 
			catch (MalformedURLException e) {

				e.printStackTrace();
				throw e;
			} 
			catch (IOException e) {

				e.printStackTrace();
				throw e;
	  			

			}
		
			catch (Exception exp) {
				
				throw exp;
				
			}
		
		return new JSONObject(jsonReturn);
		
	}
	
	
	public JSONObject hitGetPaket(String stb_id) throws Exception{
		
		String jsonReturn = "";
		
		try {
			
			URL url = new URL(myURLgetPaket);			
			URLConnection conn = url.openConnection();
			
			HttpURLConnection http = (HttpURLConnection)conn;
	        http.setRequestMethod("POST"); // PUT is another valid opt   
	        http.setDoOutput(true);			
			
			// Mapping Form
			Map<String, String> parameters = new HashMap<>();
			parameters.put("smartcard", stb_id);

			
			String writetoServer = ParameterStringBuilder.getParamsString(parameters);			
			printOutput.printToShell( "Parameter sent to Server = " + parameters.toString() , "");
			printOutput.printToShell( "Parameter sent to Server Formated = " + writetoServer , "");
			
			//setelah jadi string, jadikan string tsb byte, output nya untuk masuk ke dalam stream.
	        byte[] out = writetoServer.toString().getBytes(StandardCharsets.UTF_8);
	        int length = out.length;
	        
	        // send ke stream
	        http.setFixedLengthStreamingMode(length);
	        http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	        http.setRequestProperty("Trans-Api-Key", "d911deb1-04a1-496e-99f7-fe5e14c05e3d");
	        
	        printOutput.printToShell( "Connecting..", "");
	        http.connect();        
	        printOutput.printToShell( "Connected..", "");
			
	        try(OutputStream os = http.getOutputStream()) {
	            
	        	printOutput.printToShell( "Sending message..", "");
	            os.write(out);
	            printOutput.printToShell( "message sent..", "");
	        }
	        
	        // get the input stream       
	        printOutput.printToShell( "Response reading...", ""); 
	        try(InputStream isKu = http.getInputStream() ) {
	            
	            //pake object nya input stream reader.
	            InputStreamReader isr = new InputStreamReader(isKu, Charset.forName("UTF-8"));
	            BufferedReader reader = new BufferedReader(isr); 
	            
	            //buat string builder yang membaca reader
	            StringBuilder sb = new StringBuilder();
	            int cp;
	            while ((cp = reader.read()) != -1) {
	              sb.append((char) cp);
	            }
	            
	            printOutput.printToShell( "Server Response = " + sb.toString(), "");            
	            printOutput.printToShell( "Disconnecting from http..", "");            
	            http.disconnect();            
	            printOutput.printToShell( "Disconnected", "");    
	            
	            jsonReturn = sb.toString();
	            
	        } catch (IOException exp)
	        
	        {
	        	printOutput.printToShell( "IO Exception.." + exp.hashCode(), "");
	        	printOutput.printToShell( "Detail" + exp.toString() +  exp.getMessage(), "");
	            exp.printStackTrace();          
	            
	            
	        }	
			
			
		} 
			catch (MalformedURLException e) {

				e.printStackTrace();
				throw e;
			} 
			catch (IOException e) {

				e.printStackTrace();
				throw e;
	  			

			}
		
			catch (Exception exp) {
				
				throw exp;
				
			}
		
		return new JSONObject(jsonReturn);
		
	}

}
