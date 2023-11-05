public final class Const{
    public static final char EMPTY = ' ';
    public static final char TRAP = 't';
    public static double prob1;
    public static double prob2;
    public static int resisTime;
    public static int administrations;
    public static int cooldownVac;


    public Const(double prob1, double prob2, int resisTime, int administrations, int cooldownVac) {
        this.prob1 = prob1;
        this.prob2 = prob2;
        this.resisTime = resisTime;
        this.administrations = administrations;
        this.cooldownVac = cooldownVac;
    }
}