import java.util.*;
import java.util.Queue;
import java.lang.StringBuilder;


public class City {

    private Neighbourhood[][] block;
    private final int size;
    private Queue<int[]> newlyInfected = new LinkedList<>();
    private LinkedList<int[]> vaccinationQ = new LinkedList<>();
    private final int[][] neighbors = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1},           {0, 1},
            {1, -1},  {1, 0},  {1, 1}
    };

    private int runs = 0;
    private Visualizer visualizer;



    public City(int size) {
        this.size = size;
    }

    public void start() {
        this.block = new Neighbourhood[this.size][this.size];

        for (int row = 0; row < this.size; row++) {
            for (int column = 0; column < this.size; column++) {
                block[row][column] = new Neighbourhood();
            }
        }

        Random random = new Random();

        int randomColumn = random.nextInt(this.size);
        int randomRow = random.nextInt(this.size);

        this.block[randomRow][randomColumn].setStatus('I');
        this.newlyInfected.add(new int[] {randomRow, randomColumn});


        this.visualizer = new Visualizer(this);
        this.runs++;
    }

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



    private void updateProbabilities() {
        for (int[] cordSet : newlyInfected) {
            updateProximity(cordSet[0], cordSet[1]);
        }
    }
    private void updateProximity(int row, int column) {

        int rowToCheck;
        int colToCheck;
        Neighbourhood neiToCheck;

        for (int[] neighbor : neighbors) {
            rowToCheck = row + neighbor[0];
            colToCheck = column + neighbor[1];

            if (((rowToCheck >= 0) && (rowToCheck < this.size)) && ((colToCheck >= 0) && (colToCheck < this.size))) {

                neiToCheck = this.block[rowToCheck][colToCheck];

                if (neiToCheck.getStatus() == Const.EMPTY) {
                    neiToCheck.setProbability(Const.prob1);
                } else if (neiToCheck.getStatus() == Const.prob1) {
                    neiToCheck.setProbability(Const.prob2);
                }
            }
        }


    }


    private void updateProximity2(int row, int column) {

        int rowToCheck;
        int colToCheck;
        Neighbourhood neiToCheck;

        for (int[] neighbor : neighbors) {
            rowToCheck = row + neighbor[0];
            colToCheck = column + neighbor[1];

            if (((rowToCheck >= 0) && (rowToCheck < this.size)) && ((colToCheck >= 0) && (colToCheck < this.size))) {

                neiToCheck = this.block[rowToCheck][colToCheck];

                if ((neiToCheck.getStatus() == Const.prob2) && (countInfected(new int[] {rowToCheck, colToCheck}) >= 1)) {
                    neiToCheck.setProbability(Const.prob1);
                } else if (neiToCheck.getStatus() == Const.prob1) {
                    neiToCheck.setProbability(Const.EMPTY);
                }
            }
        }


    }
    private int countInfected(int[] cords) {

        int infected = 0;
        int rowToCheck;
        int colToCheck;
        Neighbourhood neiToCheck;

        for (int[] neighbor : neighbors) {
            rowToCheck = cords[0] + neighbor[0];
            colToCheck = cords[1] + neighbor[1];

            neiToCheck = this.block[rowToCheck][colToCheck];

            if (((rowToCheck >= 0) && (rowToCheck < this.size)) && ((colToCheck >= 0) && (colToCheck < this.size))) {
                if (neiToCheck.getStatus() == 'I') {
                    infected++;
                }
            }
        }

        return infected;
    }



    private void updateInfections() {
        Random rand = new Random();

        for (int row = 0; row < this.size; row++) {
            for (int column = 0; column < this.size; column++) {
                Neighbourhood currentNei = this.block[row][column];

                if (rand.nextDouble() < currentNei.getProbability()) {
                    this.newlyInfected.add(new int[] {row, column});
                    currentNei.setStatus('I');
                }
            }
        }

    }


    private void administerVax() {

        Random random = new Random();

        int randomColumn;
        int randomRow;

        randomColumn = random.nextInt(this.size);
        randomRow = random.nextInt(this.size);

        for (int times = 0; times < Const.administrations; times++) {
            do {
                randomColumn = random.nextInt(this.size);
                randomRow = random.nextInt(this.size);

            } while (this.block[randomRow][randomColumn].getStatus() != Const.EMPTY);

            (this.block[randomRow][randomColumn]).setStatus('V');
            this.vaccinationQ.add(new int[]{randomRow, randomColumn});

        }



    }


    private void updateCounters() {
        Neighbourhood currentNei;

        for (int row = 0; row < this.size; row++) {
            for (int column = 0; column < this.size; column++) {
                currentNei = this.block[row][column];

                if ((currentNei.getStatus() == 'V') || (currentNei.getStatus() == 'I')) {
                    currentNei.updateCounter();

                    if (currentNei.getStatus() == 'R') {
                        this.updateProximity2(row, column);

                    }
                }
            }
        }

    }

    private void displayGrid() {
        StringBuilder string = new StringBuilder();
        string.append("===================================\n");
        for (Neighbourhood[] row : this.block) {
            string.append("[");

            for (Neighbourhood neighbourhood : row) {
                string.append(neighbourhood.getStatus() + " ");
            }
            string.append("]\n");
        }
        string.append("===================================");


        System.out.println(string);
    }


    public Neighbourhood[][] getBlock() {
        return block;
    }

    public int getSize() {
        return size;
    }

    public int getRuns() {
        return runs;
    }

}
