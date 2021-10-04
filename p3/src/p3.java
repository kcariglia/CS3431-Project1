import java.sql.*;
import java.util.Scanner;

public class p3 {
    public static void main(String[] args) {

        if (args.length <= 4) {
            System.out.println("You need to include your UserID and Password parameters on the command line");
            return;
        }
        else{
            String username = args[2];
            String password = args[3];

            System.out.println("Include the number of the following menu item as the third parameter on the command line.\n1 – Report Participant Information\n2 – Report Pottery Information\n3 – Report Building Galleries Information\n4 – Update Member ID");
        }
    }
}
