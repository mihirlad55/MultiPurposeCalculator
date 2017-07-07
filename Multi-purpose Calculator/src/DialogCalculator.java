import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by mihir on 2016-12-25s.
 */
class DialogCalculator extends JDialog {

    char charToAdd = ' ';

    //key press control to only allow certain characters in the calculator
    private final KeyAdapter keyAdapterEquation = new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent e) {
            try {
                //array of chars allowed
                char[] validChars = { '-', '+', '*', '/', '^', '(', ')', '.', 's' };
                //convert char into string
                String sChar = "";
                sChar += e.getKeyChar();
                //prevent the key press if it isn't a number or one of the valid chars
                if (!(Character.isDigit(e.getKeyChar())) && !(stringContainsChars(sChar, validChars))) {
                    e.consume();
                }
                //if the key pressed was s, then instead of adding s to the textbox, add sqrt
                else if (e.getKeyChar() == 's') {
                    e.consume();
                    txtDisplay.setText(txtDisplay.getText() + "sqrt");
                }
            }
            catch (Exception ignored) {

            }
        }
    };


    //action listener to add the button's text to the calculator display textbox
    private final ActionListener actionAddKeyCharToTextBox = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            txtDisplay.setText(txtDisplay.getText() + ((JButton) e.getSource()).getText());
        }
    };


    private boolean stringContainsChars(String s, char[] validChars) {
        //convert char array of valid chars into string for every char
        for (char ch : validChars) {
            String sChar = "";
            sChar += ch;
            //if the string contains the char then return true
            if (s.contains(sChar)) return true;
        }
        //at this point, there would have been no matches, so it will return false
        return false;
    }



    private final JTextField txtDisplay;

    private final JButton btnDot;

    private final JButton btnAdd;
    private final JButton btnSubtract;
    private final JButton btnDivide;
    private final JButton btnMultiply;
    private final JButton btnPower;
    private final JButton btnSqrt;
    private final JButton btnBracketLeft;
    private final JButton btnBracketRight;
    private final JButton btnEquals;

    private final JButton btnClear;
    private final JButton btnBackspace;



    DialogCalculator() {
        //settings and initialization for window
        this.setTitle("Calculator");
        this.setLayout(new FlowLayout());
        this.setPreferredSize(new Dimension(400, 365));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);


        txtDisplay = new JTextField("", 20);
        txtDisplay.addKeyListener(keyAdapterEquation);

        btnAdd = new JButton("+");
        btnAdd.addActionListener(actionAddKeyCharToTextBox);

        btnSubtract = new JButton("-");
        btnSubtract.addActionListener(actionAddKeyCharToTextBox);

        btnMultiply = new JButton("/");
        btnMultiply.addActionListener(actionAddKeyCharToTextBox);

        btnDivide = new JButton("*");
        btnDivide.addActionListener(actionAddKeyCharToTextBox);

        btnPower = new JButton("^");
        btnPower.addActionListener(actionAddKeyCharToTextBox);

        btnSqrt = new JButton("sqrt");
        btnSqrt.addActionListener(actionAddKeyCharToTextBox);

        btnDot = new JButton(".");
        btnDot.addActionListener(actionAddKeyCharToTextBox);

        btnBracketLeft = new JButton("(");
        btnBracketLeft.addActionListener(actionAddKeyCharToTextBox);

        btnBracketRight = new JButton(")");
        btnBracketRight.addActionListener(actionAddKeyCharToTextBox);

        btnClear = new JButton("Clear");
        btnClear.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtDisplay.setText("");
            }
        });

        btnBackspace = new JButton("<--");
        btnBackspace.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //only continue if the display textbox isn't empty
                if (!txtDisplay.getText().equals("")) {
                    //if the last 4 characters of the textbox are "sqrt", then instead of removing only one character, remove the whole "sqrt" substring
                    if (txtDisplay.getText().substring(txtDisplay.getText().length() - 4).equals("sqrt")) {
                        txtDisplay.setText(txtDisplay.getText().substring(0, txtDisplay.getText().length() - 4));
                    }
                    //else just remove one character from the end of the textbox
                    else txtDisplay.setText(txtDisplay.getText().substring(0, txtDisplay.getText().length() - 1));
                }
            }
        });


        btnEquals = new JButton("=");
        btnEquals.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //try to evaluate the equation and get an answer
                try {
                    String answer = Double.toString(Equation.evaluate(txtDisplay.getText().toCharArray()));
                    //if it is an irrational number, then say so
                    if (answer.equals("NaN")) txtDisplay.setText("Irrational Number");
                    //else set the textbox display to the answer
                    else txtDisplay.setText(answer);
                }
                catch (Exception ex) {
                    //an improper equation has been entered so alert the user
                    JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(btnEquals), "Error: Please enter a correct equation", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });



        JPanel txtBoxPanel = new JPanel();
        txtBoxPanel.setPreferredSize(new Dimension(400, 60));
        txtBoxPanel.add(txtDisplay);


        JPanel numberPanel = new JPanel();
        numberPanel.setLayout(new GridLayout(5, 3));
        numberPanel.setPreferredSize(new Dimension(200, 250));

        numberPanel.add(Box.createRigidArea(new Dimension()));
        numberPanel.add(btnClear);
        numberPanel.add(btnBackspace);

        JButton[] numButtons = new JButton[10];

        for (int i = 1; i <= 9; i++) {
            //for all the numbers between 1 and 9, make a button, add the "AddKeyCharToTextbox" action listener, and add it to an array
            numButtons[i] = new JButton(Integer.toString(i));
            numButtons[i].addActionListener(actionAddKeyCharToTextBox);
            numberPanel.add(numButtons[i]);
        }
        //add 0 separately at the bottom
        numButtons[0] = new JButton("0");
        numButtons[0].addActionListener(actionAddKeyCharToTextBox);
        numberPanel.add(btnDot);
        numberPanel.add(numButtons[0]);
        numberPanel.add(btnEquals);

        JPanel operationPanel = new JPanel();
        operationPanel.setLayout(new GridLayout(5,1));
        operationPanel.setPreferredSize(new Dimension(140, 250));

        operationPanel.add(btnBracketLeft);
        operationPanel.add(btnBracketRight);
        operationPanel.add(btnAdd);
        operationPanel.add(btnSubtract);
        operationPanel.add(btnMultiply);
        operationPanel.add(btnDivide);
        operationPanel.add(btnSqrt);
        operationPanel.add(btnPower);

        this.add(txtBoxPanel);
        this.add(numberPanel);
        this.add(operationPanel);

        this.pack();
        this.revalidate();
        this.repaint();
        this.setVisible(true);
    }
}
