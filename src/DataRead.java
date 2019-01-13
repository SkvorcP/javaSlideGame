import java.util.InputMismatchException;
import java.util.Scanner;

public class DataRead {

    public static int readInt() {
        boolean success = false;
        int number = 0;
        while (!success) {
            try {
                Scanner scanner = new Scanner(System.in);
                number = scanner.nextInt();
                success = true;
            } catch (InputMismatchException e) {
                System.out.print("Wrong number format! Input data: ");
            }
        }
        return number;
    }

    public static double readDouble() {
        boolean success = false;
        double number = 0.0;
        while (!success) {
            try {
                Scanner scanner = new Scanner(System.in);
                number = scanner.nextDouble();
                success = true;
            } catch (InputMismatchException e) {
                System.out.print("Wrong number format! Input data: ");
            }
        }
        return number;
    }

    public static String readString() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}