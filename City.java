import javax.management.QueryEval;
import java.util.*;
import java.util.Queue;


public class City {

    private Neighbourhood[][] block;
    private final int size;
    private Queue<int[]> newlyInfected;
    private PriorityQueue<int[]> vaccinationQ;
    private int[][] neighbors = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1},           {0, 1},
            {1, -1},  {1, 0},  {1, 1}
    };
    private Comparator<int[]> neiComparator = new Comparator<int[]>() {
        @Override
        public int compare(int[] cordSet1, int[] cordSet2) {
            return (countInfected(cordSet1) > countInfected(cordSet2))  ? 1:-1;
        }
    };




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

        this.block[randomRow][randomColumn].setStatus('i');
        this.newlyInfected.add(new int[] {randomRow, randomColumn});
    }

    public void updateCity() {
        this.updateInfections();
        this.updateProbabilities();
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

            neiToCheck = this.block[rowToCheck][colToCheck];

            if (((rowToCheck >= 0) && (rowToCheck < this.size)) && ((colToCheck >= 0) && (colToCheck < this.size))) {
                if (neiToCheck.getStatus() == Const.EMPTY) {
                    neiToCheck.setProbability(Const.prob1);
                } else if (neiToCheck.getStatus() == Const.prob1) {
                    neiToCheck.setProbability(Const.prob2);
                }
            }
        }


    }
    private void updateInfections() {
        Random rand = new Random();
        Neighbourhood currentNei;

        for (int row = 0; row < this.size; row++) {
            for (int column = 0; column < this.size; column++) {
                currentNei = this.block[row][column];
                if (rand.nextDouble() < currentNei.getProbability()) {
                    currentNei.setStatus('i');
                    currentNei.setNewlyInfected(true);
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
                if (neiToCheck.getStatus() == 'i') {
                    infected++;
                }
            }
        }

        return infected;
    }




    private void updateCounters() {
        // update the newlyInfected, the countres for if resistant yet etc
    }



}
