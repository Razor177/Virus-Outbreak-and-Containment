import sun.awt.image.ImageWatched;

import java.util.LinkedList;
import java.util.Random;
import java.util.Queue;


/**
 * City
 * @author Michael Khart
 * ICS4UE
 * Version - 1.0 - 11/04/2023
 * This class acts as the main class. It houses the updateCity method which is the method that will update the city and
 * run the simulation.
 */

public class City {

    private Neighbourhood[][] block; // this is the city itself
    private final int SIZE;
    private LinkedList<int[]> newlyInfected = new LinkedList<>();
    private LinkedList<int[]> vaccinationQ = new LinkedList<>();
    private LinkedList<int[]> toUpdateNeighbours = new LinkedList<>();
    private final int[][] NEIGHBOURS = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1},           {0, 1},
            {1, -1},  {1, 0},  {1, 1}
    }; // this is just used to simplify the process of checking neighbours of some specific index
    private int runs = 0;
    private Visualizer visualizer;
    private Queue<Boolean> stillSpreading = new LinkedList<>(); // used just for the case where the infection dies out before the wanted runs is reached


    /**
     * City
     * Constructor - will create a new City objetct
     * @param SIZE - the SIZE of the City length and width
     */
    public City(int SIZE) {
        this.SIZE = SIZE;

        this.start();
    }


    /**
     * start
     * this method will initiate all the needed objects populate the block, and start the infection.
     */
    public void start() {
        this.block = new Neighbourhood[this.SIZE][this.SIZE];

        for (int row = 0; row < this.SIZE; row++) {
            for (int column = 0; column < this.SIZE; column++) {
                block[row][column] = new Neighbourhood();
            }
        }

        // start infection
        this.block[(this.SIZE / 2)][(this.SIZE / 2)].setStatus('I');
        this.newlyInfected.add(new int[] {(this.SIZE / 2), (this.SIZE / 2)});

        for (int runs = 0; runs < 11; runs++) {
            this.stillSpreading.add(true);
        }

        this.visualizer = new Visualizer(this);
        this.runs++;

    }

    /**
     * updateCity
     * this method calls all of the methods used to update the city, basically a run game loop method.
     */
    public void updateCity() {

        while ((this.runs <= Const.wantedRuns) && stillSpreading()) {

            try{
                Thread.sleep(Const.delay);
            } catch(Exception e) {}

            this.updateInfections();
            this.updatenumInfected();
            if (runs > Const.cooldownVac) {
                this.administerVax();
            }
            this.updateCounters();
            this.updateResistantN();

            this.runs++;

            visualizer.repaint();

        }


//

    }


    /**
     * updatenumInfected
     * this will update all ofthe probabilities for the neighbours of a newly infected nieghbourhood
     */
    private void updatenumInfected() {
        this.stillSpreading.remove();

        if (newlyInfected.size() > 0) {
            for (int[] cordSet : newlyInfected) {
                updateProximity(cordSet[0], cordSet[1], true);
            }
            this.newlyInfected.clear();
            this.stillSpreading.add(true);
        } else {
            this.stillSpreading.add(false);
        }


    }




    /**
     * updateProximity
     * this method will change the risk of all of the surrounding neighbourhoods of the given cordinates based on if a
     * neighbourhood has become infected or resistant
     * @param row - row of neighbhourhood
     * @param column - column of the neighbourhood
     */
    private void updateProximity(int row, int column, boolean option) {

        int rowToCheck;
        int colToCheck;
        Neighbourhood NToCheck;
        int numInfected;

        for (int[] neighbor : NEIGHBOURS) {
            rowToCheck = row + neighbor[0];
            colToCheck = column + neighbor[1];

            if (((rowToCheck >= 0) && (rowToCheck < this.SIZE)) && ((colToCheck >= 0) && (colToCheck < this.SIZE))) {

                NToCheck = this.block[rowToCheck][colToCheck];

                // this method is used to both increase and decrease the probabilities of getting sick for neighbours
                // in an effort to save memory and increase speed, there is an option variable which chooses between
                // increasing or decreasing the probabilites of getting sick for neighbours of a neighbourhood at a
                // specific cordinate

                if (option) {  // If we are increasing the risk of getting sick (happens when a neighbourhood is infected)
                    if (NToCheck.getStatus() != 'R' ) {
                        if (NToCheck.getStatus() == Const.DEFAULT) {
                            NToCheck.setProbability(Const.prob1);
                        } else if (NToCheck.getStatus() == Const.prob1) {
                            NToCheck.setProbability(Const.prob2);
                        }
                    }
                } else if (!option){ // If we are decreasing risk of getting sick (happens when an infected turns resistant)
                    numInfected = countInfected(new int[] {rowToCheck, colToCheck});

                    if (NToCheck.getStatus() != 'R') {
                        if (numInfected >1) {
                            NToCheck.setProbability(Const.prob2);
                        } else if (numInfected == 1) {
                            NToCheck.setProbability(Const.prob1);
                        } else {
                            NToCheck.setProbability(0);
                        }
                    }

                }


            }
        }


    }

    /**
     * updateResistanceN
     * will update all of the risks of getting infected for newly resistant neighbourhoods' neighbours
     */
    private void updateResistantN() {
        for (int[] cordSet : toUpdateNeighbours) {
            updateProximity(cordSet[0], cordSet[1], false);
        }
        this.toUpdateNeighbours.clear();

    }

    /**
     * countInfected
     * This method will find thenumber of infected neighbourhoods surrounding this cordinate within the block.
     * @param cords - a cordinate set to check the surroundings of
     * @return - the number of infected
     */
    private int countInfected(int[] cords) {

        int infected = 0;
        int rowToCheck;
        int colToCheck;
        Neighbourhood NToCheck;

        for (int[] neighbor : NEIGHBOURS) {
            rowToCheck = cords[0] + neighbor[0];
            colToCheck = cords[1] + neighbor[1];

            if (((rowToCheck >= 0) && (rowToCheck < this.SIZE)) && ((colToCheck >= 0) && (colToCheck < this.SIZE))) {

                NToCheck = this.block[rowToCheck][colToCheck];

                if (NToCheck.getStatus() == 'I') {
                    infected++;
                }
            }
        }

        return infected;
    }


    /**
     * updateInfections
     * This method will spread the infection by listing through neighbourhoods and if they are at risk, rolling the dice
     * and seeing if they get infected
     */
    private void updateInfections() {
        Random rand = new Random();

        for (int row = 0; row < this.SIZE; row++) {
            for (int column = 0; column < this.SIZE; column++) {
                Neighbourhood currentN = this.block[row][column];

                if (currentN.getProbability() != 0) {
                    if (rand.nextDouble() < currentN.getProbability()) {
                        this.newlyInfected.add(new int[] {row, column});
                        currentN.setStatus('I');
                        currentN.setProbability(0);
                    }
                }


            }
        }

    }


    /**
     * administerVax
     * This method will find random cordinates and vaccinate the neighbourhoods in those cordinates
     */
    private void administerVax() {

        Random random = new Random();

        int randomColumn;
        int randomRow;

        for (int times = 0; times < Const.administrations; times++) {
            do {
                randomColumn = random.nextInt(this.SIZE);
                randomRow = random.nextInt(this.SIZE);

            } while (this.block[randomRow][randomColumn].getStatus() != Const.DEFAULT);

            (this.block[randomRow][randomColumn]).setStatus('V');
            this.vaccinationQ.add(new int[]{randomRow, randomColumn});

        }



    }

    /**
     * updateCounters
     * Updates counters for Neighbourhood objects within the block.
     */
    private void updateCounters() {
        Neighbourhood currentN;

        for (int row = 0; row < this.SIZE; row++) {
            for (int column = 0; column < this.SIZE; column++) {
                currentN = this.block[row][column];


                if (currentN.getStatus() == 'V') {
                    currentN.updateCounter();

                } else if (currentN.getStatus() == 'I') {
                    currentN.updateCounter();

                    // if an infected turns resistant, we have to decrease the risk of all its neighbours of getting sick
                    // so we add its cordinates to updateNeighbours to have their risk re-assesed
                    if (currentN.getStatus() == 'R') {
                        this.toUpdateNeighbours.add(new int[] {row, column});
                    }
                }

            }
        }
    }

    /**
     * getBlock
     * Returns the 2D array of Neighbourhood objects
     * @return - The 2D array of Neighbourhood objects (block)
     */
    public Neighbourhood[][] getBlock() {
        return block;
    }

    /**
     * stillSpreading
     * checks if there has been a single infection within the last 8 gamecycles
     * @return - if there has been a single ifnection within the last 8 game cycles
     */
    private boolean stillSpreading() {

        for (Boolean bool : this.stillSpreading) {
            if (bool) {
                return true;
            }
        }

        return false;
    }

}
