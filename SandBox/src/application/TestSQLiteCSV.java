package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.HashMap;
import java.util.Map;

import java.io.FileReader;
import java.io.IOException;

import com.opencsv.CSVReader;

//download jar from: https://bitbucket.org/xerial/sqlite-jdbc/downloads/
//Configure Build Path => Libraries/Add External JAR

public class TestSQLiteCSV  {
	public static void main(String[] args) throws ClassNotFoundException{
		
		Map<String, Float> loadedData = loadDataFromCSV("data/LifeExpectancyWorldBankModule3.csv");
		System.out.println("\tRead: " + loadedData.size() + " entries");
		
		processSQL(loadedData);
	}
	
	private static void processSQL(Map<String, Float> data) throws ClassNotFoundException{
		// load the SQlite-JDBC driver using the current class loader
	    Class.forName("org.sqlite.JDBC");

	    Connection connection = null;
	    try{
	    	// create a database connection
	    	connection = DriverManager.getConnection("jdbc:sqlite:mydatabase.db");
	    	Statement statement = connection.createStatement();
	    	statement.setQueryTimeout(30);  // set timeout to 30 sec.

	    	statement.executeUpdate("drop table if exists lifexpect");
	    	statement.executeUpdate("create table lifexpect (country string, years float)");
	    	for (Map.Entry<String, Float> entry : data.entrySet()) {
	    		String key = entry.getKey();
	    	    Float value = entry.getValue();
	    	    
	    	    statement.executeUpdate(
	    	    		"INSERT INTO lifexpect VALUES ('" + key + "','" + value + "')");
	    	}
	    		    	
	    	ResultSet rs = statement.executeQuery("select * from lifexpect");
	    	while(rs.next()){
	    		// read the result set
	    		System.out.print("country = " + rs.getString("country") + "; ");
	    		System.out.println("years = " + rs.getInt("years"));
	    		}
	    	}
	    catch(SQLException e){
	    	// if the error message is "out of memory", 
	    	// it probably means no database file is found
	    	System.err.println(e.getMessage());
	    	}
	    finally{
	    	try{
	    		if(connection != null)
	    			connection.close();
	    	}
	    	catch(SQLException e){
	    		// connection close failed.
	    		System.err.println(e);
	    	}
	    }
	    
	  }
	
	private static Map<String, Float> loadDataFromCSV(String fileName) {
		Map<String, Float> loadedData = new HashMap<String, Float>();
		CSVReader reader = null;
		
        try {
        	//first line - CSV file header skipped
            reader = new CSVReader(new FileReader(fileName), ',', '\"', 1);
            
            String key;
            float value = 0;
            for (String[] row : reader) {
            	try {
            		value = Float.parseFloat(row[4]);
                	if((key = new String(row[2]).replace("'", "")) != null) {
                		loadedData.put(key, value);
                		System.out.println(key + " : " + value); 
                	} 
            	} 
            	catch(NumberFormatException  e) {
            		/*
            		 * omit missed (NaN) values with data cleaning 
            		 */
            	}            	
            }            
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
			
		return loadedData;
	}
}
