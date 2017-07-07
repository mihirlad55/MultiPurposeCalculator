import java.util.ArrayList;

/**s
 * Created by mihir on 2016-12-19.
 */
class Equation {

    static double evaluate(char[] equation) {

        equation = removeDoubleSigns(equation);

        //go through the entire array starting from the end looking for a left bracket
        for (int f = equation.length - 1; f >= 0; f--) {
            if (equation[f] == '(') {
                for (int e = f + 1; e < equation.length; e++) {
                    //start going forward from the position of the last left bracket to find the first right bracket
                    if (equation[e] == ')') {
                        //recursive call to evaluate the equation within the brackets and substitute the brackets for that value
                        char[] subEquation = new char[e - f - 1];
                        for (int d = f + 1; d < e; d++) {
                            subEquation[d - f - 1] = equation[d];
                        }
                        equation = removeDoubleSigns(replaceInCharArray(equation, f, e, Double.toString(evaluate(subEquation)).toCharArray()));
                        f = equation.length - 1; //reset the for loop
                    }
                }
            }
        }


        //go through every character in the equation, and find an expression involving a square root
        for (int i = 0; i < equation.length; i++) {
            if (containsMathSign(equation[i])) {
                if (equation[i] == 's') {
                    for (int b = i + 1; b < equation.length; b++) {
                        //continue if the character is an operator but not a negative sign or it is a negative sign right after the squareroot sign or if it is the last character in the equation
                        if ((containsMathSign(equation[b]) && equation[b] != '-') || (b != i + 1 && equation[b] == '-') || (b == equation.length - 1)) {
                            if (b == equation.length - 1) b++;
                            equation = replaceInCharArray(equation, i, b - 1, Operation.SQRT.calculate(equation, i));
                            i = -1;
                            break;
                        }
                    }
                }
            }
        }


        //this variable keeps track of the position of the last number after an operator sign
        int firstPos = 0;
        //go through every character in the equation, and find an expression involving an exponent
        for (int i = 0; i < equation.length; i++) {
            if (containsMathSign(equation[i])) {
                if (equation[i] == '^') {
                    for (int b = i + 1; b < equation.length; b++) {
                        //continue if the character is an operator but not a negative sign or it is a negative sign right after the ^ sign or if it is the last character in the equation
                        if ((containsMathSign(equation[b]) && equation[b] != '-') || (b != i + 1 && equation[b] == '-') || (b == equation.length - 1)) {
                            if (b == equation.length - 1) b++;
                            equation = replaceInCharArray(equation, firstPos, b - 1, Operation.POWER.calculate(equation, i));
                            i = -1;
                            break;
                        }
                    }
                }
                //reset the for loop if an operation has just been done
                if (i != -1) {
                    //if the character is a negative sign than set firstPos to that
                    if (equation[i] == '-') {
                        firstPos = i;
                    } else if (equation[i] != '-') {
                        firstPos = i + 1;
                    }
                } else firstPos = 0;
            }
        }

        firstPos = 0;
        //go through every character in the equation, and find an expression involving multiplication or division
        for (int i = 0; i < equation.length; i++) {
            if (containsMathSign(equation[i])) {
                if (equation[i] == '*' || equation[i] == '/') {
                    for (int b = i + 1; b < equation.length; b++) {
                        //continue if the character is an operator but not a negative sign or it is a negative sign right after the multiplication/division sign or if it is the last character in the equation
                        if ((containsMathSign(equation[b]) && equation[b] != '-') || (b != i + 1 && equation[b] == '-') || (b == equation.length - 1)) {
                            if (b == equation.length - 1) b++;
                            if (equation[i] == '*') {
                                equation = replaceInCharArray(equation, firstPos, b - 1, Operation.MULTIPLY.calculate(equation, i));
                            } else if (equation[i] == '/') {
                                equation = replaceInCharArray(equation, firstPos, b - 1, Operation.DIVIDE.calculate(equation, i));
                            }
                            i = -1;
                            break;
                        }
                    }
                }
                //reset the for loop if an operation has just been done
                if (i != -1) {
                    //if the character is a negative sign than set firstPos to that
                    if (equation[i] == '-') {
                        firstPos = i;
                    } else if (equation[i] != '-') {
                        firstPos = i + 1;
                    }
                } else firstPos = 0;
            }
        }

        firstPos = 0;
        //go through every character in the equation, and find an expression involving an addition or subtraction sign
        for (int i = 0; i < equation.length; i++) {
            if (containsMathSign(equation[i])) {
                if ((equation[i] == '+' || equation[i] == '-') && i != 0) {
                    for (int b = i + 1; b < equation.length; b++) {
                        //continue if the character is an operator but not a negative sign or it is a negative sign right after the addition/subtraction sign or if it is the last character in the equation
                        if ((containsMathSign(equation[b]) && equation[b] != '-') || (b != i + 1 && equation[b] == '-') || (b == equation.length - 1)) {
                            if (b == equation.length - 1) b++;
                            if (equation[i] == '+') {
                                equation = replaceInCharArray(equation, firstPos, b - 1, Operation.ADD.calculate(equation, i));
                            } else if (equation[i] == '-') {
                                equation = replaceInCharArray(equation, firstPos, b - 1, Operation.SUBTRACT.calculate(equation, i));
                            }
                            i = -1;
                            break;
                        }
                    }
                }
                //reset the for loop if an operation has just been done
                if (i != -1) {
                    //if the character is a negative sign than set firstPos to that
                    if (equation[i] == '-') {
                        firstPos = i;
                    } else if (equation[i] != '-') {
                        firstPos = i + 1;
                    }
                } else firstPos = 0;
            }
        }

        return Double.parseDouble(new String(equation));
    }

    private static char[] replaceInCharArray(char[] array, int fIndex, int lIndex, char[] arrayReplace) {
        ArrayList<Character> newArray = new ArrayList<>();

        //add the characters between 0 and the first index to the new array
        for (int i = 0; i < fIndex; i++) newArray.add(array[i]);
        //add all the characters in the replacing array to the new array
        for (char ch : arrayReplace) newArray.add(ch);
        //continue from the last index and add the rest of the characters from the main array
        for (int i = lIndex + 1; i < array.length; i++) newArray.add(array[i]);

        return ArrayListToCharArray(newArray);
    }

    private static char[] ArrayListToCharArray(ArrayList<Character> array) {
        char[] newArray = new char[array.size()];
        //go through the array list and set that index in the new array to the character that is supposed to be at that index
        for (int i = 0; i < array.size(); i++) {
            newArray[i] = array.get(i);
        }
        return newArray;
    }

    private static boolean containsMathSign(char c) {
        //check if the character contains any math signs
        return (c == '+' || c == '*' || c == '/' || c == '^' || c == '-' || c == 's');
    }

    /*private static boolean isFinalAnswer(char[] equation) {
        int count = 0;
        for (char ch : equation) {
            if (ch == '-' || ch == '+' || ch == '*' || ch == '/' || ch == '^') count++;
        }
        if (count == 0) return true;
        else if (count == 1 && equation[0] == '-') return true;
        else if (count == 1 && equation[0] == '+') return true;
        return false;
    }*/

    private static char[] reverseArray(char[] array) {
        char[] reversedArray = new char[array.length];

        int count = 0;
        //go through the array in reverse while adding the characters to the new array in the reverse order
        for (int i = array.length - 1; i >= 0; i--) {
            reversedArray[count] = array[i];
            count++;
        }

        return reversedArray;
    }

    private static char[] removeDoubleSigns(char[] equation) {
        //replace any double math signs beside each other to prevent any errors
        return new String(equation).toLowerCase().replaceAll("--", "+").replaceAll("\\+-", "-").replaceAll("-\\+", "-").replaceAll("\\++", "+").replaceAll(" ", "").replaceAll("sqrt", "s").toCharArray();
    }

    public enum Operation {
        ADD,
        SUBTRACT,
        MULTIPLY,
        DIVIDE,
        POWER,
        SQRT;

        char[] calculate(char[] equation, int index) {

            String sFNum = "";
            String sSNum = "";

            //only continue if it is not a square root operation
            if (this != SQRT) {
                //find all the numbers before a math sign (not including negative as a math sign) and add them to an array
                for (int i = index - 1; i >= 0; i--) {
                    if (Character.isDigit(equation[i]) || equation[i] == '.') {
                        sFNum += equation[i];
                    } else if (equation[i] == '-') {
                        sFNum += '-';
                        break;
                    } else break;
                }
            }
            else sFNum = "0";

            //go through all the numbers after the math sign until a math sign is hit (not including a negative as a math sign as long as its not right after the main math sign) and add them to an array
            for (int i = index + 1; i < equation.length; i++) {
                if (Character.isDigit(equation[i]) || equation[i] == '.') {
                    sSNum += equation[i];
                } else if (index + 1 == i && i != equation.length - 1 && equation[i] == '-') {
                    sSNum += '-';
                } else break;
            }

            sFNum = new String(reverseArray(sFNum.toCharArray()));

            double fNum = Double.parseDouble(sFNum);
            double sNum = Double.parseDouble(sSNum);
            double answer = 0.0;

            //do calculation
            switch (this) {
                case POWER:
                    answer = Math.pow(fNum, sNum);
                    break;
                case ADD:
                    answer = fNum + sNum;
                    break;
                case SUBTRACT:
                    answer = fNum - sNum;
                    break;
                case DIVIDE:
                    answer = fNum / sNum;
                    break;
                case MULTIPLY:
                    answer = fNum * sNum;
                    break;
                case SQRT:
                    answer = Math.sqrt(sNum);
                    break;
            }

            //if the answer is 1 / 0, then set the answer to 0.
            if (answer == 1.0 / 0.0) {
                answer = 0.0;
            }

            String answerStr = "";
            //if (answer > 0) answerStr += "+";
            answerStr += Double.toString(answer);

            return answerStr.toCharArray();
        }
    }

}

