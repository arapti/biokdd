package com.org.knowceans.mcl;

import java.io.PrintStream;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.jena.model.RDFLoad;

import com.org.knowceans.util.Vectors;

public class ReNet {
	public static void main(String[] args) throws FileNotFoundException {
		
		
		

							
		
		 long start = System.currentTimeMillis();
		double[][] a =RDFLoad.getInteractionMatrix(false);
	
		// SparseMatrix a_tr=MySQLMatrix.getInteractionMatrix(false);
		SparseMatrix a_tr=new SparseMatrix(a);
		 long intime= System.currentTimeMillis()-start;
		//a=null;
		//a_tr.print();
		SparseMatrix net=SparseMatrix.transposeadd(a_tr);
	
		net=net.positive();
	
			
			
		
		
	
		
		int m = a_tr.size(); // row size
		int n = a_tr.size();
		SparseMatrix fullcorr=RWSnew.main(a_tr);
		fullcorr=CorrByRWS.main(fullcorr);
		SparseMatrix inter= SparseMatrix.eye(m,n);
		
		
		
		inter=inter.minus(1.0);
		SparseMatrix prod= SparseMatrix.elemult(net,inter);
		
		
		
		double []  sum=prod.colsums();
		
		double value=Vectors.sum(sum);
	
		
		int edges=(int) value + n;
		System.out.print(edges+"\n");
		
	
	
	//fullcorr.print();
		
	double [][]fullcorr2=fullcorr.getDense();
		
		double[] allvalue=new double[(fullcorr2[0].length)*(fullcorr2[0].length)];
		double []matrix=new double[(fullcorr2[0].length)*(fullcorr2[0].length)];
		double[][] newnet=new double[fullcorr2[0].length][fullcorr2[1].length];
		for(int j=0; j<fullcorr2[1].length; j++){
			for(int i=0; i<fullcorr2[0].length; i++){
				matrix[i]=fullcorr2[i][j];
			}}
			Arrays.sort(matrix);
		
			for(int d=0; d<matrix.length; d++){
				allvalue[d]= matrix[d];
				
			}
			System.out.println(matrix.length);
		
		
	
		double alval=allvalue[allvalue.length-edges];
		newnet= Vectors.checkmat(fullcorr2, alval);
		
		
		long elapsedTimeMillis = System.currentTimeMillis()-start;
        System.out.println("Loading time "+intime + "ms"); 
        System.out.println("Processing time "+(elapsedTimeMillis-intime )+ "ms");
		

		
			
			
		

		
		
	
		
	}
	
	
	
}
