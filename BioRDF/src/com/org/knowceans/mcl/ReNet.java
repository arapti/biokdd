package com.org.knowceans.mcl;

import java.io.PrintStream;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.org.knowceans.util.Vectors;

public class ReNet {
	public static void main(String[] args) {
		
		PrintStream filestream=null;
		String file = "C:/Users/aggeliki/workspace/BioRDF/src/com/org/knowceans/mcl/m.txt";
		
		//File fileout = new File(
          //      "/C:/Users/aggeliki/Documents/projects/output.txt");
//try {
	//PrintStream printStream = new PrintStream(new FileOutputStream(fileout));
//} catch (FileNotFoundException e) {
	// TODO Auto-generated catch block
	//e.printStackTrace();
//}
		
		if (args.length > 1)
			file = args[0];

		double[][] a = MatrixLoader.loadDense(file);
		
		
		
		double[][] a_tr=Vectors.transpose(a);
		
		
		
		double [][] net=new double[a[0].length][a[1].length];
		 
			
			net= Vectors.positive(Vectors.add(a, a_tr));
			
		
		
		
		
		int m = a[0].length; // row size
		int n = a[1].length;
		//System.out.print(n +"\n");
		double [][] inter= Vectors.eye(m,n);
		
		
		
		inter=Vectors.dminus(inter, 1);
		
		
		
		
		double [][] prod=Vectors.elemult(net, inter);
		
		
		
		double [] sum=Vectors.colsums(prod);
		
		double value=Vectors.sum(sum);
		//System.out.print(value+"\n");
		
		int edges=(int) value + n;
		System.out.print(edges+"\n");
		
		double[][] fullcorr=CorrByRWS.main(a);
		
		/*for(int z=0; z<fullcorr[0].length;z++){
			for(int j=0; j<fullcorr[1].length; j++){
				
				System.out.print(fullcorr[z][j]+" ");
				
			}	System.out.print("\n");
			
		*/
		
		
		double[] allvalue=new double[(fullcorr[0].length)*(fullcorr[0].length)];
		double []matrix=new double[(fullcorr[0].length)*(fullcorr[0].length)];
		double[][] newnet=new double[fullcorr[0].length][fullcorr[1].length];
		for(int j=0; j<fullcorr[1].length; j++){
			for(int i=0; i<fullcorr[0].length; i++){
				matrix[i]=fullcorr[i][j];
			}}
			Arrays.sort(matrix);
			//System.out.print(matrix[i]+"\n");
			//int k=j;
			for(int d=0; d<matrix.length; d++){
				allvalue[d]= matrix[d];
				
			}
			
		
		
		
		//for(int i=0; i<fullcorr[0].length; i++){
		//for(int j=0; j<fullcorr[1].length; j++){
			//newnet[i][j]=Math.rint(fullcorr[i][j]);
		//}
		//}
		//System.out.print(edges+"\n");
		double alval=allvalue[allvalue.length-edges];
		newnet= Vectors.checkmat(fullcorr, alval);
		
		
		//	newnet=fullcorr;//}
		for(int i=0; i<fullcorr[0].length;i++){
			for(int j=0; j<fullcorr[1].length; j++){
					
				System.out.print(newnet[i][j]+" ");
					
		}	System.out.print("\n");
				
				
		}
			
			
		
			
			
		
		//System.out.print("Finished!");
		
		
	
		
	}
	
	
	
}
