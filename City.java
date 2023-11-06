import java.util.LinkedList;
import java.util.Queue;
import java.lang.StringBuilder;
import java.util.Queue;
import java.util.Random;

/**
 * City
 * @author Michael Khart
 * ISC4UE
 * Version - 1.0 - 11/04/2023
 * This class acts as the main class. It houses the updateCity method which is the method that will update the city and
 * run the simulation.
 */

public class City {

    private Neighbourhood[][] block; // this is the city itself
    private final int SIZE;
    private Queue<int[]> newlyInfected = new LinkedList<>();
    private LinkedList<int[]> vaccinationQ = new LinkedList<>();
    private final int[][] NEIGHBOURS = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1},           {0, 1},
            {1, -1},  {1, 0},  {1, 1}
    };
    private int runs = 0;
    private Visualizer visualizer;


    /**
     * City
     * Constructor - will create a new City objetct
     * @param SIZE - the SIZE of the City length and width
     */
    public City(int SIZE) {
        this.SIZE = SIZE;
    }


    /**
     * start
     * this method will initiate all of the needed objects populate the block, and start the infection.
     */
    public void start() {
        this.block = new Neighbourhood[this.SIZE][this.SIZE];

        for (int row = 0; row < this.SIZE; row++) {
            for (int column = 0; column < this.SIZE; column++) {
                block[row][column] = new Neighbourhood();
            }
        }


        this.block[(this.SIZE / 2)][(this.SIZE / 2)].setStatus('I');
        this.newlyInfected.add(new int[] {(this.SIZE / 2), (this.SIZE / 2)});


        this.visualizer = new Visualizer(this);
        this.runs++;
    }

    /**
     * updateCity
     * this method calls all of the methods used to update the city, basically a run game loop method.
     */
    public void updateCity() {

        while (this.runs <= Const.wantedRuns) {
            visualizer.repaint();

            try{Thread.sleep(100); } catch(Exception e) {}


            this.updateInfections();
            this.updateProbabilities();
            if (runs > Const.cooldownVac) {
                this.administerVax();
            }
            this.updateCounters();


            //this.displayGrid();

            this.runs++;

        }

//

    }


    /**
     * updateProbabilities
     * this will list through all of the newly infected neighbourhoods and call the updateProbability method to update
     * the probabilities of neighbours.
     */
    private void updateProbabilities() {
        for (int[] cordSet : newlyInfected) {
            updateProximity(cordSet[0], cordSet[1]);
        }
    }

    /**
     * updateProximity
     * this method will increase the risk of all of the surrounding neighbourhoods of the given cordinates
     * @param row - row of neighbhourhood
     * @param column - column of the neighbourhood
     */
    private void updateProximity(int row, int column) {

        int rowToCheck;
        int colToCheck;
        Neighbourhood neiToCheck;

        for (int[] neighbor : NEIGHBOURS) {
            rowToCheck = row + neighbor[0];
            colToCheck = column + neighbor[1];

            if (((rowToCheck >= 0) && (rowToCheck < this.SIZE)) && ((colToCheck >= 0) && (colToCheck < this.SIZE))) {

                neiToCheck = this.block[rowToCheck][colToCheck];

                if (neiToCheck.getStatus() == Const.DEFAULT) {
                    neiToCheck.setProbability(Const.prob1);
                } else if (neiToCheck.getStatus() == Const.prob1) {
                    neiToCheck.setProbability(Const.prob2);
                }
            }
        }


    }


    /**
     * updateProximity2
     * this method will update the surrounding neighbourhoods to the cordinates given, and will downgrade their probability
     * for getting sick.
     * @param row - the row of the neighbourhood
     * @param column - the column
     */
    private void updateProximity2(int row, int column) {

        int rowToCheck;
        int colToCheck;
        Neighbourhood neiToCheck;

        for (int[] neighbor : NEIGHBOURS) {
            rowToCheck = row + neighbor[0];
            colToCheck = column + neighbor[1];

            if (((rowToCheck >= 0) && (rowToCheck < this.SIZE)) && ((colToCheck >= 0) && (colToCheck < this.SIZE))) {

                neiToCheck = this.block[rowToCheck][colToCheck];

                if ((neiToCheck.getStatus() == Const.prob2) && (countInfected(new int[] {rowToCheck, colToCheck}) >= 1)) {
                    neiToCheck.setProbability(Const.prob1);
                } else if (neiToCheck.getStatus() == Const.prob1) {
                    neiToCheck.setProbability(Const.DEFAULT);
                }
            }
        }


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
        Neighbourhood neiToCheck;

        for (int[] neighbor : NEIGHBOURS) {
            rowToCheck = cords[0] + neighbor[0];
            colToCheck = cords[1] + neighbor[1];

            neiToCheck = this.block[rowToCheck][colToCheck];

            if (((rowToCheck >= 0) && (rowToCheck < this.SIZE)) && ((colToCheck >= 0) && (colToCheck < this.SIZE))) {
                if (neiToCheck.getStatus() == 'I') {
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

        randomColumn = random.nextInt(this.SIZE);
        randomRow = random.nextInt(this.SIZE);

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

                if ((currentN.getStatus() == 'V') || (currentN.getStatus() == 'I')) {
                    currentN.updateCounter();

                    if (currentN.getStatus() == 'R') {
                        this.updateProximity2(row, column);
                    }
                }
            }
        }
    }

    /**
     * getBlock
     * Returns the 2D array of Neighbourhood objects.
     * @return - The 2D array of Neighbourhood objects (block).
     */
    public Neighbourhood[][] getBlock() {
        return block;
    }

}
