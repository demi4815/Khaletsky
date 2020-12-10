package vsu.labs;

import org.apache.commons.math3.linear.*;

import java.util.Arrays;

import static org.apache.commons.math3.linear.MatrixUtils.createRealMatrix;

public class Main {

    public static void main(String[] args)
    {
        //Khaletsky.s = 3;
        RealMatrix A = Khaletsky.initMatrix(1);
        double[] x = Khaletsky.initX();
        double[] f = Khaletsky.calculateF(A, x);

        /*System.out.println(A);
        System.out.println(Arrays.toString(x));
        System.out.println(Arrays.toString(f));
        System.out.println();*/

        RealMatrix Weight = Khaletsky.initWeight();

        RealMatrix newA = Khaletsky.initNewA(A, Weight);

        double[] newF = Khaletsky.initNewF(A, Weight, f);
        double[][] D = Khaletsky.initD(newA);
        double[][] B = Khaletsky.initB(D);
        double[][] C = Khaletsky.initC(D);
        double[] Ysol = Khaletsky.solveY(B, newF);
        double[] Xsol = Khaletsky.solveX(C, Ysol);

        //System.out.println(Arrays.toString(Xsol));


        //Test.test1();
        Test.test2();
        //Test.test3();
        //Test.test4();
    }
}
