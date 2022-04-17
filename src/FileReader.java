import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Author: Keegan Farris
 * Date: 1-2-2022
 * Description: In class lab work showing scanners, exceptions, and file reader.
 */


public class FileReader {

    public static void main(String[] args) {

//        Scanner scan = new Scanner(System.in);

        String fileName = "myFile.txt";
        File f = new File(fileName);
        Scanner scan = null;

        try {
            if (f.createNewFile()) {
                System.out.println(fileName + " created successfully.");
            } else {
                System.out.println(fileName + " already exists.");
            }
            scan = new Scanner(f);

        }catch (FileNotFoundException e) {
            System.out.println("File not found " + fileName);
            e.printStackTrace();

        }catch (IOException e) {
            System.out.println("Cannot create file " + fileName);
            e.printStackTrace();
        }

        boolean loop = true;

        while (scan != null && scan.hasNext()) {
            String input = scan.nextLine();
            System.out.println(input);
            listing(input.split(","));
        }

//        while (loop) {
//
//            System.out.print("Enter something (zz to exit): ");
//            String input = scan.nextLine();
//
//            if (input.trim().equalsIgnoreCase("zz")) {
//                loop = false;
//                break;
//            }
//
//            System.out.println("You entered " + input);
//
//        }

    }

    static void listing(String[] words) {
        for (String word : words) {
            System.out.println(word.trim());
        }

    }

}
