package com.org.knowceans.mcl;
import com.org.knowceans.util.Vectors;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

public class CorrByRWS {
	
	
	public static double[][] main  (double [][]a){
		int d=0;
		int k=0;
		//String file = "C:/Users/aggeliki/workspace/BioRDF/src/com/org/knowceans/mcl/m.txt";
		//if (args.length > 1)
			//file = args[0];

		//double[][] a = MatrixLoader.loadDense(file);
		
		double [][] RWS1=RWSnew.main(a);
		//for (int i = 0; i < RWS1[0].length; i++) {
			//for (int j = 0; j < RWS1[1].length; j++) {
				//System.out.print(RWS1[i][j] + " ");

			//}
			//System.out.print("\n");
		//}
		//double [][] RWS2=Vectors.multisc2(RWS1, 1.0);
		double result=0;
		
		PearsonsCorrelation RWScorr= new PearsonsCorrelation(RWS1);
		
		RealMatrix RWScorr2=RWScorr.computeCorrelationMatrix(RWS1);
		double[][] RWScorrf=RWScorr2.getData();
		if(Vectors.isDegenerate(RWScorrf)){
			RWScorrf = Vectors.zerosd(a[0].length, a[1].length);
		}
			
		//double []matrix=new double[RWS1[0].length];
		//double []matrix2=new double[RWS1[0].length];
		//double [][] result2=new double[RWS1[0].length][RWS1[0].length];
		for(int i=0; i<RWScorrf[0].length; i++){
			for(int j=0; j<RWScorrf[1].length; j++){
				
				System.out.print(RWScorrf[i][j]+" ");
			}
			System.out.print("\n");
		}	//result=Vectors.computeCorrelation(i,j,RWS1);
	
			//if(Vectors.isDegenerate(result)) result=0.0;
			
			
			//result2[i][j]=result; 
			//} 
		//}
		
		
			
	
		
		
	
		return RWScorrf;
		
	}
	
	
	
	
	

}
