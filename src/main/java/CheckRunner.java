import org.apache.log4j.BasicConfigurator;

import java.util.Scanner;

public class CheckRunner {
    public static void main(String[] args) {
        BasicConfigurator.configure();
        Check check = new Check();
        Scanner in = new Scanner(System.in);
        check.getCheck(in.nextLine());
    }
}