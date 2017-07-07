import java.util.ArrayList;

/**s
 * Created by mihir on 2016-12-19.
 */
public class EquationB {

    static double evaluate(char[] equation) {
        String sEquation = new String(equation).replaceAll("--", "+").replaceAll("\\+-", "-").replaceAll("-\\+", "-").replaceAll("\\++", "+").replaceAll(" ", "");
        equation = sEquation.toCharArray();

        for (int f = equation.length - 1; f >= 0; f--) {
            if (equation[f] == '(') {
                for (int e = f + 1; e < equation.length; e++) {
                    if (equation[e] == ')') {
                        char[] subEquation = new char[e - f - 1];
                        for (int d = f + 1; d < e; d++) {
                            subEquation[d - f - 1] = equation[d];
                        }
                        equation = insertBetweenCharArray(equation, f, e, Double.toString(evaluate(subEquation)).toCharArray());
                        f = equation.length - 1;
                    }
                }
            }
        }

        int firstPos = 0;
        for (int i = 0; i < equation.length; i++) {
            if (containsMathSign(equation[i])) {
                if (equation[i] == '^') {
                    for (int b = i + 1; b < equation.length; b++) {
                        if ((containsMathSign(equation[b]) && equation[b] != '-') || (b != i + 1 && equation[b] == '-') || (b == equation.length - 1)) {
                            if (b == equation.length - 1) b++;
                            equation = insertBetweenCharArray(equation, firstPos, b - 1, Operation.POWER.calculate(equation, i));
                            i = -1;
                            break;
                        }
                    }
                }
                if (i != -1) {
                    if (equation[i] == '-') {
                        firstPos = i;
                    } else if (equation[i] != '-') {
                        firstPos = i + 1;
                    }
                } else firstPos = 0;
            }
        }

        firstPos = 0;
        for (int i = 0; i < equation.length; i++) {
            if (containsMathSign(equation[i])) {
                if (equation[i] == '*' || equation[i] == '/') {
                    for (int b = i + 1; b < equation.length; b++) {
                        if ((containsMathSign(equation[b]) && equation[b] != '-') || (b != i + 1 && equation[b] == '-') || (b == equation.length - 1)) {
                            if (b == equation.length - 1) b++;
                            if (equation[i] == '*') {
                                equation = insertBetweenCharArray(equation, firstPos, b - 1, Operation.MULTIPLY.calculate(equation, i));
                            } else if (equation[i] == '/') {
                                equation = insertBetweenCharArray(equation, firstPos, b - 1, Operation.DIVIDE.calculate(equation, i));
                            }
                            i = -1;
                            break;
                        }
                    }
                }
                if (i != -1) {
                    if (equation[i] == '-') {
                        firstPos = i;
                    } else if (equation[i] != '-') {
                        firstPos = i + 1;
                    }
                } else firstPos = 0;
            }
        }

        firstPos = 0;
        for (int i = 0; i < equation.length; i++) {
            if (containsMathSign(equation[i])) {
                if ((equation[i] == '+' || equation[i] == '-') && i != 0) {
                    for (int b = i + 1; b < equation.length; b++) {
                        if ((containsMathSign(equation[b]) && equation[b] != '-') || (b != i + 1 && equation[b] == '-') || (b == equation.length - 1)) {
                            if (b == equation.length - 1) b++;
                            if (equation[i] == '+') {
                                equation = insertBetweenCharArray(equation, firstPos, b - 1, Operation.ADD.calculate(equation, i));
                            } else if (equation[i] == '-') {
                                equation = insertBetweenCharArray(equation, firstPos, b - 1, Operation.SUBTRACT.calculate(equation, i));
                            }
                            i = -1;
                            break;
                        }
                    }
                }
                if (i != -1) {
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

    private static char[] insertBetweenCharArray(char[] array, int fIndex, int lIndex, char[] arrayReplace) {
        ArrayList<Character> newArray = new ArrayList<>();

        for (int i = 0; i < fIndex; i++) newArray.add(array[i]);
        for (char ch : arrayReplace) newArray.add(ch);
        for (int i = lIndex + 1; i < array.length; i++) newArray.add(array[i]);

        return ArrayListToCharArray(newArray);
    }

    private static char[] ArrayListToCharArray(ArrayList<Character> array) {
        char[] newArray = new char[array.size()];
        for (int i = 0; i < array.size(); i++) {
            newArray[i] = array.get(i);
        }
        return newArray;
    }

    private static boolean containsMathSign(char c) {
        return (c == '+' || c == '*' || c == '/' || c == '^' || c == '-');
    }

    private static boolean isFinalAnswer(char[] equation) {
        int count = 0;
        for (char ch : equation) {
            if (ch == '-' || ch == '+' || ch == '*' || ch == '/' || ch == '^') count++;
        }
        if (count == 0) return true;
        else if (count == 1 && equation[0] == '-') return true;
        else if (count == 1 && equation[0] == '+') return true;
        return false;
    }

    private static char[] reverseArray(char[] array) {
        char[] reversedArray = new char[array.length];

        int count = 0;
        for (int i = array.length - 1; i >= 0; i--) {
            reversedArray[count] = array[i];
            count++;
        }

        return reversedArray;
    }

    public enum Operation {
        ADD,
        SUBTRACT,
        MULTIPLY,
        DIVIDE,
        POWER;

        char[] calculate(char[] equation, int index) {

            String sFNum = "";
            String sSNum = "";
            for (int i = index - 1; i >= 0; i--) {
                if (Character.isDigit(equation[i]) || equation[i] == '.') {
                    sFNum += equation[i];
                } else if (equation[i] == '-') {
                    sFNum += '-';
                    break;
                } else break;
            }

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
            }

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

