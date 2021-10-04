import java.sql.*;
import java.util.Scanner;

public class p3 {
    public static void main(String[] args) {
        System.out.println(args.length);
        if (args.length < 2) {
            System.out.println("You need to include your UserID and Password parameters on the command line");
            return;}

        String username = args[0];
        String password = args[1];

        System.out.println("Include the number of the following menu item as the third parameter on the command line." +
                    "1 – Report Participant Information" +
                    "2 – Report Pottery Information" +
                    "3 – Report Building Galleries Information" +
                    "4 – Update Member ID");


        Scanner scanner = new Scanner(System.in);  // Create a Scanner object
        int option = scanner.nextInt();

        if(option == 1){

        }
        if(option == 2){

        }
        if(option == 3){

        }
        if(option == 4){

        }

    }
}
