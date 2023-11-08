import javax.swing.*;
import java.awt.*;
/**
 * Visualizer
 * @author Michael Khart
 * ICS4UE
 * Version - 1.0 - 11/04/2023
 * This class is the visulizer of the simulation. It will use the City and draw its class variable block (the city itself)
 */
public class Visualizer extends JFrame{
    final int MAX_Y = Toolkit.getDefaultToolkit().getScreenSize().height; // creates the height of the simulation
    final int GridToScreenRatio;
    private GraphicsPanel panel;
    private Neighbourhood[][] block; // the city itself from the City object

    /**
     * Visualizer
     * Constructor - will create a Visualizer instance
     * @param city - the City for the Vizualizer to vizualize
     */
    Visualizer(City city) {

        super("Virus Outbreak");

        this.panel = new GraphicsPanel();
        this.panel.setBackground(Color.LIGHT_GRAY);
        this.getContentPane().add(BorderLayout.CENTER, panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.block = city.getBlock();
        this.GridToScreenRatio = MAX_Y / block.length;

        this.setSize(city.getBlock().length * GridToScreenRatio, city.getBlock().length * GridToScreenRatio);
        this.setVisible(true);

    }


    /**
     * GraphicsPanel
     * @author Michael Khart
     * ICS4UE
     * Version - 1.0 - 11/04/2023
     * Overrides the paintComponent method to custom paint the grid of Neighbourhood elements with different colors
     * based on their status, and triggers continuous repainting.
     */
    private class GraphicsPanel extends JPanel {

        /**
         * @Override - paintComponent
         * overrides the paointComponent and draws different colors for different statuses for Neighbourhoods in City
         * @param g - the graphics object
         */
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



            this.repaint(); // calling repaint draws what we need using the paintComponent
        }
    }


}