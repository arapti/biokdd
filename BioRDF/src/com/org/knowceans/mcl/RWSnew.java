package com.org.knowceans.mcl;

import com.org.knowceans.util.Vectors;

public class RWSnew {

	public static double[][] main(double[][] a) {
		//String file = "C:/Users/aggeliki/workspace/BioRDF/src/com/org/knowceans/mcl/m.txt";
		//if (args.length > 1)
			//file = args[0];

		//double[][] a = MatrixLoader.loadDense(file);
		int m = a[0].length; // row size
		int n = a[1].length; // column size
		// System.out.print(m+" "+n);
		int nodes = a[1].length;

		double val = Vectors.mean(a) / nodes;

		double stop_value = 1 / (Vectors.mean(a));

		double beta = stop_value / nodes;
		// System.out.print(beta+"\n");
		// double ex = Vectors.mean(a);

		// System.out.println(ex);

		double epsilon = 1 / Vectors.sum(Vectors.colsums(a));

		epsilon = epsilon / Vectors.mean(a);

		double[][] den = Vectors.eye(m, m);
		double[][] aa = Vectors.maxarray(a, den);

		// for (int i = 0; i < m; i++) {
		// for (int j = 0; j < n; j++) {
		// System.out.print(aa[i][j] + " ");

		// }
		// System.out.println(" ");
		// }
		double[] bb = Vectors.colsums(aa);
		bb = Vectors.dotdiv(bb);
		// for(int i=0; i<bb.length; i++){
		// System.out.print(bb[i] +" ");

		// }
		double[][] cc = new double[m][n];
		double[] vc = new double[m];
		vc = Vectors.ones(m, 1.0);

		for (int i = 0; i < bb.length; i++) {
			for (int j = 0; j < aa.length; j++) {
				cc[i][j] = bb[j] * vc[j];
				cc[i][j] *= aa[i][j]; // spame to ginomeno

			}
			// System.out.print(c[i] +" ");

		}
		cc = Vectors.transpose(cc); // opws sti matlaba cc=cc'

		if (Vectors.isDegenerate(cc) == true)
			cc = Vectors.zerosd(m, n);

		double[][] current = Vectors.eye(m, m);

		double[][] newcurrent = current;

		double[] active = Vectors.ones(m, 1.0);

		int step = 0;
		int improve_count = 0;
		double active_nodes = 0;
		double[] improve = Vectors.ones(n, 1.0);
		double[][] curr_act = new double[active.length][m];
		double[][] curr_stop = new double[active.length][m];
		double[][] min = Vectors.fill(m, 1);
		double[][] sel = new double[current[0].length][current[1].length];
		double[][] inter3 = new double[aa[0].length][aa[0].length];
		double[][] inter2 = new double[current[0].length][current[1].length];
		double[][] temp = new double[cc[0].length][cc[1].length];
		// //////////////////////////////loop//////////////////////////

		while (Vectors.sum(active)>0) {
			System.out.print(step + " " + Vectors.sum(active) + " "
					+ improve_count + "\n");

			active_nodes = Vectors.sum(active);
			// System.out.print(active_nodes);
			current = newcurrent;
			// curr_act=active*ones(1,m).*current;
			curr_act = Vectors.outmult(active, Vectors.ones2(m));
			curr_act = Vectors.elemult(curr_act, current);

			
			//curr_stop=(active*ones(1,m)-1).*current;
			curr_stop = Vectors.outmult(active, Vectors.ones2(m));
			curr_stop = Vectors.matrixminus(curr_stop, min);
			curr_stop = Vectors.elemult(curr_stop, current);
			
			// temp=curr_act*cc-((((current*cc>0)-(current>0))==1).*( (curr_act>0)*aa ).*beta);
			double[][] inter = Vectors.matrixmult(current, cc);

			inter = Vectors.positive(inter);

			double[][] test = Vectors.positive(current);

			inter2 = Vectors.matrixminus(inter, test);

			inter2=Vectors.positive(inter2);

			sel = inter2;
			
			

			double[][] curr_act2 = Vectors.positive(curr_act);
			inter3 = Vectors.matrixmult(curr_act2, aa);

			inter3 = Vectors.elemult(sel, inter3);

			inter3 = Vectors.multisc2(inter3, beta);

			temp = Vectors
					.matrixminus(Vectors.matrixmult(curr_act, cc), inter3);

			
			//temp=temp.*(temp>0);
			double[][] pos = Vectors.positive(temp);
			temp = Vectors.elemult(temp, pos);

			newcurrent = temp;
			
			//newcurrent=newcurrent.*(newcurrent>(beta*cc));
			//stin periptwsi pou einai kainourios kombos  allazei h eksiswsi k mpainei to beta ws parametros
			double[][] prod = Vectors.multisc2(cc, beta);
			prod = Vectors.check(newcurrent, prod);
			newcurrent = Vectors.elemult(newcurrent, prod);
			
			//newcurrent=newcurrent-(current>0)*aa.*epsilon;% stin periptwsi pou den einai kainourios kombos
			double [][]current3 = Vectors.positive(current);
			double[][] product = Vectors.matrixmult(current3, aa);
			product = Vectors.multisc2(product, epsilon);
			newcurrent = Vectors.matrixminus(newcurrent, product);
			
			// newcurrent=newcurrent.*(newcurrent>0);
			double[][] pos2 = Vectors.positive(newcurrent);
			newcurrent = Vectors.elemult(newcurrent, pos2);
			
			//newcurrent=newcurrent-curr_stop;
			newcurrent = Vectors.matrixminus(newcurrent, curr_stop);

			// newcurrent=(1./sum(newcurrent'))'*ones(1,nodes).*newcurrent;%normalize
			double[][] tr_new = Vectors.transpose(newcurrent);

			double[] vec_current = new double[tr_new[1].length];
			// vec_current=Vectors.colsums(tr_new);
			double[] sum = Vectors.zeros(vec_current.length);
			for (int j = 0; j < tr_new[1].length; j++) {
				for (int i = 0; i < tr_new[0].length; i++) {
					sum[j] = sum[j] + tr_new[i][j];
				}
				vec_current[j] = sum[j];

			}
			vec_current = Vectors.dotdiv(vec_current);

			// System.out.print("\n ");
			double[][] prod3 = Vectors.outmult(vec_current,
					Vectors.ones2(nodes));
			newcurrent = Vectors.elemult(prod3, newcurrent);

			// abs(current-newcurrent)'
			double[][] diff = Vectors.matrixminus(current, newcurrent);

			// get absolute value for each matrix element
			for (int i = 0; i < diff[0].length; i++) {
				for (int j = 0; j < diff[1].length; j++) {
					diff[i][j] = Math.abs(diff[i][j]);

				}

			}

			// improve=sum(abs(current-newcurrent)')'
			double[][] tr_diff = Vectors.transpose(diff);
			double[] sum2 = Vectors.zeros(improve.length);
			for (int j = 0; j < tr_diff[1].length; j++) {
				for (int i = 0; i < tr_diff[0].length; i++) {
					sum2[j] = sum2[j] + tr_diff[i][j];
				}
				improve[j] = sum2[j];

			}

			// active=(improve>stop_value)
			active = Vectors.check(improve, stop_value);

			if (active_nodes == Vectors.sum(active)) {
				improve_count = improve_count + 1;
			} else
				improve_count = 0;

			step = step + 1;

			if (improve_count >500)
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
