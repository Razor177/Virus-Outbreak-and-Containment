import javax.swing.*;
import java.awt.*;
/**
 * Visualizer
 * @author Michael Khart
 * ISC4UE
 * Version - 1.0 - 11/04/2023
 * This class is the visulizer of the simulation. It will use the City and draw its class variable block (the city itself)
 */
public class Visualizer extends JFrame{
    final int MAX_Y = (Toolkit.getDefaultToolkit().getScreenSize().height) - 0; // creates the height of the simulation
    final int GridToScreenRatio;

    private GraphicsPanel panel;
    private Neighbourhood[][] block; // the city itself from the City object

    Visualizer (City city) {
        super("Virus Outbreak");

        this.panel = new GraphicsPanel();
        this.panel.setBackground(Color.LIGHT_GRAY);
        this.getContentPane().add(BorderLayout.CENTER, panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setVisible(true);


        this.block = city.getBlock();
        this.GridToScreenRatio = MAX_Y / (block.length); // finds the proper scaling for the screen


    }
    
    private class GraphicsPanel extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Neighbourhood currentN;
            for(int row = 0; row < block.length; row++) {
                for(int column = 0; column < block.length; column++) {

                    currentN = block[row][column];
                    if (currentN.getStatus() == 'I') {
                        g.setColor(Color.RED);
                    } else if (currentN.getStatus() == 'R') {
                        g.setColor(Color.GREEN);
                    } else if (currentN.getStatus() == 'V') {
                        g.setColor(Color.BLUE);
                    } else {
                        g.setColor(Color.GRAY);
                    }


                    g.fillRect(column*GridToScreenRatio, row*GridToScreenRatio, GridToScreenRatio, GridToScreenRatio);
                }
            }



            this.repaint();
        }
    }


}