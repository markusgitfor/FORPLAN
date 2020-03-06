/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package forplanner;

import java.util.ArrayList;

/**
 *
 * @author markus
 */
public class Simulator {

    private Stand stand;

    public Simulator(Stand stand) {
        this.stand = stand;
    }

    public void grow5years_uneven() {
        ArrayList<Tree> lista = stand.treeList;
        for (Tree tree : lista) {

            double BALSpruce = stand.BAL_spruce(tree.d13);
            double BAL = stand.BAL(tree.d13);
            tree.n = (int) (tree.n * Models.survivalModel_Pukkala(BALSpruce, BAL, stand.G, tree.sp, tree.d13));

            double BAL_other = BAL - BALSpruce;
            double growth_d13 = Models.d13_increment_Pukkala(BALSpruce, BAL_other, stand.G, tree.d13, 2, stand.TS, tree.sp);
            tree.d13 = tree.d13 + growth_d13;

            tree.height = Models.heightModel_uneven(0, 0, 0, 1, tree.sp, tree.d13);

        }
        double N_other = stand.N_other();
        double N_spruce = stand.N_spruce();
        double ingrowthN = (Models.inGrowthModel_Pukkala(stand.G, N_spruce, N_other, 0, 2));
        double meanDI = Models.meanDIngrowth_Pukkala(stand.G, 0, 0, 2);

        double ingrowthN_2 = (Models.inGrowthModel_Pukkala(stand.G, N_spruce, N_other, 0, 1));
        double meanDI_2 = Models.meanDIngrowth_Pukkala(stand.G, 0, 0, 1);
        stand.treeList.add(new Tree(meanDI, 3, (int) ingrowthN));

    }

    public void growTree() {

    }

}
