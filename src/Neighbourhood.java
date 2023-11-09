/**
 * Neighbourhood
 * @author Michael Khart
 * ISC4UE
 * Version - 1.0 - 11/05/2023
 * This is the custom object that is used to populate the City with. It has some class variables which will be used
 * in the simulation
 */
public class Neighbourhood {
    private char status = Const.DEFAULT;
    private double probability = 0;
    private int timeTillResistance = Const.timeTillResistance; // intitates a counter for how many more runs are needed to earn immunity

    /**
     * updateCounter
     * will update all counters inside the nieghbourhood instance and if needed will give resistance to the neighbourhood
     */
    public void updateCounter() {
        if (timeTillResistance > 0) {
            this.timeTillResistance--;
        } else {
            this.status = 'R';
            this.probability = 0;
        }
    }


    /**
     * Returns the probability associated with this object.
     * @return - The probability value.
     */
    public double getProbability() {
        return probability;
    }

    /**
     * Sets the probability associated with this object.
     * @param probability - The new probability value to set.
     */
    public void setProbability(double probability) {
        this.probability = probability;
    }

    /**
     * Returns the status character associated with this object.
     * @return - The status character.
     */
    public char getStatus() {
        return status;
    }

    /**
     * Sets the status character associated with this object.
     * @param status - The new status character to set.
     */
    public void setStatus(char status) {
        this.status = status;
    }

}
