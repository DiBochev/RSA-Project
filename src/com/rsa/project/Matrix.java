package com.rsa.project;

import java.util.Arrays;
import java.util.Random;

public class Matrix {
	
	private static final double UPPER_LIMIT_FOR_GENERATION = 100;
	

	private double matrix[][];

	public Matrix(){
		//just for the test
	}
	
	public Matrix(double[][] array) {
		this.setMatrix(array);
	}

	public int getRow() {
		return this.matrix.length;
		
	}

	public int getCol() {
		return this.matrix[0].length;
	}

	public double[][] getMatrix() {
		return this.matrix;
	}

	public void setMatrix(double[][] array) {
		this.matrix = array;
	}
	
	public void setMatrixSize(int m, int n){
		this.matrix = new double[m][n];
	}

	//just for the test
	public boolean equals(Matrix m) {
		if (this == m)
			return true;
		if (m == null)
			return false;
		if (getClass() != m.getClass())
			return false;
		Matrix other = (Matrix) m;
		if (!Arrays.deepEquals(matrix, other.matrix))
			return false;
		return true;
	}
	
	public static Matrix[] generate(int m, int n, int k) throws InterruptedException{
		int[] sizes = {m, n, k};
		Random rand = new Random();
		Matrix[] matrixes = new Matrix[2];
		Thread[] threads = new Thread[2];
		initializeThreadsAndStartThem(sizes, rand, matrixes, threads);
		//waiting for threads to finish work
		for (Thread thread : threads) {
			thread.join();
		}
		return matrixes;
	}

	private static void initializeThreadsAndStartThem(int[] sizes, Random rand, Matrix[] matrixes, Thread[] threads) {
		//initialize threads for generating matrix
		for (int i = 0; i < threads.length; i++) {
			final int i2 = i;
			threads[i] = new Thread(){
				public void run(){
					matrixes[i2] = new Matrix();
					matrixes[i2].setMatrixSize(sizes[i2], sizes[i2 + 1]);
					
					for (int j = 0; j < matrixes[i2].getRow(); j++) {
						for (int l = 0; l < matrixes[i2].getCol(); l++) {
							matrixes[i2].getMatrix()[j][l] = UPPER_LIMIT_FOR_GENERATION * rand.nextDouble();
						}
					}
				}
			};
			//starting thread after initialization
			threads[i].start();
		}
	}
}