package com.rsa.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.rsa.project.IOFileManager;
import com.rsa.project.Matrix;


public class IOManagerTest {
	
	@Test
	public void readWriteFileTest(){
		
		Matrix[] matrixes = null;
		try {
			matrixes = IOFileManager.loadMatrix("D:\\matrix.in");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		double[][] leftTest = {{1,2,3},{2,3,4},{3,4,4},{4,5,5},{5,6,2}};
		double[][] rightTest = {{6,6,3,3},{7,7,3,3},{8,4,2,2}};
		
		Matrix leftTestObject = new Matrix(leftTest);
		Matrix rightTestObject = new Matrix(rightTest);

		assertTrue(matrixes[0].equals(leftTestObject));
		assertTrue(matrixes[1].equals(rightTestObject));
	}
}
