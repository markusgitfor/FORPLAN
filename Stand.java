package forplanner;

import java.util.ArrayList;
import java.util.HashMap;

public class Stand {

    int TS;
    int Age;
    int SC;
    double G;
    double DG;
    double HG;
    double Hdom;
    double DGM;
    double N;
    ArrayList<Tree> treeList;
    

    public Stand(double DGM, double G, double N, int SC, int TS) {
        this.treeList = new ArrayList<>();
        this.DGM = DGM;
        this.G = G;
        this.N = N;
        this.SC = SC;
        this.TS = TS;

    }

    public void weibull3Parameter() {
        double paramA = Math.exp(-1.3065 + 1.1544 * Math.log(DGM));
        double paramC = Math.exp(0.6479 + 0.0255 * DGM - 0.0056 * G);
        double paramB = (DGM - paramA) / Math.pow((-Math.log(0.5)), (1 / paramC));

        double d13;
        double lowerLimit = Math.ceil(paramA) - 1;
        double upperLimit = lowerLimit + 2;
        double Fx = 0;

        HashMap<Double, Double> dbFx = new HashMap<>(); //centimeter as key and the value is F(x) integral function

        while (true) {
            d13 = (lowerLimit + upperLimit) / 2;
            Fx = 1 - Math.exp(-Math.pow(((d13 - paramA) / paramB), paramC));
            dbFx.put(d13, Fx);
            lowerLimit++;
            upperLimit++;
            if (Fx == 1) {
                break;
            }

        }
        for (double i = (Math.ceil(paramA) - 1); i < upperLimit - 1; i++) {
            double a = i + 1;
            double b = i + 3.0;
            double dd = (a + b) / 2;
            double integral = dbFx.get(b) - dbFx.get(a);

            int NN = (int) (integral * N);
            if (NN > 0) {
                treeList.add(new Tree(dd, 1, NN));
            }
            if (NN < 1) {
                break;
            }

            i++;

        }

    }

    public double BAL(double d13) {

        double BAL = 0;

        for (int i = 0; i < treeList.size(); i++) {
            double basal = treeList.get(i).BA();
            if (treeList.get(i).d13 > d13) {
                BAL += basal;

            }

        }
        return BAL / 1000;

    }

    public double BAL_spruce(double d13) {

        double BAL = 0;

        for (int i = 0; i < treeList.size(); i++) {
            double basal = treeList.get(i).BA();
            if (treeList.get(i).d13 > d13 && treeList.get(i).sp == 2) {
                BAL += basal;

            }

        }
        return BAL;

    }

    public double N_spruce() {
        double res = 0;
        for (Tree tree : treeList) {
            if (tree.sp == 2 && tree.d13 > 5) {
                res += tree.n;
            }

        }
        return res;
    }

    public double N_other() {
        double res = 0;
        for (Tree tree : treeList) {
            if (tree.sp != 2 && tree.d13 > 5) {
                res += tree.n;
            }

        }
        return res;
    }

    double standVolume() {
        double res = 0;
        for (Tree tree : treeList) {
            res += (tree.Volume() / 1000) * tree.n;

        }
        return res; // m3
    }

}
