import java.sql.*;
import java.util.Scanner;

public class p3 {
    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("You need to include your UserID and Password parameters on the command line");
            return;}

        String username = args[0];
        String password = args[1];

        //System.out.println("-------Oracle JDBC COnnection Testing ---------");
        try {
            // Register the Oracle driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

        } catch (ClassNotFoundException e){
            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            return;
        }
        Connection connection = null;
        try {
            // create the connection string
            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@oracle.wpi.edu:1521:orcl", username, password);
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }

        System.out.println("Include the number of the following menu item as the third parameter on the command line." +
                    "1 – Report Participant Information" +
                    "2 – Report Pottery Information" +
                    "3 – Report Building Galleries Information" +
                    "4 – Update Member ID");


        Scanner scanner = new Scanner(System.in);  // Create a Scanner object
		Scanner otherScanner = new Scanner(System.in);
        int option = scanner.nextInt();

        if(option == 1){
            //query 1 goes here
        }
        if(option == 2){
            //Print out user prompt
            System.out.println("Enter Pottery ID: ");
            String potteryID = otherScanner.nextLine();

            try{
                //Make the SQL statement
                Statement st2 = connection.createStatement();
                String query2 = "select artworkID, firstName || ' ' || lastName AS ArtistName, title, clayBody, price\n" +
                        "from (select A.artworkID, creatorEmail, title, clayBody, price from\n" +
                        "Artwork A join Pottery P on A.artworkID = P.artworkID) AP join\n" +
                        "Participant Part on AP.creatorEmail = Part.email\n" +
                        "where artworkID = " + potteryID;
                System.out.println(query2);
                ResultSet r2 = st2.executeQuery(query2);
                //Initialize variables
                String artworkID = "";
                String artistName = "";
                String title = "";
                String clayBody = "";
                String price = "";
                while (r2.next()) {
                    artworkID = r2.getString("artworkID");
                    artistName = r2.getString("ArtistName");
                    title = r2.getString("title");
                    clayBody = r2.getString("clayBody");
                    price = r2.getString("price");
                }
                //Print out info
                System.out.println("Pottery Information\n" +
                        "Artwork ID: " + artworkID + "\n" +
                        "Artist Name: " + artistName + "\n" +
                        "Title: " + title + "\n" +
                        "Clay Body: " + clayBody + "\n" +
                        "Price: " + price);

                r2.close();
                st2.close();
                connection.close();
            }
            catch (SQLException e){
                e.printStackTrace();
                return;
            }
        }
        if(option == 4){
            System.out.println("Enter the Participant’s Email Address: ");
            String email = scanner.nextLine();
            System.out.println("Enter the updated Member ID: ");
            String memid = Integer.toString(scanner.nextInt());

            try{
                Statement st4 = connection.createStatement();
                String update = "update Participant\n" +
                        "set memberID = " + memid + "\n" +
                        "where email = " + email +";";

                st4.executeUpdate(update);
                st4.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
                return;
            }


        }

    }
}
