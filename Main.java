import java.util.Scanner;

public class Main {

    public static void main(String[] args) {


        int size = 1031;
        double prob1 = 0.15;
        double prob2 = 0.45;
        int resisTime = 5;
        int administrations = 10;
        int cooldownVac = 3;
        int wantedRuns = 200000;

        new Const(prob1, prob2, resisTime, administrations, cooldownVac, wantedRuns);

        City city = new City(size);
        city.start();
        city.updateCity();



    }
}