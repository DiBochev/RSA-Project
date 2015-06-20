package com.rsa.test;

import static org.junit.Assert.assertArrayEquals;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import com.rsa.project.Matrix;
import com.rsa.project.MatrixMultiplication;
//import com.rsa.project.Strassen;

public class multiplierTest {


	private static Matrix simpleTestMatrix1;
	private static Matrix simpleTestMatrix2;
	private static Matrix resultMatrix;
	private static double[][] left = {{ 3, 2, 3,2 },{ 5, 9, 8,3 },{ 5, 9, 8,4 },{ 5, 9, 8,5 }};
	private static double right[][] = {{ 4, 7, 8,5 },{ 9, 3, 8,5 },{ 8, 1, 8,5 },{ 8, 1, 8,5 }};
	private static double expectedResult[][] = {{70, 32, 80, 50}, {189, 73, 200, 125}, {197, 74, 208, 130}, {205, 75, 216, 135}};
	private static Matrix testResult;

	@Test
	public void linearMultiplyTest() {
		testResult.setMatrix(expectedResult);
		simpleTestMatrix1.setMatrix(left);
		simpleTestMatrix2.setMatrix(right);
		resultMatrix.setMatrix(MatrixMultiplication.linearMultiply(simpleTestMatrix1, simpleTestMatrix2));
		assertArrayEquals(resultMatrix.getMatrix(), testResult.getMatrix());
	}
	
	
	
	@Test
	public void multiplyMorThreadsThanRows(){
		testResult.setMatrix(expectedResult);
		simpleTestMatrix1.setMatrix(left);
		simpleTestMatrix2.setMatrix(right);
		resultMatrix.setMatrix(MatrixMultiplication.multiplyThreading(simpleTestMatrix1, simpleTestMatrix2, 10));
		assertArrayEquals(resultMatrix.getMatrix(), testResult.getMatrix());
	}
	
	@After
	public void resetResult(){
		testResult.setMatrix(null);
		
	}
	
	@BeforeClass
	public static void InitializeMatrix(){
		simpleTestMatrix1 = new Matrix();
		simpleTestMatrix2 = new Matrix();
		resultMatrix = new Matrix();
		simpleTestMatrix1.setMatrix(left);
		simpleTestMatrix2.setMatrix(right);
		resultMatrix.setMatrix(expectedResult);
		testResult = new Matrix();
	}
	
}
