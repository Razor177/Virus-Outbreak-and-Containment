import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        int size = 10;
        double prob1 = 0.15;
        double prob2 = 0.45;
        int resisTime = 5;
        int administrations = 1;
        int cooldownVac = 3;
        int wantedRuns = 20;

        new Const(prob1, prob2, resisTime, administrations, cooldownVac, wantedRuns);

        City city = new City(size);
        city.start();
        city.updateCity();



    }
}