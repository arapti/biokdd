package com.org.knowceans.mcl;

import java.io.FileNotFoundException;

import com.org.knowceans.util.Vectors;

public class RWSnew {

	public static SparseMatrix main(SparseMatrix b) {
	
	//public static void main(String[] args) throws FileNotFoundException {
		
		
		// String file = "C:/Users/angeliki/Desktop/m.txt";
	        
		//String file = "C:/Users/aggeliki/workspace/BioRDF/src/com/org/knowceans/mcl/m.txt";
		//if (args.length > 1)
			//file = args[0];

		//double[][] a_new = MatrixLoader.loadDense(file);
		
		SparseMatrix a=b;
		//SparseMatrix a= new SparseMatrix(a_new);
		
		int m = a.size(); // row size
		int n = a.size(); // column size
	
		
		int nodes = a.size();
		double value=a.mean();

		double val = a.mean() / nodes;
		

		double stop_value = 1 / value;
		
		double beta = stop_value / nodes;
		
		double epsilon = 1 / SparseMatrix.sum(a.colsums());

		epsilon = epsilon / value;
		SparseMatrix aa=new SparseMatrix();
		SparseMatrix eyes=SparseMatrix.eye(m, m);
		
		aa = SparseMatrix.checkmat(a, eyes);
	
		SparseVector nodev=SparseVector.ones(nodes,1);
		
		double[] bb = aa.colsums();
	
		
		bb = Vectors.dotdiv(bb);
		
		
		
		
		SparseMatrix cc = new SparseMatrix();
		SparseVector vc = new SparseVector();
		vc = SparseVector.ones(m, 1.0);
		double con=0;
		double con2=0;
		double con3=0;
		
		for (int i = 0; i < bb.length; i++) {
			for (int j = 0; j < aa.size(); j++) {
				con=vc.get(j);
				con2=aa.get(i, j);
				cc.set(i,j, bb[j]*con);
				con3=cc.get(i, j);
				cc.set(i, j,con3*con2);
				

			}
			

		}
		cc = SparseMatrix.transpose2(cc);
		
		
		
		for(int i=0; i<cc.size(); i++){
			for( int j=0; j<cc.size(); j++){
				
				double v=cc.get(i,j);
				if(Double.isNaN(v)){
					cc.set(i,j,0);
				}
			}
			
		}
		
		
		//cc.print();
		 // opws sti matlaba cc=cc'

		

		//double[][] current = Vectors.eye(m, m);
		SparseMatrix current =SparseMatrix.eye(m, m);

		SparseMatrix newcurrent = current;

		SparseVector active = SparseVector.ones(m, 1.0);

		int step = 0;
		int improve_count = 0;
		double active_nodes = 0;
		SparseVector improve = SparseVector.ones(n, 1.0);
		SparseVector onesm=SparseVector.ones(m, 1.0);
		//double[][] curr_stop = new double[active.length][];
		SparseMatrix curr_stop=new SparseMatrix();

		//double[][] sel = new double[current[0].length][current[1].length];
		//double[][] inter3 = new double[aa[0].length][aa[0].length];
		//double[][] inter2 = new double[current[0].length][current[1].length];
		//double[][] temp = new double[cc[0].length][cc[1].length];
		// //////////////////////////////loop//////////////////////////

		while (active.sums()>0) {
			System.out.print(step + " " + active.sums() + " "
					+ improve_count + "\n");

			active_nodes = active.sums();
			 //System.out.print(active_nodes);
			current = newcurrent;
			
			// curr_act=active*ones(1,m).*current;
			SparseMatrix curr_act = active.hadamardProduct2(onesm);
			curr_stop=curr_act;
			curr_act = SparseMatrix.elemult(curr_act, current);
		

			SparseMatrix min = SparseMatrix.fill(m, 1);
			
			//curr_stop=(active*ones(1,m)-1).*current;
			//curr_stop = SparseMatrix.outmult(active, SparseVector.ones(m,1.0));
			
			curr_stop = SparseMatrix.matrixminus (curr_stop,min);
			
			curr_stop = SparseMatrix.elemult(curr_stop, current);
		
			SparseMatrix inter =new SparseMatrix();
			// temp=curr_act*cc-((((current*cc>0)-(current>0))==1).*( (curr_act>0)*aa ).*beta);
			 inter = current.multi( cc);
				
			inter = inter.positive();
			

			SparseMatrix test = current.positive();

			SparseMatrix inter2 = SparseMatrix.matrixminus(inter, test);
			
			inter2=inter2.positive();

			SparseMatrix sel = inter2;
			
	/*************************************************************************************************/		

			SparseMatrix curr_act2 = curr_act.positive();
			SparseMatrix inter3= new SparseMatrix();
			 inter3 = curr_act2.multi( aa);

			inter3 = SparseMatrix.elemult(sel, inter3);

			inter3 = inter3.multisc(beta);
			SparseMatrix temp=new SparseMatrix();
			temp=curr_act.multi(cc);
		
			
			temp = SparseMatrix
					.matrixminus(temp, inter3);
			
			
			
			//temp=temp.*(temp>0);
			SparseMatrix pos = temp;
			
			pos=pos.positive();
			
			
			
			temp = SparseMatrix.elemult(pos,temp);

			
			//temp.print();
			//temp.print();

			newcurrent = temp;
			
			
			//newcurrent=newcurrent.*(newcurrent>(beta*cc));
			//stin periptwsi pou einai kainourios kombos  allazei h eksiswsi k mpainei to beta ws parametros
			SparseMatrix prod = cc.multisc(beta);
			
			prod = SparseMatrix.check(newcurrent,prod);
			
			newcurrent = SparseMatrix.elemult( newcurrent,prod );
		
			
			//newcurrent=newcurrent-(current>0)*aa.*epsilon;% stin periptwsi pou den einai kainourios kombos
			SparseMatrix current3 = current.positive();
			SparseMatrix product=new SparseMatrix();
			product = current3.multi(aa);
			product = product.multisc(epsilon);
			newcurrent = SparseMatrix.matrixminus(newcurrent, product);
			
			// newcurrent=newcurrent.*(newcurrent>0);
			SparseMatrix pos2 = newcurrent.positive();
			newcurrent = SparseMatrix.elemult(newcurrent, pos2);
			
			//newcurrent=newcurrent-curr_stop;
			newcurrent = SparseMatrix.matrixminus(newcurrent, curr_stop);
			
			
			// newcurrent=(1./sum(newcurrent'))'*ones(1,nodes).*newcurrent;%normalize
			SparseMatrix tr_new = SparseMatrix.transpose2(newcurrent);
		

			//double[] vec_current = new double[tr_new[1].length];
			SparseVector vec_current=new SparseVector();
			// vec_current=Vectors.colsums(tr_new);
			SparseVector sum = SparseVector.fill(tr_new.size(),0);
			
			
			
			
			
			double v=0;
			double v2=0;
			double v3=0;
			for (int i = 0; i < tr_new.size(); i++) {
				for (int j = 0; j < tr_new.size(); j++) {
					v=tr_new.get(i,j);
					v3=sum.get(i);
					sum.put2(i,v+v3);
					
				}
				v2=sum.get(i);
				vec_current.put2(i,v2); 

			}
			vec_current =vec_current.dotdiv();
			
			for(int i=0; i<vec_current.size(); i++){
				if (Double.isInfinite(vec_current.get(i))){
					vec_current.put2(i, 0.0);
				}
				//System.out.println(vec_current.get(i));
				
			}
			
			
			

			// System.out.print("\n ");
			//double [][] p=new double [vec_current.length][];
			 SparseMatrix prod3 = vec_current.hadamardProduct2(
					nodev);
			 
			newcurrent = SparseMatrix.elemult(prod3, newcurrent);
		
			//remove NaN values, replace with 0
			double v5=0;
			for(int i=0; i<newcurrent.size(); i++){
				for( int j=0; j<newcurrent.size(); j++){
					
					 v5=newcurrent.get(i,j);
					if(Double.isNaN(v5)){
						newcurrent.set(i,j,0);
					}
				}
				
			}
			

			
			
		
			
			
			
			
			
			
			// abs(current-newcurrent)
			SparseMatrix diff = SparseMatrix.matrixminus(current, newcurrent);
			
			// get absolute value for each matrix element
			double vd=0;
			for (int i = 0; i < diff.size(); i++) {
				for (int j = 0; j < diff.size(); j++) {
					
					vd=diff.get(i, j);
					diff.set(i,j,Math.abs(vd));

				}

			}
		
			
			
			
			
			
			// improve=sum(abs(current-newcurrent)')'
			SparseMatrix tr_diff = SparseMatrix.transpose2(diff);
		
			double v4=0;			
			SparseVector sum2 = SparseVector.fill(improve.size(),0);
			
		
			
			
			double tm=0;
			double tm2=0;
			double tm3=0;
			for (int j = 0; j < tr_diff.size(); j++) {
				for (int i = 0; i <tr_diff.size(); i++) {
					tm=tr_diff.get(i, j);
					
					tm3=sum2.get(j);
							sum2.put2(j, tm+tm3);
				}
					tm2=sum2.get(j);
				//improve.set(j,tm2 );
					improve.put2(j, tm2);
			}
			
			
			
		
			
			

			// active=(improve>stop_value)
			double c2=0;
			active = SparseVector.check(improve, stop_value);
		/*	
			for( int i=0; i< active.size(); i++){
				c2=improve.get(i);
				System.out.println(c2);
				
			}*/
		
			double v56=active.sums();
			System.out.println("active sums"+v56);
			
			
			if (active_nodes == active.sums()) {
				improve_count = improve_count + 1;
			} else
				improve_count = 0;

			step = step + 1;

		if (improve_count >1) 
			break;
				

	}

		//for (int i = 0; i < current[0].length; i++) {
			//for (int j = 0; j < current[1].length; j++) {
				//System.out.print(current[i][j] + " ");

			//}
			//System.out.print("\n");
		//}
return current;
	
	
	}
}
