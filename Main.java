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

        Scanner input = new Scanner(System.in);
        int wantedRuns = 2000; //input.nextInt();

        City city = new City(findOptimalValues(wantedRuns));
        city.start();
        city.updateCity();


    }


    private static int findOptimalValues(int wantedRuns) {

        Const.wantedRuns = wantedRuns;
        Const.cooldownVac = wantedRuns/10;
        Const.administrations = wantedRuns/10;

        Const.prob1 = 0.10;
        Const.prob2 = 0.50;
        Const.timeTillResistance = 5;

        return (wantedRuns/10) * 3;





    }
}