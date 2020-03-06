package forplanner;


public class FORPLANNER {

  

    public static void main(String[] args) {
        
        LP lp = new LP();
        lp.testLP();

      
        


        //Tree puu = new Tree(20, 1);
        //puu.heightModel(1, 0, 0, 0);
        // System.out.println(puu.Volume());
        // puu.survivalModel();
        // puu.inGrowthModel();
        // puu.meanDIngrowth();
        Stand kuvio = new Stand(20, 20, 1000, 1, 1200);
        // DgM, G, r/ha
        kuvio.weibull3Parameter();

        for (Tree tree : kuvio.treeList) {
            //System.out.println(tree.d13);
            //System.out.println(tree.n);
        }

        System.out.println(kuvio.standVolume());
        double a = kuvio.standVolume();

        Simulator simulaattori = new Simulator(kuvio);
        simulaattori.grow5years_uneven();

        for (Tree tree : kuvio.treeList) {
            //System.out.println(tree.height);
        }

        System.out.println(kuvio.standVolume());

        double b = kuvio.standVolume();

        System.out.println((b - a) / 5);

    }

}
