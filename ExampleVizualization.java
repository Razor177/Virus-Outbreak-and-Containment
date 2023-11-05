import java.io.File;
import java.util.Scanner;

public class ExampleVizualization{
    public static void main(String[] args) throws Exception{
        String twoDstring = "";
        Scanner input = new Scanner(new File("twoDstring.txt"));
        while (input.hasNext()){
            twoDstring = twoDstring + input.nextLine() +"\n";
        }
        System.out.println(twoDstring);
        Visualizer v = new Visualizer(twoDstring);
    }
}