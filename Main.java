import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        int size = 10;
        double prob1 = 0.1;
        double prob2 = 0.3;
        int resisTime = 5;
        int administrations = 1;
        int cooldownVac = 10;

        new Const(prob1, prob2, resisTime, administrations, cooldownVac);
        City city = new City(size);
        city.start();
        city.updateCity();



    }
}