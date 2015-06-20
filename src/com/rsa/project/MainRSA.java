package com.rsa.project;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class MainRSA {
	
	private static int numberOfThreads = 0;
	private static int n;
	private static int m;
	private static int k;
	
	public static void main(String[] args) {
		
		Matrix[] matrices = null;
		Matrix result = new Matrix();
		
		Options options = new Options();
		options.addOption("t", true, "threads number");
		options.addOption("tasks", true, "threadsa number");
		options.addOption("m", true, "matrix size");
		options.addOption("n", true, "matrix size");
		options.addOption("k", true, "matrix size");
		options.addOption("i", true, "file with left and right matrices");
		options.addOption("o", true, "file to save result matrix");
		options.addOption("q", false, "quiet mode");
		
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = null;
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		if (cmd.hasOption("m") && cmd.hasOption("n") && cmd.hasOption("k")) {
			try {
				m = Integer.parseInt(cmd.getOptionValue("m"));
				n = Integer.parseInt(cmd.getOptionValue("n"));
				k = Integer.parseInt(cmd.getOptionValue("k"));
				matrices = Matrix.generate(m, n, k);
				System.out.println("generate matrix");
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
				return;
			}
		}else if (cmd.hasOption("i")) {
			try {
				System.out.println("read matrix");
				matrices = IOFileManager.loadMatrix(cmd.getOptionValue("i"));
			} catch (FileNotFoundException e) {
				System.err.println(e.getMessage());
				return;
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
		
		if (cmd.hasOption("q")) {
			Form.start(m,n,k);
			return;
		}
		
		if (cmd.hasOption("t") || cmd.hasOption("tasks")) {
			if (cmd.getOptionValue("t") != null) {
				numberOfThreads = Integer.parseInt(cmd.getOptionValue("t"));
			}else{
				numberOfThreads = Integer.parseInt(cmd.getOptionValue("tasks"));
			}
		}
		
		
		try {
			matrices = Matrix.generate(n, m, k);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("start linear multi");
		double timeForOneThread = System.currentTimeMillis();
		result.setMatrix(MatrixMultiplication.linearMultiply(matrices[0], matrices[1]));
		timeForOneThread = System.currentTimeMillis() -timeForOneThread;
		System.out.println(timeForOneThread);
		
		System.out.println("start multi 2");
		double t2 = System.currentTimeMillis();
		MatrixMultiplication.multiplyThreading(matrices[0], matrices[1], 4);
		t2 = System.currentTimeMillis() - t2;
		System.out.println(t2);
		double accelerationInTime = timeForOneThread / t2;
		System.out.println(accelerationInTime);
		
		System.out.println("start multi 4");
		t2 = System.currentTimeMillis();
		MatrixMultiplication.multiplyThreading(matrices[0], matrices[1], 4);
		t2 = System.currentTimeMillis() - t2;
		System.out.println(t2);
		accelerationInTime = timeForOneThread / t2;
		System.out.println(accelerationInTime);
		
		System.out.println("start multi 6");
		 t2 = System.currentTimeMillis();
		MatrixMultiplication.multiplyThreading(matrices[0], matrices[1], 6);
		t2 = System.currentTimeMillis() - t2;
		System.out.println(t2);
		 accelerationInTime = timeForOneThread / t2;
		System.out.println(accelerationInTime);
		
		System.out.println("start multi 8");
		t2 = System.currentTimeMillis();
		MatrixMultiplication.multiplyThreading(matrices[0], matrices[1], 8);
		t2 = System.currentTimeMillis() - t2;
		System.out.println(t2);
		accelerationInTime = timeForOneThread / t2;
		System.out.println(accelerationInTime);
		
		if (cmd.hasOption("o")) {
			try {
				IOFileManager.saveMatrix(cmd.getOptionValue("o"), result);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
