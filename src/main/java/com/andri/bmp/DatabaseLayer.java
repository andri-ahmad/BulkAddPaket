package com.andri.bmp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.*;
import oracle.jdbc.pool.OracleDataSource; 
import java.sql.*;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.File;

public class DatabaseLayer {
	
	/** Creates a new instance of DataBaseLayer */
	private String fileName = "db.properties";
    private Properties dataBaseProperties = new Properties();
    private Connection conn;
    private int trxTypeSync;
    
    public DatabaseLayer() {
        System.out.println("Creating DataBaseLayer class [DONE]");
            this.trxTypeSync = 1;
    }
    
    public void loadProperties(String propFileName) throws SQLException, FileNotFoundException, IOException {
        this.dataBaseProperties.load(new FileInputStream(new File("resources/db.properties")));
        //this.conn = connect();
      }

      public void loadDblConnection() throws SQLException,IOException {
    	  
    	  ///InputStream is = app.getFileFromResourceAsStream(fileName);
    	  //this.getClass().getResourceAsStream(fileName)
    	  
    	  ClassLoader classLoader = getClass().getClassLoader();
          InputStream inputStream = classLoader.getResourceAsStream(fileName);
    	  
        this.dataBaseProperties.load(inputStream);
        this.conn = connect();
      }

      public void releaseDblConnection() throws SQLException {
        this.conn.close();
      }

    public Connection getConnection() throws SQLException{
        return this.conn;
    }

      public Connection connect() throws SQLException {
        OracleDataSource ods = new OracleDataSource();
        ods.setUser(this.dataBaseProperties.getProperty("Username").toString());
        ods.setPassword(this.dataBaseProperties.getProperty("Password").toString());
        ods.setURL(this.dataBaseProperties.getProperty("ConnectionString").toString());
        Connection c = ods.getConnection();
        return c;
      }
      
      public void CMconnect() throws SQLException {
        OracleDataSource ods = new OracleDataSource();
        ods.setUser(this.dataBaseProperties.getProperty("CMUsername").toString());
        ods.setPassword(this.dataBaseProperties.getProperty("CMPassword").toString());
        ods.setURL(this.dataBaseProperties.getProperty("CMConnectionString").toString());
        this.conn = ods.getConnection();
      }  
      
      public void CRMDBconnect() throws SQLException {
        OracleDataSource ods = new OracleDataSource();
        ods.setUser(this.dataBaseProperties.getProperty("CRMDBUsername").toString());
        ods.setPassword(this.dataBaseProperties.getProperty("CRMDBPassword").toString());
        ods.setURL(this.dataBaseProperties.getProperty("CRMDBConnectionString").toString());
        this.conn = ods.getConnection();
      }      

      public void close() throws SQLException {
        this.conn.close();
      }

      public Properties getDataBaseProperties() {
        return this.dataBaseProperties;
      }

      public void setDataBaseProperties(Properties DataBaseProperties) {
        this.dataBaseProperties = DataBaseProperties;
      }

      public int updateQuery(String query) throws SQLException {
        Statement queryStatement = this.conn.createStatement();
        int i = queryStatement.executeUpdate(query);
        queryStatement.close();
        return i;
      }

        public ResultSet selectQuery(String query) throws SQLException{
            Statement queryStatement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = queryStatement.executeQuery(query);
            return rs;
        }
        
        

}
