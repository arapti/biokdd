package com.org.knowceans.mcl;

import java.io.PrintStream;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;

import org.hibernate.cfg.AnnotationConfiguration;

import com.jena.model.RDFLoad2000;

import com.vaannila.protein.MySQLMatrix2000;
import com.vaannila.protein.Protein;
import com.org.knowceans.util.Vectors;

public class ReNet2000 {
	public static void main(String[] args) throws FileNotFoundException {
		long a = 0;
		long b = 0;
		long c = 0;

		long start = System.currentTimeMillis();
		for (int i = 0; i < 10; i++) {
			b = methodB();
			AnnotationConfiguration config = new AnnotationConfiguration();
			config.addAnnotatedClass(Protein.class);
			config.configure("hibernate.cfg.xml");
			a = methodA();
			c = methodC();

		}
		long elapsedTimeMillis = System.currentTimeMillis() - start;

	}

	public static long methodA() {

		long start = System.currentTimeMillis();

		double[][] a = MySQLMatrix2000.getInteractionMatrix(false);

		SparseMatrix a_tr = new SparseMatrix(a);
		long intime = System.currentTimeMillis() - start;

		SparseMatrix net = SparseMatrix.transposeadd(a_tr);

		net = net.positive();

		int m = a_tr.size(); // row size
		int n = a_tr.size();
		SparseMatrix fullcorr = RWSnew.main(a_tr);
		fullcorr = CorrByRWS.main(fullcorr);
		SparseMatrix inter = SparseMatrix.eye(m, n);

		inter = inter.minus(1.0);
		SparseMatrix prod = SparseMatrix.elemult(net, inter);

		double[] sum = prod.colsums();

		double value = Vectors.sum(sum);
		// System.out.print(value+"\n");

		int edges = (int) value + n;
		// System.out.print(edges+"\n");

		// fullcorr.print();

		double[][] fullcorr2 = fullcorr.getDense();

		double[] allvalue = new double[(fullcorr2[0].length)
				* (fullcorr2[0].length)];
		double[] matrix = new double[(fullcorr2[0].length)
				* (fullcorr2[0].length)];
		double[][] newnet = new double[fullcorr2[0].length][fullcorr2[1].length];
		for (int j = 0; j < fullcorr2[1].length; j++) {
			for (int i = 0; i < fullcorr2[0].length; i++) {
				matrix[i] = fullcorr2[i][j];
			}
		}
		Arrays.sort(matrix);

		for (int d = 0; d < matrix.length; d++) {
			allvalue[d] = matrix[d];

		}

		double alval = allvalue[allvalue.length - edges];
		newnet = Vectors.checkmat(fullcorr2, alval);

		long elapsedTimeMillis = System.currentTimeMillis() - start;
		System.out.println("Loading time " + intime + "ms");
		System.out.println("Processing time " + (elapsedTimeMillis - intime)
				+ "ms");

		return elapsedTimeMillis;

	}

	public static long methodB() {

		long start = System.currentTimeMillis();
		AnnotationConfiguration config = new AnnotationConfiguration();
		config.addAnnotatedClass(Protein.class);
		config.configure("hibernate.cfg.xml");

		double[][] a = MySQLMatrix2000.getInteractionMatrix(false);

		SparseMatrix a_tr = new SparseMatrix(a);
		long intime = System.currentTimeMillis() - start;

		SparseMatrix net = SparseMatrix.transposeadd(a_tr);

		net = net.positive();

		int m = a_tr.size(); // row size
		int n = a_tr.size();
		SparseMatrix fullcorr = RWSnew.main(a_tr);
		fullcorr = CorrByRWS.main(fullcorr);
		SparseMatrix inter = SparseMatrix.eye(m, n);

		inter = inter.minus(1.0);
		SparseMatrix prod = SparseMatrix.elemult(net, inter);

		double[] sum = prod.colsums();

		double value = Vectors.sum(sum);
		// System.out.print(value+"\n");

		int edges = (int) value + n;
		// System.out.print(edges+"\n");

		// fullcorr.print();

		double[][] fullcorr2 = fullcorr.getDense();

		double[] allvalue = new double[(fullcorr2[0].length)
				* (fullcorr2[0].length)];
		double[] matrix = new double[(fullcorr2[0].length)
				* (fullcorr2[0].length)];
		double[][] newnet = new double[fullcorr2[0].length][fullcorr2[1].length];
		for (int j = 0; j < fullcorr2[1].length; j++) {
			for (int i = 0; i < fullcorr2[0].length; i++) {
				matrix[i] = fullcorr2[i][j];
			}
		}
		Arrays.sort(matrix);

		for (int d = 0; d < matrix.length; d++) {
			allvalue[d] = matrix[d];

		}

		double alval = allvalue[allvalue.length - edges];
		newnet = Vectors.checkmat(fullcorr2, alval);

		long elapsedTimeMillis = System.currentTimeMillis() - start;
		System.out.println("Loading time " + intime + "ms");
		System.out.println("Processing time " + (elapsedTimeMillis - intime)
				+ "ms");

		return elapsedTimeMillis;

	}

	public static long methodC() {
		long start = System.currentTimeMillis();
		double[][] a = RDFLoad2000.getInteractionMatrix(false);

		// SparseMatrix a_tr=MySQLMatrix.getInteractionMatrix(false);
		SparseMatrix a_tr = new SparseMatrix(a);
		long intime = System.currentTimeMillis() - start;
		// a=null;
		// a_tr.print();
		SparseMatrix net = SparseMatrix.transposeadd(a_tr);

		net = net.positive();

		int m = a_tr.size(); // row size
		int n = a_tr.size();
		SparseMatrix fullcorr = RWSnew.main(a_tr);
		fullcorr = CorrByRWS.main(fullcorr);
		SparseMatrix inter = SparseMatrix.eye(m, n);

		inter = inter.minus(1.0);
		SparseMatrix prod = SparseMatrix.elemult(net, inter);

		double[] sum = prod.colsums();

		double value = Vectors.sum(sum);

		int edges = (int) value + n;
		System.out.print(edges + "\n");

		// fullcorr.print();

		double[][] fullcorr2 = fullcorr.getDense();

		double[] allvalue = new double[(fullcorr2[0].length)
				* (fullcorr2[0].length)];
		double[] matrix = new double[(fullcorr2[0].length)
				* (fullcorr2[0].length)];
		double[][] newnet = new double[fullcorr2[0].length][fullcorr2[1].length];
		for (int j = 0; j < fullcorr2[1].length; j++) {
			for (int i = 0; i < fullcorr2[0].length; i++) {
				matrix[i] = fullcorr2[i][j];
			}
		}
		Arrays.sort(matrix);

		for (int d = 0; d < matrix.length; d++) {
			allvalue[d] = matrix[d];

		}
		System.out.println(matrix.length);

		double alval = allvalue[allvalue.length - edges];
		newnet = Vectors.checkmat(fullcorr2, alval);

		long elapsedTimeMillis = System.currentTimeMillis() - start;
		System.out.println("Loading time " + intime + "ms");
		System.out.println("Processing time " + (elapsedTimeMillis - intime)
				+ "ms");
		return elapsedTimeMillis;
	}
}