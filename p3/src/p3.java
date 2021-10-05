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
        int option = scanner.nextInt();

        if(option == 1){
            //query 1 goes here
            System.out.println("Enter Participant’s email address: ");
            String emailAdd = scanner.nextLine();

            try {
                Statement st1 = connection.createStatement();
                String query1 = "select email, firstName ||' '|| lastName AS name, phone, city ||','|| state AS address, memberID\n" +
                        "from Participant\n" +
                        "where email = emailAdd";
                ResultSet r1 = st1.executeQuery(query1);

                String email;
                String name;
                String phone;
                String address;
                String memberID;

                while(r1.next()){
                    email = r1.getString("email");
                    name = r1.getString("name");
                    phone = r1.getString("phone");
                    address = r1.getString("address");
                    memberID = r1.getString("memberID");

                    if(memberID != null) {
                        System.out.println("Participant Information\n" +
                                "Email: " + email +
                                "Name: " + name + "\n" +
                                "Phone " + phone + "\n" +
                                "City/State: " + address + "\n" +
                                "Member ID: " + memberID);
                    } else {
                        System.out.println("Participant Information\n" +
                                "Email: " + email +
                                "Name: " + name + "\n" +
                                "Phone " + phone + "\n" +
                                "City/State: " + address + "\n");
                    }


                    r1.close();
                    st1.close();
                    connection.close();
                }


            }
            catch (SQLException e){
                e.printStackTrace();
                return;
            }

        }
        if(option == 2){
            //query 2 goes here
        }
        if(option == 3){
            System.out.println("Enter Building Name: ");
            String building = scanner.nextLine();

            try {
                Statement st3 = connection.createStatement();
                String query3 = "select buildingName, street, city, state, zipcode, galleryName\n" +
                        "from Building natural join Gallery\n" +
                        "where Building.buildingName = \n" +
                        "group by buildingName " + building + "\n" +
                        "order by galleryName desc;";
                ResultSet r3 = st3.executeQuery(query3);

                String street;
                String city;
                String state;
                String zipcode;
                String galleryName;
                int numgal = 1;

                while(r3.next()){
                    street = r3.getString("street");
                    city = r3.getString("city");
                    state = r3.getString("state");
                    zipcode = r3.getString("zipcode");
                    galleryName = r3.getString("galleryName");

                    System.out.println("Building Gallery Information\n" +
                            "Building name: " + building +
                            "Building address: " + street + " " + city + ", " + state + " " + zipcode+ "\n" +
                            "Gallery " + Integer.toString(numgal) + ": " + galleryName);

                    numgal++;
                    r3.close();
                    st3.close();
                    connection.close();
                }


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
