package com.rsa.project;

public final class MatrixMultiplication {

	private MatrixMultiplication() {
	}

	private static double [][] matrix;

	private static void multiplyCore(Matrix leftMatrix, Matrix rightMatrix, int threadsNumber, final int numberOfCurrentThread) throws IllegalArgumentException {
		for (int i = numberOfCurrentThread; i < leftMatrix.getRow(); i += threadsNumber) {
			for (int k = 0; k < leftMatrix.getCol(); k++) {
				for (int j = 0; j < rightMatrix.getCol(); j++) {
					matrix[i][j] += leftMatrix.getMatrix()[i][k] * rightMatrix.getMatrix()[k][j];
				}
			}
		}
	}

	public static double[][] linearMultiply(Matrix leftMatrix, Matrix rightMatrix) {
		matrix = new double[leftMatrix.getRow()][rightMatrix.getCol()]; 
		multiplyCore(leftMatrix, rightMatrix, 1, 0);
		 return matrix;
	}

	public static double[][] multiplyThreading(Matrix leftMatrix, Matrix rightMatrix, int threadsNumber) throws IllegalArgumentException {
		Thread threads[] = new Thread[threadsNumber];
		matrix = new double[leftMatrix.getRow()][rightMatrix.getCol()];

		// creating the threads
		for (int i = 0; i < threadsNumber; i++) {
			final int numberOfCurrentThread = i;
			threads[numberOfCurrentThread] = new Thread() {
				public void run() {
					multiplyCore(leftMatrix, rightMatrix, threadsNumber, numberOfCurrentThread);
				}
			};
			threads[numberOfCurrentThread].start();
		}
		// join every threads
		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.getMessage();
			}
		}
		return matrix;
	}
}

