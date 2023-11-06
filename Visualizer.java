import javax.swing.*;
import java.awt.*;

/**
 * Example 2D String visualization
 * @author ICS4UE
 * @version Nov 2023
 */
public class Visualizer extends JFrame{
    final int MAX_X = Toolkit.getDefaultToolkit().getScreenSize().width; // creates the max x
    final int MAX_Y = (Toolkit.getDefaultToolkit().getScreenSize().height) - 50; // creates the may y
    final int GridToScreenRatio;

    private GraphicsPanel panel;
    private City city;
    private Neighbourhood[][] block;

    Visualizer (City city) {
        super("Virus Outbreak");

        this.panel = new GraphicsPanel();
        this.panel.setBackground(Color.LIGHT_GRAY);
        this.getContentPane().add(BorderLayout.CENTER, panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setVisible(true);


        this.city = city;
        this.block = city.getBlock();

        this.GridToScreenRatio = MAX_Y / (block.length);


    }
    
    private class GraphicsPanel extends JPanel {

        GraphicsPanel() {

        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Neighbourhood currentN;
            for(int row = 0; row < block.length; row++) {
                for(int column = 0; column < block.length; column++) {

                    currentN = block[row][column];
                    if (currentN.getStatus() == 'I') {
                        g.setColor(Color.RED);
//                        g.fillRect((leftCol+column)*GRIDSIZE, (topRow+row)*GRIDSIZE, GRIDSIZE, GRIDSIZE);
                    } else if (currentN.getStatus() == 'R') {
                        g.setColor(Color.GREEN);
//                        g.fillRect((leftCol+column)*GRIDSIZE, (topRow+row)*GRIDSIZE, GRIDSIZE, GRIDSIZE);
                    } else if (currentN.getStatus() == 'V') {
                        g.setColor(Color.BLUE);
//                        g.fillRect((leftCol+column)*GRIDSIZE, (topRow+row)*GRIDSIZE, GRIDSIZE, GRIDSIZE);
                    } else {
                        g.setColor(Color.GRAY);
//                        g.fillRect((leftCol+column)*GRIDSIZE, (topRow+row)*GRIDSIZE, GRIDSIZE, GRIDSIZE);
                    }


                    g.fillRect(column*GridToScreenRatio, row*GridToScreenRatio, GridToScreenRatio, GridToScreenRatio);
//                    g.setColor(Color.BLACK);
//                    g.drawRect(column*GridToScreenRatio, row*GridToScreenRatio, GridToScreenRatio, GridToScreenRatio);
                }
            }



            this.repaint();
        }
    }


}