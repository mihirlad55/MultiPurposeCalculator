import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by Mihir ons 2016-12-17.s
 */

class DialogFunctions extends JDialog {


    //key press control for textbox
    public static final KeyAdapter keyAdapterDecimalsOnly = new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent e) {
            try {
                //if the char isn't a number or a negative sign or a period, then prevent the key press
                if (!(Character.isDigit(e.getKeyChar())) && e.getKeyChar() != '-' && e.getKeyChar() != '.')
                {
                    e.consume();
                }
            }
            catch (Exception ex) {
                if (e.getKeyChar() != '-' && e.getKeyChar() != '.')
                    e.consume();
            }
        }
    };

    //key press control for textbox
    private static final KeyAdapter keyAdapterEquation = new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent e) {
            try {
                //array of chars allowed
                char[] validChars = { '-', '+', '*', '/', '^', '(', ')', '.', 's', 'q', 'r', 't'};
                //convert char to string
                String sChar = "";
                sChar += e.getKeyChar();
                //prevent key press if key is not a digit or if it isn't one of the valid chars
                if (!(Character.isDigit(e.getKeyChar())) && !(stringContainsChars(sChar, validChars))) {
                    e.consume();
                }
            }
            catch (Exception ignored) {

            }
        }
    };

    
    class PanelCalculateQuadratic extends JPanel {
        
        final private JTextField txtYVal;
        final private JTextField txtXVal;
        final private JTextField txtAVal;
        final private JTextField txtBVal;
        final private JTextField txtCVal;
        final private JTextField txtHVal;
        final private JTextField txtKVal;

        final private JTextField lblYVal;
        final private JTextField lblX1Val;
        final private JTextField lblX2Val;
        final private JTextField lblRoot1;
        final private JTextField lblRoot2;
        final private JTextField lblAVal;
        final private JTextField lblBVal;
        final private JTextField lblCVal;
        final private JTextField lblHVal;
        final private JTextField lblKVal;
        final private JTextField lblOpenDirection;

        final private JButton btnSolve;
        
        PanelCalculateQuadratic() {
            this.setPreferredSize(new Dimension(400, 500));
            getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
            
            txtYVal = new JTextField("", 4);
            txtYVal.addKeyListener(keyAdapterEquation);

            txtXVal = new JTextField("", 4);
            txtXVal.addKeyListener(keyAdapterEquation);

            txtAVal = new JTextField("", 4);
            txtAVal.addKeyListener(keyAdapterEquation);

            txtBVal = new JTextField("", 4);
            txtBVal.addKeyListener(keyAdapterEquation);

            txtCVal = new JTextField("", 4);
            txtCVal.addKeyListener(keyAdapterEquation);

            txtHVal = new JTextField("", 4);
            txtHVal.addKeyListener(keyAdapterEquation);

            txtKVal = new JTextField("", 4);
            txtKVal.addKeyListener(keyAdapterEquation);


            lblYVal = new JTextField("", 5);
            lblYVal.setEditable(false);

            lblX1Val = new JTextField("", 5);
            lblX1Val.setEditable(false);

            lblX2Val = new JTextField("", 5);
            lblX2Val.setEditable(false);

            lblAVal = new JTextField("", 5);
            lblAVal.setEditable(false);

            lblBVal = new JTextField("", 5);
            lblBVal.setEditable(false);

            lblCVal = new JTextField("", 5);
            lblCVal.setEditable(false);

            lblHVal = new JTextField("", 5);
            lblHVal.setEditable(false);

            lblKVal = new JTextField("", 5);
            lblKVal.setEditable(false);

            lblRoot1 = new JTextField("", 5);
            lblRoot1.setEditable(false);

            lblRoot2 = new JTextField("", 5);
            lblRoot2.setEditable(false);

            lblOpenDirection = new JTextField("", 5);
            lblOpenDirection.setEditable(false);


            btnSolve =  new JButton("Solve");
            btnSolve.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    short count = 0;

                    //create a new quadratic equation object
                    Functions.Quadratic quadraticEqtn = new Functions.Quadratic();

                    //if the textbox isn't empty then set a particular variable to that value after evaluating it
                    if (!txtYVal.getText().equals("")) quadraticEqtn.setY(Equation.evaluate(txtYVal.getText().toCharArray()));
                    if (!txtXVal.getText().equals("")) quadraticEqtn.setX(Equation.evaluate(txtXVal.getText().toCharArray()));
                    if (!txtAVal.getText().equals("")) quadraticEqtn.setA(Equation.evaluate(txtAVal.getText().toCharArray()));
                    if (!txtBVal.getText().equals("")) quadraticEqtn.setB(Equation.evaluate(txtBVal.getText().toCharArray()));
                    if (!txtCVal.getText().equals("")) quadraticEqtn.setC(Equation.evaluate(txtCVal.getText().toCharArray()));
                    if (!txtHVal.getText().equals("")) quadraticEqtn.setH(Equation.evaluate(txtHVal.getText().toCharArray()));
                    if (!txtKVal.getText().equals("")) quadraticEqtn.setK(Equation.evaluate(txtKVal.getText().toCharArray()));

                    //if the textboxes a, b, and c are not empty or there are at least 4 known values of the equation
                    if ((!txtAVal.getText().equals("") && !txtBVal.getText().equals("") && !txtCVal.getText().equals("")) || quadraticEqtn.numberOfKnownValues() >= 4) {

                        //solve for any unknown values if possible
                        quadraticEqtn.checkForUnknowns();

                        //if the variable is not unknown, then display the value
                        if (!Double.isNaN(quadraticEqtn.getY())) lblYVal.setText(Double.toString(quadraticEqtn.getY()));
                        else lblYVal.setText("");

                        if (!Double.isNaN(quadraticEqtn.getX1())) lblX1Val.setText(Double.toString(quadraticEqtn.getX1()));
                        else lblX1Val.setText("");

                        if (!Double.isNaN(quadraticEqtn.getX2())) lblX2Val.setText(Double.toString(quadraticEqtn.getX2()));
                        else lblX2Val.setText("");

                        if (!Double.isNaN(quadraticEqtn.getH1())) lblHVal.setText(Double.toString(quadraticEqtn.getH1()));
                        else lblHVal.setText("");

                        if (!Double.isNaN(quadraticEqtn.getH2())) lblHVal.setText(lblHVal.getText() + " or " + Double.toString(quadraticEqtn.getH2()));

                        lblAVal.setText(Double.toString(quadraticEqtn.getA()));
                        lblBVal.setText(Double.toString(quadraticEqtn.getB()));
                        lblCVal.setText(Double.toString(quadraticEqtn.getC()));
                        lblKVal.setText(Double.toString(quadraticEqtn.getK()));


                        if (!Double.isNaN(quadraticEqtn.getRoot1())) lblRoot1.setText(Double.toString(quadraticEqtn.getRoot1()));
                        else lblRoot1.setText("");

                        if (!Double.isNaN(quadraticEqtn.getRoot2())) lblRoot2.setText(Double.toString(quadraticEqtn.getRoot2()));
                        else lblRoot2.setText("");

                        if (quadraticEqtn.getRoot1() == Double.NaN && quadraticEqtn.getRoot2() == Double.NaN) {
                            lblRoot1.setText("None");
                            lblRoot2.setText("None");
                        }

                        //if the a value is greater than 0 than set the text of the label to open up, else set it to open down
                        if (quadraticEqtn.getA() > 0) lblOpenDirection.setText("Up");
                        else lblOpenDirection.setText("Down");
                    }
                    else JOptionPane.showMessageDialog(getContentPane(), "Please enter at least 4 values.");

                }
            });


            JPanel panelInput = new JPanel();
            panelInput.setLayout(new FlowLayout());
            panelInput.setPreferredSize(new Dimension(400, 50));

            panelInput.add(new JLabel("Y:"));
            panelInput.add(txtYVal);
            panelInput.add(new JLabel("X:"));
            panelInput.add(txtXVal);
            panelInput.add(new JLabel("A:"));
            panelInput.add(txtAVal);
            panelInput.add(new JLabel("B:"));
            panelInput.add(txtBVal);
            panelInput.add(new JLabel("C:"));
            panelInput.add(txtCVal);
            panelInput.add(new JLabel("H:"));
            panelInput.add(txtHVal);
            panelInput.add(new JLabel("K:"));
            panelInput.add(txtKVal);

            JPanel panelOutput = new JPanel();
            panelOutput.setLayout(new GridLayout(0, 3));
            panelOutput.setPreferredSize(new Dimension (400, 250));

            panelOutput.add(new JLabel("Y:"));
            panelOutput.add(lblYVal);
            panelOutput.add(Box.createRigidArea(new Dimension(10, 10)));
            panelOutput.add(new JLabel("X1: "));
            panelOutput.add(lblX1Val);
            panelOutput.add(Box.createRigidArea(new Dimension(10, 10)));
            panelOutput.add(new JLabel("X2: "));
            panelOutput.add(lblX2Val);
            panelOutput.add(Box.createRigidArea(new Dimension(10, 10)));
            panelOutput.add(new JLabel("A: "));
            panelOutput.add(new outputTextField().add(lblAVal));
            panelOutput.add(Box.createRigidArea(new Dimension(10, 10)));
            panelOutput.add(new JLabel("B: "));
            panelOutput.add(lblBVal);
            panelOutput.add(Box.createRigidArea(new Dimension(10, 10)));
            panelOutput.add(new JLabel("C: "));
            panelOutput.add(lblCVal);
            panelOutput.add(Box.createRigidArea(new Dimension(10, 10)));
            panelOutput.add(new JLabel("H: "));
            panelOutput.add(lblHVal);
            panelOutput.add(Box.createRigidArea(new Dimension(10, 10)));
            panelOutput.add(new JLabel("K: "));
            panelOutput.add(lblKVal);
            panelOutput.add(Box.createRigidArea(new Dimension(10, 10)));
            panelOutput.add(new JLabel("Root 1: "));
            panelOutput.add(lblRoot1);
            panelOutput.add(Box.createRigidArea(new Dimension(10, 10)));
            panelOutput.add(new JLabel("Root 2: "));
            panelOutput.add(lblRoot2);
            panelOutput.add(Box.createRigidArea(new Dimension(10, 10)));
            panelOutput.add(new JLabel("Opens: "));
            panelOutput.add(new outputTextField().add(lblOpenDirection));


            this.add(new JLabel("Please fill in at least 3 of these values in order to solve for the unknown."));
            this.add(panelInput);
            this.add(btnSolve);
            this.add(panelOutput);

        }
    }

    class outputTextField extends JPanel {

        outputTextField() {
            this.setLayout(new BorderLayout(50, 50));
            this.setPreferredSize(new Dimension(100, 100));
            this.setSize(new Dimension(100, 100));

        }

        @Override
            public Component add(Component comp) {
                addImpl(comp, null, -1);
                return this;
            }
    }
    
    class PanelCalculateLinear extends JPanel {

        final private JTextField txtXVal;
        final private JTextField txtYVal;
        final private JTextField txtMVal;
        final private JTextField txtBVal;

        final private JTextField lblXVal;
        final private JTextField lblYVal;
        final private JTextField lblMVal;
        final private JTextField lblBVal;
        final private JTextField lblXIntercept;

        final private JButton btnSolve;

        PanelCalculateLinear() {
            getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
            this.setPreferredSize(new Dimension(400, 500));

            txtXVal = new JTextField("", 4);
            txtXVal.addKeyListener(keyAdapterEquation);

            txtYVal = new JTextField("", 4);
            txtYVal.addKeyListener(keyAdapterEquation);

            txtMVal = new JTextField("", 4);
            txtMVal.addKeyListener(keyAdapterEquation);

            txtBVal = new JTextField("", 4);
            txtBVal.addKeyListener(keyAdapterEquation);

            lblYVal = new JTextField("", 4);
            lblYVal.setEditable(false);

            lblXVal = new JTextField("", 4);
            lblXVal.setEditable(false);

            lblMVal = new JTextField("", 4);
            lblMVal.setEditable(false);

            lblBVal = new JTextField("", 4);
            lblBVal.setEditable(false);

            lblXIntercept = new JTextField("", 4);
            lblXIntercept.setEditable(false);

            btnSolve = new JButton("Solve");
            btnSolve.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    short count = 0;

                    double y = 0, m = 0, x = 0, b = 0;
                    //try to evaluate and set the variable to the textbox text
                    try {
                        x = Equation.evaluate(txtXVal.getText().toCharArray());
                    } catch (Exception ex) {
                        if (!txtXVal.getText().equals("")) JOptionPane.showMessageDialog(getContentPane(), "Please enter a correct non-algebraic equation or a valid number");
                    }
                    try {
                        y = Equation.evaluate(txtYVal.getText().toCharArray());
                    } catch (Exception ex) {
                        if (!txtYVal.getText().equals("")) JOptionPane.showMessageDialog(getContentPane(), "Please enter a correct non-algebraic equation or a valid number");
                    }
                    try {
                        m = Equation.evaluate(txtMVal.getText().toCharArray());
                    } catch (Exception ex) {
                        if (!txtMVal.getText().equals("")) JOptionPane.showMessageDialog(getContentPane(), "Please enter a correct non-algebraic equation or a valid number");
                    }
                    try {
                        b = Equation.evaluate(txtBVal.getText().toCharArray());
                    } catch (Exception ex) {
                        if (!txtBVal.getText().equals("")) JOptionPane.showMessageDialog(getContentPane(), "Please enter a correct non-algebraic equation or a valid number");
                    }


                    //count the number of values filled in
                    if (!txtXVal.getText().equals("")) count++;
                    if (!txtYVal.getText().equals("")) count++;
                    if (!txtMVal.getText().equals("")) count++;
                    if (!txtBVal.getText().equals("")) count++;

                    //if there are 3 values then continue
                    if (count == 3) {
                        //if a certain textbox is empty then find that particular value using the other given values
                        if (txtXVal.getText().equals("")) x = Functions.Linear.findX(y, m, b);
                        else if (txtYVal.getText().equals("")) y = Functions.Linear.findY(x, m, b);
                        else if (txtMVal.getText().equals("")) m = Functions.Linear.findM(y, x, b);
                        else if (txtBVal.getText().equals("")) b = Functions.Linear.findB(y, m, x);

                        lblYVal.setText(Double.toString(y));
                        lblXVal.setText(Double.toString(x));
                        lblMVal.setText(Double.toString(m));
                        lblBVal.setText(Double.toString(b));
                        lblXIntercept.setText(Double.toString(Functions.Linear.findXIntercept(m, b)));
                    }
                    //if all the values are given, then check if the function is correct
                    else if (count == 4) {
                        boolean isCorrect = true;

                        //check every value and see if it works with the other given values
                        if (Math.round(x) != Math.round(Functions.Linear.findX(y, m, b))) isCorrect = false;
                        else if (Math.round(y) != Math.round(Functions.Linear.findY(x, m, b))) isCorrect = false;
                        else if (Math.round(m) != Math.round(Functions.Linear.findM(y, x, b))) isCorrect = false;
                        else if (Math.round(b) != Math.round(Functions.Linear.findB(y, m, x))) isCorrect = false;

                        //provide response to user
                        if (isCorrect) {
                            JOptionPane.showMessageDialog(getContentPane(), "This is linear system is correct.");
                        }
                        else {
                            JOptionPane.showMessageDialog(getContentPane(), "This linear system is incorrect.");
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(getContentPane(), "Please fill in 3 of the values");
                    }
                }
            });



            //adding components and labels to window
            JPanel panelInput = new JPanel();
            panelInput.setLayout(new FlowLayout());
            panelInput.setPreferredSize(new Dimension(400, 50));

            panelInput.add(new JLabel("Y:"));
            panelInput.add(txtYVal);
            panelInput.add(new JLabel("X:"));
            panelInput.add(txtXVal);
            panelInput.add(new JLabel("Slope(m):"));
            panelInput.add(txtMVal);
            panelInput.add(new JLabel("Y-Intercept(b):"));
            panelInput.add(txtBVal);

            JPanel panelOutput = new JPanel();
            panelOutput.setLayout(new GridLayout(0, 3));
            panelOutput.setPreferredSize(new Dimension (400, 120));

            panelOutput.add(new JLabel("Y:"));
            panelOutput.add(lblYVal);
            panelOutput.add(Box.createRigidArea(new Dimension(10, 10)));
            panelOutput.add(new JLabel("X:"));
            panelOutput.add(lblXVal);
            panelOutput.add(Box.createRigidArea(new Dimension(10, 10)));
            panelOutput.add(new JLabel("Slope:"));
            panelOutput.add(lblMVal);
            panelOutput.add(Box.createRigidArea(new Dimension(10, 10)));
            panelOutput.add(new JLabel("Y-Intercept:"));
            panelOutput.add(lblBVal);
            panelOutput.add(Box.createRigidArea(new Dimension(10, 10)));
            panelOutput.add(new JLabel("X-Intercept:"));
            panelOutput.add(lblXIntercept);
            panelOutput.add(Box.createRigidArea(new Dimension(10, 10)));


            this.add(new JLabel("Please fill in at least 4 of these values in order to solve for the unknown."));
            this.add(panelInput);
            this.add(btnSolve);
            this.add(panelOutput);
        }
    }



    DialogFunctions() {
        //adjusting some settings for the window
        this.setTitle("Functions");
        this.setLayout(new FlowLayout());
        this.setPreferredSize(new Dimension(450, 500));
        this.setLocationRelativeTo(null);
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setResizable(false);

        //add tabs to tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Linear", new PanelCalculateLinear());
        tabbedPane.addTab("Quadratic", new PanelCalculateQuadratic());
        add(tabbedPane);

        //initialize
        this.pack();
        this.revalidate();
        this.repaint();
        this.setVisible(true);
    }


    private static boolean stringContainsChars(String s, char[] validChars) {
        for (char ch : validChars) {
            String sChar = "";
            sChar += ch;
            if (s.contains(sChar)) return true;
        }
        return false;
    }

    private static String decimalToFraction(double number) {
        String sNumber = Double.toString(number);
        String beforeDecimal = "";
        String afterDecimal = "";

        for (char ch : sNumber.toCharArray()) {
            if (ch != '.') {
                beforeDecimal += ch;
            }
            else break;
        }

        for (char ch : sNumber.toCharArray()) afterDecimal += ch;

        long denominator = Long.parseLong(Double.toString(Math.pow(10, afterDecimal.length())));
        long numerator = Long.parseLong(afterDecimal);
        numerator += denominator * Long.parseLong(beforeDecimal);

        ArrayList<Long> numberList = new ArrayList<>();
        numberList.add(numerator);
        numberList.add(denominator);

        ArrayList<Long> commonFactors = DialogFactorization.getCommonFactors(numberList);

        long factor = commonFactors.get(commonFactors.size() - 1);
        denominator /= factor;
        numerator /= factor;

        return Long.toString(numerator) + "/" + Long.toString(denominator);
    }
}
