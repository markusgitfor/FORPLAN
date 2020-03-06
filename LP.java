/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package forplanner;
import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;
/**
 *
 * @author markus
 */
public class LP {
    
    
    static {
        System.loadLibrary("jniortools");
    }
    
    public void testLP(){
          MPSolver solver = new MPSolver(
                "SimpleMipProgram", MPSolver.OptimizationProblemType.GLOP_LINEAR_PROGRAMMING);

        double inf = java.lang.Double.POSITIVE_INFINITY;
        // x and y are integer non-negative variables.
        double stand_1_area = 3.0;
        double stand_2_area = 4.0;
        MPVariable x11 = solver.makeIntVar(0.0, stand_1_area, "x11"); // Eri käsittelyvaihtoehdot ja niiden pinta-alat
        MPVariable x21 = solver.makeIntVar(0.0, stand_1_area, "x21");
        MPVariable x31 = solver.makeIntVar(0.0, stand_1_area, "x31");
        MPVariable x12 = solver.makeIntVar(0.0, stand_2_area, "x12");
        MPVariable x22 = solver.makeIntVar(0.0, stand_2_area, "x22");
        MPVariable x32 = solver.makeIntVar(0.0, stand_2_area, "x32");

        System.out.println("Muuttujien lkm = " + solver.numVariables());

        MPConstraint ct = solver.makeConstraint(700, inf, "ct"); // Lopputilavuudet eri käsittelyillä
        ct.setCoefficient(x11, 240);
        ct.setCoefficient(x21, 195);
        ct.setCoefficient(x31, 10);
        ct.setCoefficient(x12, 260);
        ct.setCoefficient(x22, 200);
        ct.setCoefficient(x32, 5);

        MPConstraint ct2 = solver.makeConstraint(0.0, 3, "ct2"); // Pinta-alarajoite, kuvio 1 
        ct2.setCoefficient(x11, 1);
        ct2.setCoefficient(x21, 1);
        ct2.setCoefficient(x31, 1);

        MPConstraint ct3 = solver.makeConstraint(0.0, 4, "ct3"); // Pinta-alarajoite, kuvio 2
        ct3.setCoefficient(x12, 1);
        ct3.setCoefficient(x22, 1);
        ct3.setCoefficient(x32, 1);

        System.out.println("Rajoitteiden lkm = " + solver.numConstraints());

        MPObjective ca = solver.objective(); // Maksimoi poistuman määrää, m3
        ca.setCoefficient(x11, 0);
        ca.setCoefficient(x21, 55);
        ca.setCoefficient(x31, 200);
        ca.setCoefficient(x12, 0);
        ca.setCoefficient(x22, 65);
        ca.setCoefficient(x32, 230);
        ca.setMaximization();

        solver.solve();
        System.out.println("Ratkaisu:");
        System.out.println("Kuvioiden lopputilavuus = " + ca.value());
        System.out.println("x21, kuvio 1, harvennus, pinta-ala = " + x21.solutionValue());
        System.out.println("x22, kuvio 2, harvennus, pinta-ala = " + x22.solutionValue());
        System.out.println("x32, kuvio 2 avohakkuu, pinta-ala = " + x32.solutionValue());

    }

    
    
}
