import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by Mihir on 2016-12-16.daw
 */
class DialogFactorization extends JDialog{

    //key adapter which only allows positive integers with commas
    private final KeyAdapter keyAdapterPositiveIntegersWithCommas = new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent e) {
            //prevent the key press if it isn't a number or comma
            if (!(Character.isDigit(e.getKeyChar())) && e.getKeyChar() != ',')
            {
                e.consume();
            }
        }
    };

    //key adapter which only allows positive integers
    public static final KeyAdapter keyAdapterPositiveIntegers = new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent e) {
            //prevent the key press if it isn't a number
            if (!Character.isDigit(e.getKeyChar()))
            {
                e.consume();
            }
        }
    };


    public class panelGeneral extends JPanel {

        private final JTextArea txtOutput;
        private final JTextField txtNumber;
        private final JButton btnListFactors;
        private final JButton btnListPrimeFactorization;
        private final JButton btnListMultiples;

        panelGeneral() {
            this.setLayout(new FlowLayout());
            this.setPreferredSize(new Dimension(400, 600));


            txtNumber = new JTextField("", 30);
            //txtNumber.addKeyListener(keyAdapterPositiveIntegers);

            btnListFactors = new JButton("List Factors");
            btnListFactors.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    //only continue if the textbox isn't empty
                    if (!txtNumber.getText().equals("")) {
                        long number = 0;
                        try {
                            //try converting the textbox text into a long
                            number = Long.parseLong(txtNumber.getText());
                        } catch (Exception ignored) { }
                        //only continue if the number isn't 0
                        if (number != 0) {
                            String factorList = "";
                            ArrayList<Long> factors = getFactors(number);
                            //take the numbers between the commas and add them to a factor list
                            for (Long factor : factors) {
                                factorList = factorList + Long.toString(factor) + ", ";
                            }
                            //get rid of the extra comma and space at the end
                            factorList = factorList.substring(0, factorList.length() - 2);

                            txtOutput.setText("Factors: " + factorList);
                        } else {
                            JOptionPane.showMessageDialog(getContentPane(), "Please enter a positive integer greater than 0.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(getContentPane(), "Please enter a positive integer greater than 0.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            txtOutput = new JTextArea("",10,30);
            txtOutput.setFont(new Font("Calibri", Font.PLAIN, 16));
            txtOutput.setLineWrap(true);
            txtOutput.setWrapStyleWord(true);
            txtOutput.setEditable(false);

            btnListPrimeFactorization = new JButton("Prime Factorization");
            btnListPrimeFactorization.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!txtNumber.getText().equals("")) {
                        long number = 0;
                        try {
                            number = Long.parseLong(txtNumber.getText());
                        } catch (Exception ignored) {
                        }

                        if (number > 1) {
                            String primeFactorization = "";
                            String condensedPrimeFactorization = "";
                            ArrayList<Long> primeFactors = getPrimeFactorization(Long.parseLong(txtNumber.getText()));

                            //show full prime factorization without exponents
                            for (int i = 0; i < primeFactors.size(); i++) {
                                long factor = primeFactors.get(i);
                                primeFactorization += factor + " x ";

                                int count = 0;
                                for (Long primeFactor : primeFactors) {
                                    if (factor == primeFactor) count++;
                                }

                                //add exponents to numbers to output the condensed version
                                if (!condensedPrimeFactorization.contains(Long.toString(factor))) {
                                    condensedPrimeFactorization += Long.toString(factor) + (count > 1 ? superscript(Integer.toString(count)) : "") + " x ";
                                }
                            }

                            if (primeFactorization.length() > 0) {
                                //remove the x and spaces at the end since the factorization will have been finished at this point
                                primeFactorization = primeFactorization.substring(0, primeFactorization.length() - 3);
                                condensedPrimeFactorization = condensedPrimeFactorization.substring(0, condensedPrimeFactorization.length() - 3);

                                txtOutput.setText("Prime Factorization: " + primeFactorization);
                                if (!primeFactorization.equals(condensedPrimeFactorization))
                                    txtOutput.setText(txtOutput.getText() + "\nPrime Factorization With Exponents: " + condensedPrimeFactorization);
                            }
                        }
                        else {
                            JOptionPane.showMessageDialog(getContentPane(), "Please enter a positive integer greater than 1.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(getContentPane(), "Please enter a positive integer greater than 1.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            btnListMultiples = new JButton("List Multiples");
            btnListMultiples.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!txtNumber.getText().equals("")) {
                        long number = 0;
                        try {
                            //try to convert the textbox text into a long
                            number = Long.parseLong(txtNumber.getText());
                        } catch (Exception ignored) { }

                        //only continue if the number is positive
                        if (number > 0) {
                            ArrayList<Long> multiples = getMultiples(number);
                            String multipleStr = "";

                            //create a string with the multiples which are separated by commas
                            for (long multiple : multiples) {
                                multipleStr += Long.toString(multiple) + ", ";
                            }
                            //remove the extra comma and space at the end
                            multipleStr = multipleStr.substring(0, multipleStr.length() - 2);
                            txtOutput.setText("Multiples of " + Long.toString(number) + ": " + multipleStr);
                        }
                        else {
                            JOptionPane.showMessageDialog(getContentPane(), "Please enter a positive integer greater than 1.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(getContentPane(), "Please enter a positive integer greater than 1.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });


            this.add(txtNumber);
            this.add(btnListFactors);
            this.add(btnListMultiples);
            this.add(btnListPrimeFactorization);
            this.add(new JScrollPane(txtOutput));
        }
    }




    public class panelMultipleNumbers extends JPanel {

        private final JTextField txtNumbers;
        private final JButton btnListCommonFactors;
        private final JButton btnListCommonMultiples;
        private final JTextArea txtOutput;

        panelMultipleNumbers() {
            this.setLayout(new FlowLayout());
            this.setPreferredSize(new Dimension(400, 600));


            txtNumbers = new JTextField("", 30);
            txtNumbers.addKeyListener(keyAdapterPositiveIntegersWithCommas);

            txtOutput = new JTextArea("",10,30);
            txtOutput.setFont(new Font("Calibri", Font.PLAIN, 16));
            txtOutput.setLineWrap(true);
            txtOutput.setWrapStyleWord(true);
            txtOutput.setEditable(false);

            btnListCommonMultiples = new JButton("List Common Multiples");
            btnListCommonMultiples.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    ArrayList<Long> numbers = new ArrayList<>();
                    try {
                        for (String number : txtNumbers.getText().trim().split(",")) {
                            numbers.add(Long.parseLong(number));
                        }
                    }
                    catch (Exception ignored) { }

                    //only continue if the user entered an appropriate number or numbers
                    if (!numbers.contains(Long.parseLong("0")) && !txtNumbers.getText().equals("") && containsNumbers(txtNumbers.getText())) {
                        ArrayList<Long> commonMultiples = getCommonMultiples(numbers);
                        if (commonMultiples.size() > 1) {
                            String commonMultiplesStr = "";

                            //create a string with the multiples which are separated by commas
                            for (Long commonMultiple : commonMultiples)
                                commonMultiplesStr = commonMultiplesStr + Long.toString(commonMultiple) + ", ";

                            //remove the extra comma and space at the end
                            commonMultiplesStr = commonMultiplesStr.substring(0, commonMultiplesStr.length() - 2);
                            txtOutput.setText("Common Multiples: " + commonMultiplesStr);
                        }
                        else
                            txtOutput.setText("There are no small common multiples.");
                    }
                    else {
                        JOptionPane.showMessageDialog(getContentPane(), "Please enter a positive integer greater than 1.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            btnListCommonFactors = new JButton("List Common Factors");
            btnListCommonFactors.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    ArrayList<Long> numbers = new ArrayList<>();
                    try {
                        //get the numbers the user entered and put them in a list after it is split by a comma
                        for (String number : txtNumbers.getText().trim().split(",")) {
                            numbers.add(Long.parseLong(number));
                        }
                    }
                    catch (Exception ignored) { }
                    //only continue if the user entered an appropriate number or numbers
                    if (!numbers.contains(Long.parseLong("0")) && !txtNumbers.getText().equals("") && containsNumbers(txtNumbers.getText())) {
                        ArrayList<Long> commonFactors = getCommonFactors(numbers);
                        //only continue if there is more than 1 factor
                        if (commonFactors.size() > 1) {
                            String commonFactorStr = "";

                            //combine all the factors into a string of factors separated by commas
                            for (Long commonFactor : commonFactors) {
                                commonFactorStr = commonFactorStr + Long.toString(commonFactor) + ", ";
                            }
                            //remove the extra comma and space at the end
                            commonFactorStr = commonFactorStr.substring(0, commonFactorStr.length() - 2);
                            txtOutput.setText("Common Factors: " + commonFactorStr);
                        }
                        else
                            txtOutput.setText("There are no common factors other than 1.");
                    }
                    else {
                        JOptionPane.showMessageDialog(getContentPane(), "Please enter a positive integer greater than 1.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            this.add(new JLabel("Separate different numbers by commas:"));
            this.add(txtNumbers);
            this.add(btnListCommonFactors);
            this.add(btnListCommonMultiples);
            this.add(new JScrollPane(txtOutput));
        }
    }




    DialogFactorization() {
        this.setTitle("Factorization");
        this.setSize(500, 800);
        this.setLayout(new FlowLayout());
        this.setPreferredSize(new Dimension(400, 600));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.setResizable(false);
        this.setLocationRelativeTo(null);


        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("General", new panelGeneral());
        tabbedPane.addTab("Multiple Numbers", new panelMultipleNumbers());


        this.add(tabbedPane);

        this.pack();
        this.revalidate();
        this.repaint();

        this.setVisible(true);
    }

    //go through every character in the string and check if it is a digit
    private static boolean containsNumbers(String str) {
        char[] buffer = str.toCharArray();
        for (char aBuffer : buffer) {
            if (Character.isDigit(aBuffer)) {
                return true;
            }
        }
        return false;
    }


    private static ArrayList<Long> getFactors(long number) {
        ArrayList<Long> factors = new ArrayList<>();

        //go through every number between 1 and half the number and add that number to a list if it divides the main number without remainders
        for (long i = 1; i <= number / 2; i++) {
            if (number % i == 0) {
                factors.add(i);
            }
        }

        //if the main number hasnt already been added to the list, then add it
        if (!factors.contains(number)) {
            factors.add(number);
        }

        return factors;
    }

    private static ArrayList<Long> getPrimeFactorization(long number) {
        ArrayList<Long> primeFactors = new ArrayList<>();
        long factoredNumber = number;

        //go through every number greater than 2 and repeatedly divide it by numbers that divide it equally without remainders, keep dividing by that number until it no longer divides without remainders
            for (long i = 2; i <= number / 2; ) {
                if (factoredNumber % i == 0) {
                    primeFactors.add(i);
                    factoredNumber /= i;
                } else i++;
            }
        //if the factored number is a prime number itself, then just add itself to the list
        if (primeFactors.size() == 0)
            primeFactors.add(number);

        return primeFactors;
    }

   /* private ArrayList<Long> getCommonFactors(long numL, long numG) {
        ArrayList<Long> commonFactors = new ArrayList<>();

        long tempNum = numG;
        if (numL > numG) {
            numG = numL;
            numL = tempNum;
        }

        for (long i = 1; i <= numL / 2; i++) {
            if (numL % i == 0 && numG % i == 0)
                commonFactors.add(i);
        }
        if (numG % numL == 0)
            commonFactors.add(numL);

        return commonFactors;
    } */


    static ArrayList<Long> getCommonFactors(ArrayList<Long> numbers) {
        ArrayList<Long> commonFactors = new ArrayList<>();

        //go through all of the numbers and find the smallest number
        long lowestNum = Long.MAX_VALUE;
        for (Long number : numbers) {
            if (lowestNum > number) {
                lowestNum = number;
            }
        }

        //go through all of the numbers check if dividing the number gives you no remainder. If it does, than set divisible to false since all of the numbers aren't divisible by that factor.
        for (long i = 1; i <= lowestNum / 2; i++) {
            boolean divisible = true;
            for (Long number : numbers) {
                if (number % i != 0) {
                    divisible = false;
                    break;
                }
            }
            if (divisible)
                commonFactors.add(i);
        }

        //check if the smallest number can divide all the other numbers without a remainder
        boolean divisible = true;
        for (Long number : numbers)
            if (number % lowestNum != 0) {
                divisible = false;
                break;
            }

        if (divisible)
            commonFactors.add(lowestNum);

        return commonFactors;
    }

    private static ArrayList<Long> getCommonMultiples(ArrayList<Long> numbers) {
        ArrayList<Long> commonMultiples = new ArrayList<>();

        //go through all of the numbers and find the largest number in the set
        long largestNum = 0;
        for (Long number : numbers) {
            if (largestNum < number) {
                largestNum = number;
            }
        }

        //get 10 multiples by checking if each of the numbers in the set can divide the largest number without a remainder
        for (long i = largestNum; commonMultiples.size() < 10; i += largestNum) {
            boolean isMultiple = true;
            for (Long number : numbers) {
                if (i % number != 0) {
                    isMultiple = false;
                    break;
                }
            }
            if (isMultiple)
                commonMultiples.add(i);
        }

        return commonMultiples;
    }


    private static ArrayList<Long> getMultiples(long number) {
        ArrayList<Long> multiples = new ArrayList<>();

        //find the first 100 multiples
        for (int i = 2; i < 100; i++) multiples.add(number * i);

        return multiples;
    }


    //make a number superscript
    private static String superscript(String str) {
        str = str.replaceAll("0", "⁰");
        str = str.replaceAll("1", "¹");
        str = str.replaceAll("2", "²");
        str = str.replaceAll("3", "³");
        str = str.replaceAll("4", "⁴");
        str = str.replaceAll("5", "⁵");
        str = str.replaceAll("6", "⁶");
        str = str.replaceAll("7", "⁷");
        str = str.replaceAll("8", "⁸");
        str = str.replaceAll("9", "⁹");
        return str;
    }
}
