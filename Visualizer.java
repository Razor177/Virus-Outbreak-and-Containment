import javax.swing.*;
import java.awt.*;

/**
 * Example 2D String visualization
 * @author ICS4UE
 * @version Nov 2023
 */
public class Visualizer extends JFrame{
    final int MAX_X = (int)getToolkit().getScreenSize().getWidth();
    final int MAX_Y = (int)getToolkit().getScreenSize().getHeight();    
    final int GRIDSIZE = MAX_Y/60;
    final int CENTER_COL = (MAX_X/4/GRIDSIZE)/2;
    final int CENTER_ROW = (MAX_Y/2/GRIDSIZE)/2;

    private GraphicsPanel panel;
    private String twoDstring;
    private int rows, cols;
    private int leftCol, topRow;
    
    Visualizer (String twoDstring) {
        this.panel = new GraphicsPanel();
        this.panel.setBackground(Color.LIGHT_GRAY);
        this.getContentPane().add(BorderLayout.CENTER, panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(MAX_X/4, MAX_Y/2);
        this.setVisible(true);
        
        this.twoDstring = twoDstring;
        this.rows = twoDstring.split("\n").length;  //number of lines  
        this.cols = twoDstring.indexOf("\n");       //length of the first line
        this.leftCol = CENTER_COL - cols/2;
        this.topRow  = CENTER_ROW - rows/2;
    }
    
    private class GraphicsPanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            //draw the twoDstring
            String[] lines = twoDstring.split("\n");
            for (int row=0; row<rows; row++){
                String line = lines[row];
                for (int col=0; col<cols; col++){
                    if (line.charAt(col) == Const.EMPTY){
                        g.setColor(Color.LIGHT_GRAY);
                        g.fillRect((leftCol+col)*GRIDSIZE, (topRow+row)*GRIDSIZE, GRIDSIZE, GRIDSIZE);                        
                    }else if (line.charAt(col) == Const.TRAP){                       
                        g.setColor(Color.BLACK);
                        g.fillOval((leftCol+col)*GRIDSIZE, (topRow+row)*GRIDSIZE, GRIDSIZE, GRIDSIZE);
                    }
                }
            }            
            //draw the grid lines
            g.setColor(Color.BLACK);
            for (int x=0; x<MAX_X; x=x+GRIDSIZE){
                g.drawLine(x,0,x,MAX_Y);
            }
            for (int y=0; y<MAX_Y; y=y+GRIDSIZE){
                g.drawLine(0,y,MAX_X,y);
            }
            
            this.repaint();
        }
    }
}