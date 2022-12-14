import java.util.Scanner;

public class CheckRunner {
    public static void main(String[] args) {
        Check check = new Check();
        Scanner in = new Scanner(System.in);
        check.getCheck(in.nextLine());
    }
}