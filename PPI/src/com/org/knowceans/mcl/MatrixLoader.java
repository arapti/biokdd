package com.org.knowceans.mcl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.org.knowceans.util.Vectors;

public class MatrixLoader {

	 public static void main(String[] args) {
	        double[][] a = loadDense("m.txt");
	        System.out.println(Vectors.print(a));
	    }

	    /**
	     * read a graph with line format labelfrom labelto weight
	     *
	     * @param string
	     */
	    public static SparseMatrix loadSparse(String file) {
	        SparseMatrix matrix = new SparseMatrix();
	        try {
	            BufferedReader br = new BufferedReader(new FileReader(file));
	            String line = null;
	            while ((line = br.readLine()) != null) {
	                String[] parts = line.split(" ");
	                if (parts.length < 2) {
	                    System.out.println("Warning: wrong line format (1)");
	                    continue;
	                }
	                int from = Integer.parseInt(parts[0].trim());
	                int to = Integer.parseInt(parts[1].trim());
	                double weight = 1.;
	                if (parts.length > 2) {
	                    weight = Double.parseDouble(parts[2].trim());
	                }
	                matrix.add(from, to, weight);

	            }
	            br.close();
	            return matrix;
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	    /**
	     * read a graph with the elements of the adjacency matrix in each line.
	     *
	     * @param file
	     * @return
	     */
	    public static double[][] loadDense(String file) {
	        ArrayList<double[]> matrix = new ArrayList<double[]>();
	        try {
	            BufferedReader br = new BufferedReader(new FileReader(file));
	            String line = null;
	            while ((line = br.readLine()) != null) {
	                String[] parts = line.trim().split("[ ,] *");
	                double[] vec = new double[parts.length];

	                for (int i = 0; i < parts.length; i++) {
	                    vec[i] = Double.parseDouble(parts[i].trim());
	                }
	                matrix.add(vec);
	            }
	            br.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return matrix.toArray(new double[0][]);
	    }
	
	
}
