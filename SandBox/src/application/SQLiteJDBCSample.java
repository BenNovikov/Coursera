package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;

//download jar from: https://bitbucket.org/xerial/sqlite-jdbc/downloads/
//Configure Build Path => Libraries/Add External JAR

public class SQLiteJDBCSample  {
	public static void main(String[] args) throws ClassNotFoundException{
		
//		processSQL();
		
		Map<String, Float> loadedData = 
				loadDataFromCSV("data/LifeExpectancyWorldBankModule3.csv");
	}
	private static void processSQL() throws ClassNotFoundException{
		// load the sqlite-JDBC driver using the current class loader
	    Class.forName("org.sqlite.JDBC");

	    Connection connection = null;
	    try{
	    	// create a database connection
	    	connection = DriverManager.getConnection("jdbc:sqlite:mydatabase.db");
	    	Statement statement = connection.createStatement();
	    	statement.setQueryTimeout(30);  // set timeout to 30 sec.

	    	statement.executeUpdate("drop table if exists person");
	    	statement.executeUpdate("create table person (id integer, name string)");
	    	statement.executeUpdate("insert into person values(1, 'leo')");
	    	statement.executeUpdate("insert into person values(2, 'yui')");
	    	ResultSet rs = statement.executeQuery("select * from person");
	    	while(rs.next()){
	    		// read the result set
	    		System.out.println("name = " + rs.getString("name"));
	    		System.out.println("id = " + rs.getInt("id"));
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
	
	private static Map<String, Float> loadDataFromCSV(String fileName){
		Map<String, Float> loadedData = new HashMap<String, Float>();

		CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(fileName));
            String[] line;
            while ((line = reader.readNext()) != null) {
            	System.out.println("Country: " + line[2] + " Expectancy: " + line[4]);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
			
		return loadedData;
	}
}
