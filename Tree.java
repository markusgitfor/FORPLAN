package forplanner;

import java.util.ArrayList;

public class Tree {

    int sp;
    int status;
    double d13; // centimeters
    double height; // meters
    double basalArea;
    double crownHeight;
    double d6;
    double sawlogProportion;
    double pulplogProportion;
    double volume;
    double age;
    double competition; //  d / Dg
    int n; // amount of trees computed by weibull

    public Tree(double d13, int sp, int n) {
        this.sp = sp;
        this.d13 = d13;
        this.height = Models.heightModel_uneven(0, 0, 0, 1, sp, d13);
        this.n = n;
    }

    double Volume() {
        //Laasasenaho 1982 volume functions, d and h as parameters
        double volume = 0;
        if (sp == 1) {
            volume = 0.036089 * Math.pow(d13, 2.01395) * Math.pow(0.99676, 2.0) * Math.pow(height, 2.07025) * Math.pow((height - 1.3), -1.07209);
        }
        if (sp == 2) {
            volume = 0.022927 * Math.pow(d13, 1.91505) * Math.pow(0.99146, d13) * Math.pow(height, 2.82541) * Math.pow((height - 1.3), -1.53547);
        }
        if (sp == 3) {
            volume = 0.011197 * Math.pow(d13, 2.10253) * Math.pow(0.986, d13) * Math.pow(height, 3.98519) * Math.pow((height - 1.3), -2.659);
        }
        this.volume = volume;
        return volume; // dm3

    }

    public double BA() {
        return Math.PI / 4 * Math.pow(this.d13, 2);
    }

}
