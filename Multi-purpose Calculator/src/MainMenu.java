import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Mihir ons 2016-12-14.
 */

class MainMenu extends JFrame{

    MainMenu(){
        //initialization and settings
        super("Multi-purpose Calculator");
        this.setSize(300, 500);
        this.setLayout(new FlowLayout());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        //action listeners for buttons to open new windows
        JButton btnFactorization = new JButton("Factorization");
        btnFactorization.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new DialogFactorization();
                    }
                });
            }
        });

        JButton btnFunctions = new JButton("Functions");
        btnFunctions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new DialogFunctions();
                    }
                });
            }
        }
        );

        JButton btnCalculator = new JButton("Calculator");
        btnCalculator.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new DialogCalculator();
                    }
                });
            }
        });


        this.add(btnFactorization);
        this.add(btnFunctions);
        this.add(btnCalculator);

        this.pack();
        this.revalidate();
        this.repaint();

        this.setVisible(true);
    }
}

