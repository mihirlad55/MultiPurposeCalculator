import com.sun.media.sound.InvalidFormatException;

import javax.swing.*;

import static java.lang.Double.*;
import static java.lang.Double.NaN;
import static java.lang.Double.isNaN;

/**s
 * Created by mihir on 2016-12-20.
 */
class Functions {

    static class Linear {
        static double findX(double y, double m, double b) {
            try {
                return (y - b) / m;
            }
            catch (Exception ex) {
                return 0;
            }
        }

        static double findY(double x, double m, double b) {
            return m * x + b;
        }

        static double findM(double y, double x, double b) {
            try {
                return (y - b) / x;
            }
            catch (Exception ex){
                return 0;
            }
        }

        static double findB(double y, double m, double x) {
            return y - m * x;
        }

        static double findXIntercept(double m, double b) {
            try {
                return (0 - b) / m;
            }
            catch (Exception ex){
                return 0;
            }
        }
    }

    static class Quadratic {


        private double[] x = new double[2];
        private double[] roots = new double[2];
        private double y = NaN;
        private double a = NaN;
        private double b = NaN;
        private double c = NaN;
        private double h[] = new double[2];
        private double k = NaN;


        Quadratic(double a, double b, double c) {
            this.a = a;
            this.b = b;
            this.c = c;

            roots = findRoots(a, b, c);
            x[0] = NaN;
            x[1] = NaN;
            h[0] = NaN;
            h[1] = NaN;
        }

        Quadratic(String a, String b, String c) {
            try {
                this.a = parseDouble(a);
            }
            catch (Exception ignored){ }

            try {
                this.b = parseDouble(b);
            }
            catch (Exception ignored){ }

            try {
                this.c = parseDouble(c);
            }
            catch (Exception ignored){ }

            roots = findRoots(this.a, this.b, this.c);
            x[0] = NaN;
            x[1] = NaN;
            h[0] = NaN;
            h[1] = NaN;
        }

        Quadratic() {
            x[0] = NaN;
            x[1] = NaN;
            h[0] = NaN;
            h[1] = NaN;
            roots[0] = NaN;
            roots[1] = NaN;
        }

        double getY() { return this.y; }
        double getX1() { return this.x[0]; }
        double getX2() { return this.x[1]; }
        double getRoot1() { return this.roots[0]; }
        double getRoot2() { return this.roots[1]; }
        double getA() { return this.a; }
        double getB() { return this.b; }
        double getC() { return this.c; }
        double getH1() { return this.h[0]; }
        double getH2() { return this.h[1]; }
        double getK() { return this.k; }

        void setY(double y) { this.y = y; }
        void setX(double x) { this.x[0] = x; }
        void setA(double a) { this.a = a; }
        void setB(double b) { this.b = b; }
        void setC(double c) { this.c = c; }
        void setH(double h) { this.h[0] = h; }
        void setK(double k) { this.k = k; }

        boolean setY(String y) {
            try {
                this.y = parseDouble(y);
                return true;
            }
            catch (Exception ignored){
                return false;
            }
        }

        boolean setX(String x) {
            try {
                this.x[0] = parseDouble(x);
                return true;
            }
            catch (Exception ignored){
                return false;
            }
        }

        boolean setA(String a) {
            try {
                this.a = parseDouble(a);
                return true;
            }
            catch (Exception ex){
                return false;
            }
        }

        boolean setB(String b) {
            try {
                this.b = parseDouble(b);
                return true;
            }
            catch (Exception ex){
                return false;
            }
        }

        boolean setC(String c) {
            try {
                this.c = parseDouble(c);
                return true;
            }
            catch (Exception ex){
                return false;
            }
        }

        boolean setH(String h) {
            try {
                this.h[0] = parseDouble(h);
                return true;
            }
            catch (Exception ex){
                return false;
            }
        }

        boolean setK(String k) {
            try {
                this.k = parseDouble(k);
                return true;
            }
            catch (Exception ex){
                return false;
            }
        }


        private double findYFromStandard(double x, double a, double b, double c) {
            if (!isNaN(a) && !isNaN(x) && !isNaN(c) && !isNaN(b)) {
                return a * Math.pow(x, 2) + b * x + c;
            }
            return NaN;
        }
        private double findYFromVertex(double x, double a, double h, double k) {
            if (!isNaN(x) && !isNaN(h) && !isNaN(k) && !isNaN(a)) {
                return (a * Math.pow(x + h, 2)) + k;
            }
            return NaN;
        }

        private double findA1(double y, double x, double b, double c) {
            if (!isNaN(y) && !isNaN(b) && !isNaN(x) && !isNaN(c)) {
                return (y - b * x - c) / Math.pow(x, 2);
            }
            return NaN;
        }

        private double findA2(double y, double h, double k, double x) {
            if (!isNaN(y) && !isNaN(k) && !isNaN(x) && !isNaN(h)) {
                return (y - k) / Math.pow(x - h, 2);
            }
            return NaN;
        }
        private double findB(double y, double x, double a, double c) {
            if (!isNaN(a) && !isNaN(x) && !isNaN(c) && !isNaN(y)) {
                return (y - a * Math.pow(x, 2) - c ) / x;
            }
            return NaN;
        }

        private double findB(double a, double h) {
            if (!isNaN(h) && !isNaN(a)) {
                return(2 * h * a);
            }
            return NaN;
        }

        private double findC(double a, double b, double k) {
            if (!isNaN(k) && !isNaN(b) && !isNaN(a)) {
                return k + Math.pow(b, 2) / (4 * a);
            }
            return NaN;
        }

        private double findC(double y, double x, double a, double b) {
            if (!isNaN(y) && !isNaN(x) && !isNaN(a) && !isNaN(b)) {
                //return y - a * Math.pow(x, 2) - b * x;
            }
            return NaN;
        }

        private double findK(double y, double x, double a, double h) {
            if (!isNaN(y) && !isNaN(x) && !isNaN(a) && !isNaN(h)) {
                return y - (a * Math.pow(x + h, 2));
            }
            return NaN;
        }

        private double findK(double b, double c, double h) {
            if (!isNaN(b) && !isNaN(h) && !isNaN(c)) {
                return (b * h / 2) + c;
            }
            return NaN;
        }

        private double findH(double a, double b) {
            if (!isNaN(b) && !isNaN(a)) {
                return b / (2 * a) * (-1);
            }
            return NaN;
        }

        private double[] findH(double y, double a, double x, double k) {
            double[] h = new double[2];
            h[0] = NaN;
            h[1] = NaN;
            if (!isNaN(y) && !isNaN(a) && !isNaN(x) && !isNaN(k)) {
                h = findX(y, a, 2 * a * x, k + a * Math.pow(x, 2));
            }
            return h;
        }

        private double[] findRoots(double a, double b, double c) {
            double[] roots = this.roots;

            if (!isNaN(a) && !isNaN(b) && !isNaN(c)) {
                roots[0] = ((-1) * b + Math.sqrt(Math.pow(b, 2) - 4 * a * c)) / (2 * a);
                roots[1] = ((-1) * b - Math.sqrt(Math.pow(b, 2) - 4 * a * c)) / (2 * a);
            }
            return roots;
        }

        private double[] findX(double y, double a, double b, double c) {

            double[] x = this.x;

            if (!isNaN(y) && !isNaN(a) && !isNaN(b) && !isNaN(c)) {
                x[0] = ( (-1) * b + Math.sqrt( Math.pow(b, 2) - 4 * a * (c - y)) ) / (2 * a);
                x[1] = ( (-1) * b - Math.sqrt( Math.pow(b, 2) - 4 * a * (c - y)) ) / (2 * a);
            }
            return x;
        }

        int numberOfKnownValues() {
            int count = 0;
            if (!isNaN(y)) count++;
            if (!isNaN(x[0])) count++;
            if (!isNaN(a)) count++;
            if (!isNaN(b)) count++;
            if (!isNaN(c)) count++;
            if (!isNaN(h[0])) count++;
            if (!isNaN(k)) count++;
            return count;
        }

        boolean checkForUnknowns() {
            int count = numberOfKnownValues();

            if (count >= 3) {
                for (int i = 0; i < 16; i++) {
                    if (isNaN(y)) y = findYFromStandard(x[0], a, b, c);
                    if (isNaN(y)) y = findYFromVertex(x[0], a, h[0], k);

                    if (isNaN(x[0])) x = findX(y, a, b, c);

                    if (isNaN(a)) a = findA1(y, x[0], b, c);
                    if (isNaN(a)) a = findA1(y, roots[0], b, c);
                    if (isNaN(a)) a = findA2(y, h[0], k, x[0]);
                    if (isNaN(a)) a = findA2(y, h[0], k, roots[0]);

                    if (isNaN(b)) b = findB(y, x[0], a, c);
                    if (isNaN(b)) b = findB(y, roots[0], a, c);
                    if (isNaN(b)) b = findB(a, h[0]);

                    if (isNaN(c)) c = findC(y, x[0], a, b);
                    if (isNaN(c)) c = findC(y, roots[0], a, b);
                    if (isNaN(c)) c = findC(a, b, k);

                    if (isNaN(h[0])) h[0] = findH(a, b);
                    if (isNaN(h[0])) h = findH(y, a, x[0], k);

                    if (isNaN(k)) k = findK(b, c, h[0]);
                    if (isNaN(k)) k = findK(y, x[0], a, h[0]);
                    if (isNaN(k)) k = findK(y, roots[0], a, h[0]);

                    if (isNaN(roots[0])) roots = findRoots(a, b, c);
                }
                if (isNaN(x[1])) x = findX(y, a, b, c);
            }

            return count >= 4;
        }
    }
}
