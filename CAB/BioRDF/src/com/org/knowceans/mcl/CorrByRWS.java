package com.org.knowceans.mcl;
import com.org.knowceans.util.Vectors;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

public class CorrByRWS {
	
	
	public static SparseMatrix main  (SparseMatrix a){
		int d=0;
		int k=0;
		//String file = "C:/Users/aggeliki/workspace/BioRDF/src/com/org/knowceans/mcl/m.txt";
		//if (args.length > 1)
			//file = args[0];

		//double[][] a = MatrixLoader.loadDense(file);
		
		//SparseMatrix RWS1=a;
	
		
		double result=0;
		double [][]inter=a.getDense();
		
	/*	for(int i=0; i<inter[0].length; i++){
			for(int j=0; j<inter[1].length; j++){
				System.out.print(inter[i][j]+" ");
				
			}
			System.out.print("\n");
			
		}
		*/
		PearsonsCorrelation RWScorr= new PearsonsCorrelation(inter);
		
		RealMatrix RWScorr2=RWScorr.computeCorrelationMatrix(inter);
		double[][] RWScorrf=RWScorr2.getData();
		if(Vectors.isDegenerate(RWScorrf)){
			RWScorrf = Vectors.zerosd(inter[0].length, inter[1].length);
		}
			
		
		
		
	/*	for(int i=0; i<RWScorrf[0].length; i++){
			for(int j=0; j<RWScorrf[1].length; j++){
				
				System.out.print(RWScorrf[i][j]+" ");
			}
			System.out.print("\n");
		}	
		
		
		*/
		
		SparseMatrix fin=new SparseMatrix(RWScorrf);
			RWScorrf=null;
	
		
		
	
		return fin;
		
	}
	
	
	
	
	

}
