package com.andri.bmp;

import com.andri.amdocs.*;
import java.sql.ResultSet;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainClass {
	
	public static void wait(int ms)
	{
	    try
	    {
	        Thread.sleep(ms);
	    }
	    catch(InterruptedException ex)
	    {
	        Thread.currentThread().interrupt();
	    }
	}

	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Application is running..");
		String stb_id;
    	String paket_name;
    	String paket_durasi;
    	String jsonResponse;
    	JSONObject serverJSONResponse;
		
		// Running and load the Log4		
		PrintOutput printOutput = new PrintOutput(); 
		nusantara nusa	= new nusantara();
		nusa.loadProperties();
		
		
		printOutput.printToShell("----------------------------------------", "");
        printOutput.printToShell("++++ BULK MANAGEMENT FOR NUSANTARA +++++", "");
		
		
		// Get Connection to the DB
        printOutput.printToShell("----------------------------", "");
        printOutput.printToShell( "OPENING DATABASE CONNECTION", "");
        
        DatabaseLayer dbl = new DatabaseLayer();
        //dbl.loadProperties("XX");
        dbl.loadDblConnection();
        //dbl.connect();     
        printOutput.printToShell("OPENING DATABASE CONNECTION [DONE]", "");    
		
		
		// Process one hit API
        //string query
        // prepare query from database
        ResultSet rs = null;
        
        String query1 =    "SELECT * FROM PREPAID.BOM WHERE STATUS = 'NEW'";       
        rs = dbl.selectQuery(query1);
        
        // looping data hasil query
        printOutput.printToShell("", "");
        printOutput.printToShell("PROCESSING RECORD..", ""); 
        
        if (!rs.next()) {
        	
        	printOutput.printToShell("", "");
        	printOutput.printToShell( "No record to process." , "");
        	printOutput.printToShell("", "");
        	
        } else {
        	
        	do 
        		{
            	
            	printOutput.printToShell("", "");
            	printOutput.printToShell( "NEW RECORD : " +  rs.getString(1) + " - STB_ID : " + rs.getString(5) 
            			+ " - NAMA-PAKET : " + rs.getString(9) + " - DURASI : " + rs.getString(10)
            			, "" );    	
            	
            	stb_id = rs.getNString(5);
            	paket_name = rs.getNString(9);
            	paket_durasi = rs.getNString(10);
            	jsonResponse = "";
            	
            	try  {
            		
            		wait(1000);
            		
            		// Hit the API to add paket to kartu.
            		serverJSONResponse = nusa.createPaket(stb_id, paket_name, paket_durasi);
           	
            		// baca json string dari hasil create paket
            		String status = serverJSONResponse.getString("status");
            		String message = serverJSONResponse.getString("message");
            		
            		printOutput.printToShell( "Response status = "+ status, "");
            		printOutput.printToShell( "Found Message = " + message, "");
        		
            		// jika json statusnya = 200, maka update ke DB sukses dan message sukses nya di trim 500
            		// jika json status nya selain 200, update ke DB failed dan message selain 200 nya.
            		if (message.length() >= 400 ) {message = message.substring(1, 399);}
            		String messageTrim = status + "-" + message;
            		if (status.equals("200")) {
            			
            			// Update to DB:
            			// update DB kalo sukses di remove
                        dbl.updateQuery("UPDATE PREPAID.BOM SET sys_update_date=sysdate, STATUS = 'DONE',NOTES='"+messageTrim+"'  WHERE ID = " + rs.getString(1) );
                        printOutput.printToShell("Success::Updating to database.." + messageTrim, ""); 
            			
            			
            		} else {
            			
            			// Update selain 200:
            			// Update to DB:
            			// update DB kalo sukses di remove
                        dbl.updateQuery("UPDATE PREPAID.BOM SET sys_update_date=sysdate, STATUS = 'FAILED',NOTES='"+messageTrim+"'  WHERE ID = " + rs.getString(1) );
                        printOutput.printToShell("Success::Updating to database.." + messageTrim, "");
            			
            		}
            				
            		
            	} catch (Exception exp) {
            		
            		// Jika gagal hit catch disini
            		// update ke DB jika ada exception lain.
            		
            		dbl.updateQuery("UPDATE PREPAID.BOM SET sys_update_date=sysdate, STATUS = 'FAILED',NOTES=SUBSTR('"+exp.toString()+"',1,400)  WHERE ID = " + rs.getString(1) );
                    
                    printOutput.printToShell("FINISH with Exception, CLOSING DB CONNECTION & " + exp.toString(), "");
            		
            	}       
            	
            
            } while (rs.next());
            	
        	// End do
        	
        } // End else if rs.net
        
		
		
		// DONE
        
        // Closing database connection
        rs.close();      
        dbl.releaseDblConnection();
        
        printOutput.printToShell("Success::FINISH, CLOSING DB CONNECTION", "");
        printOutput.printToShell("Success::-----------------------------", "");
        

        System.out.println("Application stopped!.");
	}
	
	
	public static void mainCekPaket(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Application is running..");
		String stb_id;
    	
    	JSONObject serverJSONResponse;
		
		// Running and load the Log4		
		PrintOutput printOutput = new PrintOutput(); 
		nusantara nusa	= new nusantara();
		nusa.loadProperties();
		
		
		printOutput.printToShell("----------------------------------------", "");
        printOutput.printToShell("++++ BULK MANAGEMENT FOR NUSANTARA +++++", "");
		
		
		// Get Connection to the DB
        printOutput.printToShell("----------------------------", "");
        printOutput.printToShell( "OPENING DATABASE CONNECTION", "");
        
        DatabaseLayer dbl = new DatabaseLayer();
        //dbl.loadProperties("XX");
        dbl.loadDblConnection();
        //dbl.connect();     
        printOutput.printToShell("OPENING DATABASE CONNECTION [DONE]", "");    
		
		
		// Process one hit API
        //string query
        // prepare query from database
        ResultSet rs = null;
        
        String query1 =    "SELECT * FROM PREPAID.BOM WHERE STATUS = 'NEW'";       
        rs = dbl.selectQuery(query1);
        
        // looping data hasil query
        printOutput.printToShell("", "");
        printOutput.printToShell("PROCESSING RECORD..", ""); 
        while(rs.next()){
        	
        	printOutput.printToShell( "RECORD : " +  rs.getString(1) + " - STB_ID : " + rs.getString(5) 
        			+ " - NAMA-PAKET : " + rs.getString(9) + " - DURASI : " + rs.getString(10)
        			, "" );    	
        	
        	stb_id = rs.getNString(5);
        
        	
        	try  {
        		
        		// Hit the API to add paket to kartu.
        		//serverJSONResponse = nusa.createPaket(stb_id, paket_name, paket_durasi);
        		
        		//test Hit getPaket
        		serverJSONResponse = nusa.hitGetPaket(stb_id);
        		
        		String status = serverJSONResponse.getString("status");
        		JSONArray message = serverJSONResponse.getJSONArray("message");
        		
        		printOutput.printToShell( "Response status = "+ status, "");
        		printOutput.printToShell( "Found Message = " + message.length() + " product ", "");
        		
        		for ( int i=0; i< message.length();i++) {
        			JSONObject product = message.optJSONObject(i);
        			
        			printOutput.printToShell( i+1 + " ", "");
        			printOutput.printToShell( "nama_paket : "+ product.getString("nama_paket"), "");
        			printOutput.printToShell( "harga_minipack : "+ product.getLong("harga_minipack"), "");
        			printOutput.printToShell( "durasi_paket : "+ product.getString("durasi_paket"), "");
        			
        			
        		}
        
        		
        		// baca json string dari hasil create paket
        		
        		// jika json statusnya = 200, maka update ke DB sukses dan message sukses nya di trim 500
        		
        		
        		// jika json status nya selain 200, update ke DB failed dan message selain 200 nya.
        		
        		
        		
        	} catch (Exception exp) {
        		
        		// Jika gagal hit catch disini
        		// update ke DB jika ada exception lain.
        		
        		
                
                printOutput.printToShell("FINISH with Exception, CLOSING DB CONNECTION & " + exp.toString(), "");
        		
        	}        	
        	
        
        }
		
		
		// DONE
        
        // Closing database connection
        rs.close();      
        dbl.releaseDblConnection();
        
        printOutput.printToShell("FINISH, CLOSING DB CONNECTION", "");
        printOutput.printToShell("-----------------------------", "");
        

        System.out.println("Application stopped!.");
	}

}
