package vsu.labs;

import org.apache.commons.math3.linear.*;

import java.util.Arrays;

import static org.apache.commons.math3.linear.MatrixUtils.createRealMatrix;

public class Test
{
    public static void test1()
    {
        int IER = 1;
        Khaletsky.n = 3;
        Khaletsky.s = 2;
        double[][] a = {{1, 0}, {0, 1}, {2, 1}};
        RealMatrix A = createRealMatrix(a);
        double[] f = {0, 0, 8};
        double[][] b1 = {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}};
        double[][] b2 = {{2, 0, 0}, {0, 2, 0}, {0, 0, 1}};
        double[][] b3 = {{1, 0, 0}, {0, 1, 0}, {0, 0, 2}};

        RealMatrix Weight = createRealMatrix(b3);

        RealMatrix newA = Khaletsky.initNewA(A, Weight);
        double[] newF = Khaletsky.initNewF(A, Weight, f);
        double[][] D = Khaletsky.initD(newA);
        double[][] B = Khaletsky.initB(D);
        double[][] C = Khaletsky.initC(D);
        double[] Ysol = Khaletsky.solveY(B, newF);
        double[] Xsol = Khaletsky.solveX(C, Ysol);
        System.out.println(Arrays.toString(Xsol));
        double p = Khaletsky.norm(A, Xsol, f);
        System.out.println(p);
        System.exit(IER);
    }

    public static void test2()
    {
        int IER = 2;
        double maxN = Math.pow(10, 2);
        for (int n = 10; n <= maxN; n *= 10)
        {
            for (int eps = 1; eps <= 2; eps += 1)
            {
                double abs = 0;
                double p = 0;
                for (int cnt = 0; cnt < 10; cnt++)
                {
                    Khaletsky.n = n;
                    Khaletsky.s = n - 1;

                    RealMatrix A = Khaletsky.initMatrix(eps);
                    double[] x = Khaletsky.initX();
                    double[] f = Khaletsky.calculateF(A, x);
                    RealMatrix Weight = Khaletsky.initWeight();
                    RealMatrix newA = Khaletsky.initNewA(A, Weight);
                    double[] newF = Khaletsky.initNewF(A, Weight, f);
                    double[][] D = Khaletsky.initD(newA);
                    double[][] B = Khaletsky.initB(D);
                    double[][] C = Khaletsky.initC(D);
                    double[] Ysol = Khaletsky.solveY(B, newF);
                    double[] Xsol = Khaletsky.solveX(C, Ysol);

                    abs = abs + Khaletsky.accuracy(Xsol, x) / 10;
                    p = p + Khaletsky.norm(A, Xsol, f) / 10;
                }
                System.out.println("N = " + n + ", A[i][j] from " +  -Math.pow(10, eps) + " to " + Math.pow(10, eps) + ", accuracy = " + abs + ", norm = " + p);
            }
        }
        System.exit(IER);
    }

    public static void test3()
    {
        int IER = 3;
        double maxN = Math.pow(10, 2);
        for (int n = 10; n <= maxN; n *= 10)
        {
            for (int eps = 1; eps <= 2; eps += 1)
            {
                double abs = 0;
                for (int cnt = 0; cnt < 10; cnt++)
                {
                    Khaletsky.n = n;
                    Khaletsky.s = n;

                    RealMatrix A = Khaletsky.initMatrix(eps);
                    double[] x = Khaletsky.initX();
                    double[] f = Khaletsky.calculateF(A, x);
                    double[][] D = Khaletsky.initD(A);
                    double[][] B = Khaletsky.initB(D);
                    double[][] C = Khaletsky.initC(D);
                    double[] Ysol = Khaletsky.solveY(B, f);
                    double[] Xsol = Khaletsky.solveX(C, Ysol);

                    abs = abs + Khaletsky.accuracy(Xsol, x) / 10;
                }
                System.out.println("N = " + n + ", A[i][j] from " +  -Math.pow(10, eps) + " to " + Math.pow(10, eps) + ", accuracy = " + abs);
            }
        }
        System.exit(IER);
    }

    public static void test4()
    {
        int IER = 4;
        double abs = 0;
        int eps  = 1;
        int n = 3;
        Khaletsky.n = n;
        Khaletsky.s = n;
        double[][] a = {
                {8.36679999999999, -8.83679999999999, -6.07679999999999},
                {1.33679999999999, -2.31679999999999, 8.15679999999999},
                {-9.56679999999999, 13.85679999999999, 3.73679999999999}
        };

        RealMatrix A = createRealMatrix(a);
        A.setRow(0, a[0]);
        A.setRow(1, a[1]);
        A.setRow(2, a[2]);

        //RealMatrix A = Khaletsky.initMatrix(eps);

        System.out.println(A);

        //double[] x = Khaletsky.initX();
        double[] x = {9.340561744035951617 , 9.320561744035951617 , 4.240561744035951617 };

        System.out.println("x = " + Arrays.toString(x));

        double[] f = Khaletsky.calculateF(A, x);

        System.out.println("f = " + Arrays.toString(f));

        double[][] D = Khaletsky.initD(A);

        System.out.println();
        System.out.println("D = ");
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < n; j++)
            {
                System.out.print(D[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();

        double[][] B = Khaletsky.initB(D);

        System.out.println();
        System.out.println("B = ");
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < n; j++)
            {
                System.out.print(B[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();

        double[][] C = Khaletsky.initC(D);

        System.out.println();
        System.out.println("C = ");
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < n; j++)
            {
                System.out.print(C[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();

        double[][] testA = Khaletsky.multyBC(B, C);

        System.out.println();
        System.out.println("A = ");
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < n; j++)
            {
                System.out.print(testA[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();

        double[] Ysol = Khaletsky.solveY(B, f);

        System.out.println("Ysol = " + Arrays.toString(Ysol));
        System.out.println();

        double[] Xsol = Khaletsky.solveX(C, Ysol);

        System.out.println("Xsol = " + Arrays.toString(Xsol));
        System.out.println();

        System.out.println("x = " + Arrays.toString(x));

        abs = Khaletsky.accuracy(Xsol, x) / 10;
        System.out.println("N = " + n + ", A[i][j] from " +  -Math.pow(10, eps) + " to " + Math.pow(10, eps) + ", accuracy = " + abs);

        System.exit(IER);
    }



}
