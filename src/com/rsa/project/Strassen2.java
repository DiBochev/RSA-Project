package com.rsa.project;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Strassen2 {
	
	private int numberOfThreads;
	private int numberOfFreeThreads;
	
	public double[][] multiplyThreading(double[][] A, double[][] B, int numberOfThreads) {
		 int n = A.length;
	        double[][] R = new double[n][n];
	        /** base case **/
	        if (n == 1){
	        	R[0][0] = A[0][0] * B[0][0];
	        }
	        else {
	        	double[][] A11 = new double[n/2][n/2];
	        	double[][] A12 = new double[n/2][n/2];
	        	double[][] A21 = new double[n/2][n/2];
	        	double[][] A22 = new double[n/2][n/2];
	        	
	        	double[][] B11 = new double[n/2][n/2];
	        	double[][] B12 = new double[n/2][n/2];
	        	double[][] B21 = new double[n/2][n/2];
	        	double[][] B22 = new double[n/2][n/2];
	 
	            /** Dividing matrix A into 4 halves **/
	            split(A, A11, 0 , 0);
	            split(A, A12, 0 , n/2);
	            split(A, A21, n/2, 0);
	            split(A, A22, n/2, n/2);
	            /** Dividing matrix B into 4 halves **/
	            split(B, B11, 0 , 0);
	            split(B, B12, 0 , n/2);
	            split(B, B21, n/2, 0);
	            split(B, B22, n/2, n/2);
	 
	            /** 
	              M1 = (A11 + A22)(B11 + B22)
	              M2 = (A21 + A22) B11
	              M3 = A11 (B12 - B22)
	              M4 = A22 (B21 - B11)
	              M5 = (A11 + A12) B22
	              M6 = (A21 - A11) (B11 + B12)
	              M7 = (A12 - A22) (B21 + B22)
	            **/
	            
	            double [][] M1 = multiplyCore(add(A11, A22), add(B11, B22));
	            double [][] M2 = multiplyCore(add(A21, A22), B11);
	            double [][] M3 = multiplyCore(A11, sub(B12, B22));
	            double [][] M4 = multiplyCore(A22, sub(B21, B11));
	            double [][] M5 = multiplyCore(add(A11, A12), B22);
	            double [][] M6 = multiplyCore(sub(A21, A11), add(B11, B12));
	            double [][] M7 = multiplyCore(sub(A12, A22), add(B21, B22));
	 
//	            ArrayList<double[][]> matrixes = new ArrayList<double[][]>();
//	            matrixes.add(add(A11, A22));
//	            matrixes.add(add(B11, B22));
//	            matrixes.add(add(A21, A22));
//	            matrixes.add(B11);
//	            matrixes.add(A11);
//	            matrixes.add(sub(B12, B22));
//	            matrixes.add(A22);
//	            matrixes.add(sub(B21, B11));
//	            matrixes.add(add(A11, A12));
//	            matrixes.add(B22);
//	            matrixes.add(sub(A21, A11));
//	            matrixes.add(add(B11, B12));
//	            matrixes.add(sub(A12, A22));
//	            matrixes.add(add(B21, B22));
//	            
//	            Thread[] threads = new Thread[8];
//	            
//	            int threadCounter = 0;
//	            
//	            for (int i = 0; i < threads.length; i++) {
//	            	final int temp = threadCounter;
//					threads[i] = new Thread(){
//						public void run(){
//							multiplyCore(matrixes.get(temp), matrixes.get(temp + 1));
//						}
//					};
//					threads[i].start();
//					threadCounter +=2;
//				}
	            
//	            ArrayList<double[][]> results = new ArrayList<double[][]>(7);
	            
	            
	            
	            /**
	              C11 = M1 + M4 - M5 + M7
	              C12 = M3 + M5
	              C21 = M2 + M4
	              C22 = M1 - M2 + M3 + M6
	            **/
	            
	            double [][] C11 = add(sub(add(M1, M4), M5), M7);
	            double [][] C12 = add(M3, M5);
	            double [][] C21 = add(M2, M4);
	            double [][] C22 = add(sub(add(M1, M3), M2), M6);
	 
	            /** join 4 halves into one result matrix **/
	            join(C11, R, 0 , 0);
	            join(C12, R, 0 , n/2);
	            join(C21, R, n/2, 0);
	            join(C22, R, n/2, n/2);
	        }
	        /** return result **/    
	        return R;
	}
	
    /** Function to multiply matrices **/
    public double[][] multiplyCore(double[][] A, double[][] B){        
        int n = A.length;
        double[][] R = new double[n][n];
        /** base case **/
        if (n == 1){
        	R[0][0] = A[0][0] * B[0][0];
        }
        else{
        	double[][] A11 = new double[n/2][n/2];
        	double[][] A12 = new double[n/2][n/2];
        	double[][] A21 = new double[n/2][n/2];
        	double[][] A22 = new double[n/2][n/2];
        	double[][] B11 = new double[n/2][n/2];
        	double[][] B12 = new double[n/2][n/2];
        	double[][] B21 = new double[n/2][n/2];
        	double[][] B22 = new double[n/2][n/2];
 
            /** Dividing matrix A into 4 halves **/
            split(A, A11, 0 , 0);
            split(A, A12, 0 , n/2);
            split(A, A21, n/2, 0);
            split(A, A22, n/2, n/2);
            /** Dividing matrix B into 4 halves **/
            split(B, B11, 0 , 0);
            split(B, B12, 0 , n/2);
            split(B, B21, n/2, 0);
            split(B, B22, n/2, n/2);
 
            /** 
              M1 = (A11 + A22)(B11 + B22)
              M2 = (A21 + A22) B11
              M3 = A11 (B12 - B22)
              M4 = A22 (B21 - B11)
              M5 = (A11 + A12) B22
              M6 = (A21 - A11) (B11 + B12)
              M7 = (A12 - A22) (B21 + B22)
            **/
 
            double [][] M1 = multiplyCore(add(A11, A22), add(B11, B22));
            double [][] M2 = multiplyCore(add(A21, A22), B11);
            double [][] M3 = multiplyCore(A11, sub(B12, B22));
            double [][] M4 = multiplyCore(A22, sub(B21, B11));
            double [][] M5 = multiplyCore(add(A11, A12), B22);
            double [][] M6 = multiplyCore(sub(A21, A11), add(B11, B12));
            double [][] M7 = multiplyCore(sub(A12, A22), add(B21, B22));
 
            /**
              C11 = M1 + M4 - M5 + M7
              C12 = M3 + M5
              C21 = M2 + M4
              C22 = M1 - M2 + M3 + M6
            **/
            double [][] C11 = add(sub(add(M1, M4), M5), M7);
            double [][] C12 = add(M3, M5);
            double [][] C21 = add(M2, M4);
            double [][] C22 = add(sub(add(M1, M3), M2), M6);
 
            /** join 4 halves into one result matrix **/
            join(C11, R, 0 , 0);
            join(C12, R, 0 , n/2);
            join(C21, R, n/2, 0);
            join(C22, R, n/2, n/2);
        }
        
        //thread finish it's work. Need to reuse it! 
        
        /** return result **/    
        return R;
    }
    /** Function to sub two matrices **/
    public double[][] sub(double[][] A, double[][] B){
        int n = A.length;
        double[][] C = new double[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                C[i][j] = A[i][j] - B[i][j];
        return C;
    }
    /** Function to add two matrices **/
    public double[][] add(double[][] m1, double[][] m4){
        int n = m1.length;
        double[][] C = new double[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                C[i][j] = m1[i][j] + m4[i][j];
        return C;
    }
    /** Function to split parent matrix into child matrices **/
    public void split(double[][] P, double[][] a11, int iB, int jB) {
        for(int i1 = 0, i2 = iB; i1 < a11.length; i1++, i2++)
            for(int j1 = 0, j2 = jB; j1 < a11.length; j1++, j2++)
                a11[i1][j1] = P[i2][j2];
    }
    /** Function to join child matrices intp parent matrix **/
    public void join(double[][] C, double[][] P, int iB, int jB){
        for(int i1 = 0, i2 = iB; i1 < C.length; i1++, i2++)
            for(int j1 = 0, j2 = jB; j1 < C.length; j1++, j2++)
                P[i2][j2] = C[i1][j1];
    }    
}