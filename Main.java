import java.util.Scanner;

/**
 * Main
 * @author Michael Khart
 * ISC4UE
 * Version - 1.0 - 11/04/2023
 * This class will just instantiate needed variables and objects and start the game loop
 */
public class Main {

    public static void main(String[] args) {

        int size = 1080;

        Const.prob1 = 0.15;
        Const.prob2 = 0.45;
        Const.timeTillResistance = 5;
        Const.administrations = 10;
        Const.cooldownVac = 3;
        Const.wantedRuns = 1000;


        City city = new City(size);
        city.start();
        city.updateCity();


    }
}