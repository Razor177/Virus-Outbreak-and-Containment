import java.util.Scanner;

/**
 * Main
 * @author Michael Khart
 * ICS4UE
 * Version - 1.0 - 11/04/2023
 * This class will just instantiate needed variables and objects and start the game loop
 */
public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        System.out.println("=========================================================== \n" +
                           "                  Welcome to Virus Outbreak                 \n" +
                           "Please select how many cycles of the simulation you would   \n" +
                           "like to play out:");
        System.out.println("=========================================================== \n");

        int wantedRuns = input.nextInt();

        City city = new City(findOptimalValues(wantedRuns));
        city.start();
        city.updateCity();


        input.close();

    }


    /**
     * findOptimalValues\
     * does some rudamentary calculations to give a city size and other variables for the outbreak to not fully
     * conquer the whole map and now spread too little and only cover a small portion
     * @param wantedRuns - the user wanted runs
     * @return - the calculated optimal size
     */
    private static int findOptimalValues(int wantedRuns) {

        Const.wantedRuns = wantedRuns;
        Const.cooldownVac = wantedRuns/30;
        Const.administrations = wantedRuns/15;

        if (wantedRuns/25 > 10) {
            Const.delay = wantedRuns/25;
        } else {
            Const.delay = 10;
        }

        Const.prob1 = 0.10;
        Const.prob2 = 0.50;
        Const.timeTillResistance = 4;

        return (wantedRuns/10) * 4;
    }


}