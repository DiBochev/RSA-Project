package com.rsa.project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public final class IOFileManager {

	private IOFileManager() {
		
	}

	public static Matrix[] loadMatrix(String path) throws IOException, FileNotFoundException {
		double matrixLeft[][];
		double matrixRight[][];
		try(BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(path))));){
			String data;
			data = br.readLine();
			String[] temp = data.split("\\s+");
			matrixLeft = new double[Integer.parseInt(temp[0])][Integer.parseInt(temp[1])];
			matrixRight = new double[Integer.parseInt(temp[1])][Integer.parseInt(temp[2])];
			
			for (int i = 0; i < matrixLeft.length; i++) {
				data = br.readLine();
				temp = data.split(" ");
				for (int j = 0; j < temp.length; j++) {
					matrixLeft[i][j] = Double.parseDouble(temp[j]);
				}
			}
			for (int i = 0; i < matrixRight.length; i++) {
				data = br.readLine();
				temp = data.split(" ");
				for (int j = 0; j < temp.length; j++) {
					matrixRight[i][j] = Integer.parseInt(temp[j]);
				}
			}
		}
		
		Matrix[] matrixes = new Matrix[2];
		matrixes[0] = new Matrix(matrixLeft);
		matrixes[1] = new Matrix(matrixRight);
		
		return matrixes;
	}

	public static void saveMatrix(String path, Matrix result) throws IOException {
		try(BufferedWriter writer = new BufferedWriter( new FileWriter(path));){
			writer.write(result.getRow() + " " + result.getCol());
			writer.newLine();
			
			for (int i = 0; i < result.getRow(); i++) {
				for (int j = 0; j < result.getCol(); j++) {
					writer.write(result.getMatrix()[i][j] + " ");
				}
				writer.newLine();
			}
		    writer.flush();
		}
	}
}