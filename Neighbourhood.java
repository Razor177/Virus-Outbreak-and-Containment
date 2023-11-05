public class Neighbourhood {
    private char status = Const.EMPTY;
    private double probability = 0;
    private int timeTillResistance = Const.timeTillResistance;
    private boolean changedStatus = false;

    public Neighbourhood() {
    }

    public void updateCounter() {
        if (timeTillResistance > 0) {
            this.timeTillResistance--;
            this.changedStatus = false;
        } else {
            this.status = 'R';
            this.changedStatus = true;
            this.probability = 0;
        }
    }


    public int getTimeTillResistance() {
        return timeTillResistance;
    }
    public void setTimeTillResistance(int timeTillResistance) {
        this.timeTillResistance = timeTillResistance;
    }
    public double getProbability() {
        return probability;
    }
    public void setProbability(double probability) {
        this.probability = probability;
    }
    public char getStatus() {
        return status;
    }
    public void setStatus(char status) {
        this.status = status;
    }
    public boolean isChangedStatus() {
        return changedStatus;
    }
    public void setChangedStatus(boolean changedStatus) {
        this.changedStatus = changedStatus;
    }
}
