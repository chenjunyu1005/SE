import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ora_DBTest_Demo {

	public ora_DBTest_Demo (){
		
	}

	public void testconnection_mysql (int hr_offset) {        
        Connection connect = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs;

    	
		try {
		      // This will load the MySQL driver, each DB has its own driver
		      Class.forName("com.mysql.jdbc.Driver");
		      // Setup the connection with the DB
		      System.out.print("Enter Username ");
		        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		      String root=bufferedReader.readLine();
		      System.out.print("Enter Password ");
		        BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(System.in));
		      String password=bufferedReader1.readLine();
		      
		      //String s = "CREATE DATABASE IF NOT EXISTS db";
		      connect = DriverManager
	    		          .getConnection("jdbc:mysql://localhost:3306/db", root,password);
		      //preparedStatement = connect.prepareStatement(s);
		      //preparedStatement.executeUpdate();
		      /*drop a table
		      
		      String delete="DROP TABLE REGISTRATION";
		      preparedStatement = connect.prepareStatement(delete);
		      preparedStatement.executeUpdate();
		      */
		     //create table
		      String sql = "CREATE TABLE IF NOT EXISTS REGISTRATION " +
	                   "(id INTEGER not NULL auto_increment, " +
	                   " first VARCHAR(255), " + 
	                   " last VARCHAR(255), " + 
	                   " age INTEGER, " + 
	                   " PRIMARY KEY ( id ))";	    	  
	    	  preparedStatement = connect.prepareStatement(sql);

	    	  preparedStatement.executeUpdate();
	    	 //insert data
	    	  preparedStatement.executeUpdate("INSERT INTO REGISTRATION(first, last, age) "
	    	          +"VALUES ('A', 'B', '20')");
	    	  preparedStatement.executeUpdate("INSERT INTO REGISTRATION(first, last, age) "
	    	          +"VALUES ('C', 'D', '21')");
	    	  preparedStatement.executeUpdate("INSERT INTO REGISTRATION(first, last, age) "
	    	          +"VALUES ('E', 'F', '22')");
	    	 
	    	//retrieve data 
	    	  ResultSet r0=preparedStatement.executeQuery("SELECT * FROM db.registration");
              while(r0.next()){
            	  int id = r0.getInt(1);
            	  String first = r0.getString(2);
            	  String last = r0.getString(3);
            	  int age = r0.getInt(4);
        
            	  System.out.println(id + "\t" + first +
                          "\t" + last + "\t" + age );
            	
           
              }
              r0.close();
	          preparedStatement.close();
	    	 
		      String qry1a = "SELECT CURDATE() + " + hr_offset;

	    	  System.out.println(qry1a);
	    	 
	    	  // "id, uid, create_time, token for id_management.id_logtime";
	    	  // Parameters start with 1
	    	  preparedStatement = connect.prepareStatement(qry1a);

	    	  ResultSet r2=preparedStatement.executeQuery();

	            if (r2.next())
	            {
	              String nt = r2.getString(1); 
	              System.out.println(hr_offset + " hour(s) ahead of the system clock of mysql at 149.4.223.xxx is:" + nt);
	  
	              
	            }
	            
	            r2.close();
	            preparedStatement.close();
	    	  
	    	} catch (Exception e) {
	    		try {
					throw e;
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		  	} finally {
			      if (preparedStatement != null) {
				        try {
							preparedStatement.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				  }	      

			      if (connect != null) {
			        try {
						connect.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			      }
		    }
	}
	
    public int testConnection (int hr_offset) {
        String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
        String dbURL1 = "jdbc:oracle:thin:@bonnet19.cs.qc.edu:1521:dminor";
        
        String userName1 = "lackner";
        String userPassword1 = "guest";

        String oracle_driver = "oracle.jdbc.driver.OracleDriver";
        
        Connection conn;
        PreparedStatement pstmt;
        ResultSet rs;

    	int flag = 0;
    	String newTime;
    	

        try
        {    
        	Class.forName(oracle_driver);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        try
        {
            conn = DriverManager.getConnection(dbURL1, userName1, userPassword1);
            String stmtQuery = "select sysdate + " + hr_offset + " from dual";
            pstmt = conn.prepareStatement(stmtQuery);
            // pstmt.setString(1,usrname);
            rs = pstmt.executeQuery();
            if (rs.next())
            {
              newTime = rs.getString(1); 
              System.out.println(hr_offset + " hour(s) ahead of the system clock of Oracle at bonnet19 is:" + newTime);
            }
            rs.close();
            pstmt.close();
            
            try
            {
              conn.close();
            } 
            catch (SQLException e) {};
            
        }
        catch (SQLException e)
        {
          System.out.println(e.getMessage());
          flag = -1;
        }
                
    	return flag;
    }
	
	public static void main(String[] args)
	{
		try
		{
			if (args.length != 1) {
				System.out.println("Usage: java -jar Ora_DBTest.jar <number_of_hr_offset>");
				System.out.println("Success returns errorlevel 0. Error return greater than zero.");
				System.exit(1);
			}

	        /* Print a copyright. */
	        System.out.println("Example for Oracle DB connection via Java");
	        System.out.println("Copyright: Bon Sy");
	        System.out.println("Free to use this at your own risk!");
	        
	    	ora_DBTest_Demo DBConnect_instance = new ora_DBTest_Demo();

	       
	    	if (DBConnect_instance.testConnection(Integer.parseInt(args[0])) == 0) {
	            System.out.println("Successful Completion");
	        } else {
	            System.out.println("Oracle DB connection fail");
	        }
	        
	    	
	       DBConnect_instance.testconnection_mysql(Integer.parseInt(args[0]));
	        
		} 
		catch (Exception e){
			// probably error in input
			System.out.println("Hmmm... Looks like input error ....");
		}		
  }
	
	
}