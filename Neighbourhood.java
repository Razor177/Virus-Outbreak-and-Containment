public class Neighbourhood {
    private char status;
    private double probability = 0;
    private boolean newlyInfected;

    public Neighbourhood() {
        this.status = Const.EMPTY;
        this.newlyInfected = false;
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
    public boolean isNewlyInfected() {
        return newlyInfected;
    }
    public void setNewlyInfected(boolean newlyInfected) {
        this.newlyInfected = newlyInfected;
    }
}
