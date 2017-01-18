

//JDBC Test Program - Project 1 Environment Setup

import java.sql.*;			//Enables SQL Processing
import java.sql.Date;
import java.util.*;			//For Scanner Class


public class JDBC {
    
    public static void printMenu() {
        System.out.println("Option 1: Find Movies for a Star");
        System.out.println("Option 2: Insert a new Star");
        System.out.println("Option 3: Insert a new Customer");
        System.out.println("Option 4: Delete a Customer");
        System.out.println("Option 5: Print MetaData");
        System.out.println("Option 6: Enter a SQL Query");
        System.out.println("Option 7: Exit to log in");
        System.out.println("Option 8: Exit");
        
    }
    
    public static void main(String[] arg) throws Exception {
        
        Scanner s = new Scanner(System.in);
        boolean active = true;
        String username;
        String password;
        int command;
        
        System.out.println("Welcome to JDBC! Please enter your credentials");
        
        System.out.print("Username: ");
        username = s.nextLine();
        System.out.print("Password: "); 			//NOTE: password visible in console
        password = s.nextLine();
        
        while (active) {
            
            
            //Incorporate MySQL Driver
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                
                //Connect to Database
                System.out.println("Connecting to Database...");
                Connection connection = DriverManager.getConnection("jdbc:mysql:///moviedb?autoReconnect=true&useSSL=false", username, password);
                System.out.println("Connected!");
                System.out.println();
                System.out.println("Please select an option:");
                printMenu();
                System.out.print("Option: ");
                command = s.nextInt();
                s.nextLine();					//clear buffer
                
                switch (command) {
                    case 1:				//print movies for a given star
                        printMovie(connection, s);
                        break;
                    case 2:				//insert new star
                        insertStar(connection, s);
                        break;
                    case 3:				//insert new customer
                        insertCustomer(connection, s);
                        break;
                    case 4:				//delete customer
                        deleteCustomer(connection, s);
                        break;
                    case 5:				//print metadata
                        printMetadata(connection, s);
                        break;
                    case 6:				//enter valid command
                        executeSqlQuery(connection, s);
                        break;
                    case 7:				//exit to log in
                        break;
                    case 8:				//exit program
                        System.out.println("Bye!");
                        return;
                    default:
                        
                }
                
            } catch (Exception e){
                System.out.println("Connection Failed! Incorrect Username or Password.");
                System.out.println(e.toString());
                System.out.println("Try Again.");
            }
        }
        s.close();
    }
    
    //Create and execute an SQL statement to select all the movies for a given star
    public static void printMovie(Connection connection, Scanner s) {
        char searchBy = ' ';
        String firstName = "", lastName = "", id = "";
        
        
        System.out.print("Enter star's first name:");
        firstName = s.nextLine();
        System.out.print("Enter star's last name:");
        lastName = s.nextLine();
        if(lastName.length() < 1 && firstName.length() < 1){
            System.out.print("Enter star's ID:");
            id = s.nextLine();
            if(id.length() > 0){
                searchBy = 'i';
            }
        }
        else if(firstName.length() < 1){
            searchBy = 'l';
        }
        else if(lastName.length() < 1){
            searchBy = 'n';
        }
        else{
            searchBy = 'b';
        }
        
        Statement select;
        ResultSet result;
        try {
            select = connection.createStatement();
            switch(searchBy){
                case 'n':
                    result = select.executeQuery("select m.* from stars_in_movies, movies m, stars s where first_name = '"+firstName+"' and s.id = star_id and movie_id = m.id");
                    break;
                case 'l':
                    result = select.executeQuery("select m.* from stars_in_movies, movies m, stars s where last_name = '"+lastName+"' and s.id = star_id and movie_id = m.id");
                    break;
                case 'b':
                    result = select.executeQuery("select m.* from stars_in_movies, movies m, stars s where first_name = '"+firstName+"' and last_name = '"+lastName+"' and s.id = star_id and movie_id = m.id");
                    break;
                case 'i':
                    result = select.executeQuery("select m.* from stars_in_movies, movies m, stars s where star_id = '"+id+"' and movie_id = m.id");
                    break;
                default:
                    System.out.println("At least one field (first name, last name or ID) must be entered to query the DB");
                    return;
            }
            System.out.println();
            
            while(result.next()){
                System.out.println("Title: " + result.getString(2));
                System.out.println("Year: " + result.getInt(3));
                System.out.println("Director: " + result.getString(4));
                System.out.println("Banner URL: " + result.getString(5));
                System.out.println("Trailer URL: " + result.getString(6));
                System.out.println();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    //Insert a new star into the database
    public static void insertStar(Connection connection, Scanner s) {
    	String last_name = null;
    	String first_name = null;
    	String date = null;
    	Date dob = null;
    	
    	while (true) {
    		System.out.print("Insert star's last name or only name (required): ");
            last_name = s.nextLine();
            if (!last_name.equals("")) {
            	break;
            }
    	}
        System.out.print("Insert star's first name (optional): ");
        first_name = s.nextLine();
        while (true) {
        	System.out.print("Insert star's date of birth yyyy-mm-dd (optional): ");
            date = s.nextLine();
            if (!date.equals("")) {
            	try {
            		dob = Date.valueOf(date);
                    break;
            	}
                catch (IllegalArgumentException e) {
                	System.out.println("Wrong date format");
                }
            }
        }
        System.out.print("Insert star's photo url (optional): ");
        String photo_url = s.nextLine();
        if (photo_url.equals("")) {
            photo_url = null;
        }
        try {
            String sqlQuery = "INSERT INTO stars (first_name, last_name, dob, photo_url) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStmt = connection.prepareStatement(sqlQuery);
            preparedStmt.setString(1, first_name);
            preparedStmt.setString(2, last_name);
            preparedStmt.setDate(3, dob);
            preparedStmt.setString(4,photo_url);
            preparedStmt.execute();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    //Insert a customer into the database
    public static void insertCustomer(Connection connection, Scanner s) {
        
    }
    
    //Delete a customer from the database
    private static void deleteCustomer(Connection connection, Scanner s) {
        System.out.print("Enter Customer ID for deletion: ");
        String ID = s.nextLine();
        Statement update;
        try {
            update = connection.createStatement();
            int retID = update.executeUpdate("delete from customers where id = " + ID);
            System.out.println("retID = " + retID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //Provide the metadata of the database
    private static void printMetadata(Connection connection, Scanner s) {
        // TODO Auto-generated method stub
        
    }
    
    //Enter a valid SELECT/UPDATE/INSERT/DELETE SQL command
    private static void executeSqlQuery(Connection connection, Scanner s) {
        // TODO Auto-generated method stub
        
    }
}
