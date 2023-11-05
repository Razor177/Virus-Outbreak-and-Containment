import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        int size = 10;
        double prob1 = 0.2;
        double prob2 = 0.5;
        int resisTime = 3;
        int administrations = 1;
        int cooldownVac = 10;

        new Const(prob1, prob2, resisTime, administrations, cooldownVac);
        City sim = new City(size);



    }
}