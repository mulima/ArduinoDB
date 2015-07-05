package arduinodb;
import java.sql.*;


public class DatabaseConnection{
static final String driver = "com.mysql.jdbc.Driver";        
String url = "jdbc:mysql://localhost:3306/arduino";
String username = "root";
String password = "";
//loads the driver
public DatabaseConnection () throws ClassNotFoundException{
		Class.forName(driver);

}
		//get a db connection
	public Connection getConnection() throws Exception{
	
		Connection connection =DriverManager.getConnection(this.url,this.username, this.password);
 
 		return connection;
	}


//method to select a particular column from a particular table
 public ResultSet select(String col,String tab)
  	{
  	
  	ResultSet rs_1=null;
  	
  	
  	try{
  		
  		DatabaseConnection d=new DatabaseConnection();
  		Connection conn=d.getConnection();
  		Statement stmt=conn.createStatement();
  		String query="SELECT "+col+" FROM "+tab;
  		ResultSet rs=stmt.executeQuery(query);
  		rs_1=rs;
  				
  		
  		
  		}
  		
  
  	catch(Exception ex) {
         ex.printStackTrace();
       }  
  	
  	
  	return rs_1;
  	
  	}


//a general method to run a select query having a where clause
public ResultSet selectWhere(String col,String tab,String whereParameter,String equalsParameter)
  	{
  	
  	ResultSet rs_1=null;
  	
  	
  	try{
  		
  		DatabaseConnection d=new DatabaseConnection();
  		Connection conn=d.getConnection();
  		Statement stmt=conn.createStatement();
  		String query="SELECT "+col+" FROM "+tab+" where "+whereParameter+"='"+equalsParameter+"'";
  	//	System.out.println("Called selectWhere() method in DatabaseConnection class ["+ query+"]");
  		ResultSet rs=stmt.executeQuery(query);
  		rs_1=rs;
  				
  		
  		
  		}
  		
  
  	catch(Exception ex) {
         ex.printStackTrace();
       }  
  	
  	
  	return rs_1;
  	
  	}



}