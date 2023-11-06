public final class Const{
    public static final char EMPTY = '-';
    public static double prob1;
    public static double prob2;
    public static int timeTillResistance;
    public static int administrations;
    public static int cooldownVac;
    public static int wantedRuns;


    public Const(double prob1, double prob2, int resisTime, int administrations, int cooldownVac, int wantedRuns) {
        this.prob1 = prob1;
        this.prob2 = prob2;
        this.timeTillResistance = resisTime;
        this.administrations = administrations;
        this.cooldownVac = cooldownVac;
        this.wantedRuns = wantedRuns;
    }
}