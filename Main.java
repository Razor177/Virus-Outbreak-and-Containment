import java.util.Scanner;

public class Main {

    public static void main(String[] args) {


        int size = 1080;

        Const.prob1 = 0.15;
        Const.prob2 = 0.45;
        Const.timeTillResistance = 5;
        Const.administrations = 10;
        Const.cooldownVac = 3;
        Const.wantedRuns = 200000;


        City city = new City(size);
        city.start();
        city.updateCity();



    }
}