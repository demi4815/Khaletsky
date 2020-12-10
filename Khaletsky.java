package vsu.labs;

import org.apache.commons.math3.linear.*;
import java.util.ArrayList;

import static org.apache.commons.math3.linear.MatrixUtils.createRealMatrix;

public class Khaletsky
{
    static int n = 3, s = 2;
    /*RealMatrix A = initMatrix(1);
    double[] x = initX();
    double[] f = calculateF(A, x);
    RealMatrix Weight = initWeight();
    RealMatrix newA = initNewA(A, Weight);
    double[] newF = initNewF(A, Weight, f);
    double[][] D = initD(newA);
    double[][] B = initB(D);
    double[][] C = initC(D);
    double[] Ysol = solveY(B, newF);
    double[] Xsol = solveX(C, Ysol);*/

    public static RealMatrix initMatrix(int eps)
    {
        double[][] A1 = new double[n][s];

        for(int i = 0; i < n; i++)
        {
            for (int j = 0; j < s; j++)
            {
                A1[i][j] = Math.random() * 2 * Math.pow(10, eps) - Math.pow(10, eps);
            }

        }

        RealMatrix A2 = createRealMatrix(A1);
        return A2;
    }

    public static double[] initX()
    {
        double[] x = new double[s];

        for(int i = 0; i < s; i++)
        {
            x[i] = Math.random() * 10;
        }

        return x;
    }

    public static double[] calculateF(RealMatrix A, double[] x)
    {
        double[] f = new double[n];

        for(int i = 0; i < n; i++)
        {
            for (int j = 0; j < s; j++)
            {
                f[i] = f[i] + A.getEntry(i, j) * x[j];
            }
        }

        return f;
    }

    public static RealMatrix initWeight()
    {
        double[][] B1 = new double[n][n];

        for(int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                if(i == j)
                {
                    B1[i][j] = (int) (Math.random() * 10) + 1;
                   // B1[i][j] = 1;
                }
                else
                {
                    B1[i][j] = 0;
                }
            }

        }

        RealMatrix B2 = createRealMatrix(B1);
        return B2;
    }

    public static RealMatrix initNewA(RealMatrix A, RealMatrix Weight)
    {
        RealMatrix At = A.transpose();
        RealMatrix A1 = At.multiply(Weight);
        return A1.multiply(A);
    }

    public static double[] initNewF(RealMatrix A, RealMatrix Weight, double[] f)
    {
        double[] newF = new double[s];
        RealMatrix At = A.transpose();
        RealMatrix A1 = At.multiply(Weight);

        for(int i = 0; i < s; i++)
        {
            for (int j = 0; j < n; j++)
            {
                newF[i] = f[j] * A1.getEntry(i, j);
            }
        }

        return newF;
    }

    public static double[][] initD(RealMatrix A)
    {
        double[][] D = new double[s][s];
        int j = 0;
        int i;
        double sum;
        int count = 0;

        while (count < s - 1)
        {
            for (i = j; i < s; i++)
            {
                sum = 0;
                for (int k = 0; k <= j - 1; k++)
                {
                    sum = sum + D[i][k] * D[k][j];
                }
                D[i][j] = A.getEntry(i, j) - sum;
            }

            i = j;

            for (j = i + 1; j < s; j++)
            {
                sum = 0;
                for (int k = 0; k <= i - 1; k++)
                {
                    sum = sum + D[i][k] * D[k][j];
                }
                D[i][j] = (A.getEntry(i, j) - sum)/D[i][i];
            }

            j = i + 1;

            count++;
        }

        sum = 0;

        for (int k = 0; k <= s - 2; k++)
        {
            sum = sum + D[s - 1][k] * D[k][s - 1];
        }

        D[s - 1][s - 1] = A.getEntry(s - 1, s - 1) - sum;

        return D;
    }

    public static double[][] initB(double[][] D)
    {
        double[][] B = new double[s][s];

        for(int i = 0; i < s; i++)
        {
            for (int j = 0; j < s; j++)
            {
                if(j > i)
                {
                    B[i][j] = 0;
                }
                else
                {
                    B[i][j] = D[i][j];
                }

            }
        }

        return B;
    }

    public static double[][] initC(double[][] D)
    {
        double[][] C = new double[s][s];

        for(int i = 0; i < s; i++)
        {
            for (int j = 0; j < s; j++)
            {
                if(j < i)
                {
                    C[i][j] = 0;
                }
                else if (i == j)
                {
                    C[i][j] = 1;
                }
                else
                {
                    C[i][j] = D[i][j];
                }
            }
        }

        return C;
    }

    public static double[][] multyBC(double[][] B,double[][] C)
    {
        double[][] A = new double[s][s];
        double sum = 0;
        for(int i = 0; i < s; i++)
        {
            for (int j = 0; j < s; j++)
            {
                for(int k = 0 ; k < s; k++)
                {
                    sum = sum + B[i][k] * C[k][j];
                }
                A[i][j] = sum;
                sum = 0;
            }
        }

        return A;
    }

    public static double[] solveY(double[][] B, double[] f)
    {
        double[] Ysol = new double[s];
        double sum;
        for(int i = 0; i < s; i++)
        {
            sum = 0;
            for (int k = 0; k <= i - 1; k++)// было -2
            {
                sum = sum + B[i][k] * Ysol[k];
            }
            Ysol[i] = (f[i] - sum)/B[i][i];
        }

        return Ysol;
    }

    public static double[] solveX(double[][] C, double[] Ysol)
    {
        double[] Xsol = new double[s];
        double sum;
        /*for(int i = s - 1; i >= 0; i--)
        {
            sum = 0;
            for (int k = i; k <= s - 1; k++)
            {
                sum = sum + C[i][k] * Xsol[k];
            }
            Xsol[i] = Ysol[i] - sum;
        }*/
        for(int i = s - 1; i >= 0; i--)
        {
            sum = 0;
            for (int k = i + 1; k <= s - 1; k++)
            {
                sum = sum + C[i][k] * Xsol[k];
            }
            Xsol[i] = Ysol[i] - sum;
        }

        return Xsol;
    }

    public static double accuracy(double[] Xsol, double[] x)
    {
        double delta = -1;

        for (int i = 0; i < s; i++)
        {
            double q = 0.01;

            if (Math.abs(x[i]) > q)
            {
                delta = Math.max(Math.abs((Xsol[i] - x[i]) / x[i]), delta);
            }
            else
            {
                delta = Math.max(Math.abs(Xsol[i] - x[i]), delta);
            }
        }

        return delta;
    }
    public static double norm(RealMatrix A, double[] Xsol, double[] f)
    {
        double[] r = new double[s];
        double p = 0;

        for(int i = 0; i < s; i++)
        {
            r[i] = 0;
            for (int j = 0; j < s; j++)
            {
                r[i] = r[i] + A.getEntry(i, j) * Xsol[j];
            }
            r[i] = r[i] - f[i];
            p = p + Math.pow(r[i], 2);
        }

        p = Math.sqrt(p);
        return p;
    }





}
