package com.jena.model;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.tdb.TDBFactory;

public class RDFLoad {
	public static double[][] getInteractionMatrix(boolean print) {
		String directory = "C:/Users/lalou/workspace/BioRDF/int_model";
		Dataset ds = TDBFactory.createDataset(directory);

		Model model = ds.getDefaultModel();// we load the dataset into an empty
											// model
		String ra;
		String rb;
		int row_b = 0;
		int row_a = 0;
		double[][] matrix = null;
		try {
			// we make the query and specify the model in which it is made
			QueryExecution qExec = QueryExecutionFactory.create(
					"PREFIX ns:<http://www.protein_interactions.com/> "
							+ "SELECT ?row_a " + "WHERE { ?s ns:Row_A ?row_a }"
							+ "ORDER BY DESC(?row_a) LIMIT 1", model);
			ResultSet rs = qExec.execSelect();

			while (rs.hasNext()) {

				QuerySolution row = rs.next();
				Literal thing = row.getLiteral("row_a");
				ra = thing.getLexicalForm();
				row_a = Integer.parseInt(ra);

			}

			// we make the query and specify the model in which it is made
			QueryExecution qExec2 = QueryExecutionFactory.create(
					"PREFIX ns:<http://www.protein_interactions.com/> "
							+ "SELECT ?row_b " + "WHERE { ?s ns:Row_B ?row_b }"
							+ "ORDER BY DESC(?row_b) LIMIT 1", model);

			ResultSet rs2 = qExec2.execSelect();

			while (rs2.hasNext()) {

				QuerySolution row = rs2.next();
				Literal thing = row.getLiteral("row_b");

				rb = thing.getLexicalForm();
				row_b = Integer.parseInt(rb);
				// System.out.println(row_b);

			}

			if (row_a > row_b) {
				matrix = new double[row_a][row_a];
			} else {
				matrix = new double[row_b][row_b];
			}

			QueryExecution qExec3 = QueryExecutionFactory.create(
					"PREFIX ns:<http://www.protein_interactions.com/> "
							+ "SELECT * WHERE {" + "?s ns:Row_A ?row_a" + "."
							+ "?s ns:Row_B ?row_b}", model);

			ResultSet rs3 = qExec3.execSelect();

			while (rs3.hasNext()) {
				QuerySolution row = rs3.next();
				Literal thing = row.getLiteral("row_a");
				Literal thing2 = row.getLiteral("row_b");
				String nra = thing.getLexicalForm();
				String nrb = thing2.getLexicalForm();
				
				int nrow_a = (Integer.parseInt(nra))-1;
				int nrow_b = (Integer.parseInt(nrb))-1 ;

				matrix[nrow_a][nrow_b] = 1.0;
				matrix[nrow_b][nrow_a] = 1.0;

			}
			model.close();

			if (print == true) {

				for (int i = 0; i < matrix.length; i++) {
					for (int j = 0; j < matrix.length; j++) {
						System.out.print(matrix[i][j] + " ");

					}
					System.out.println(" ");

				}
			}return matrix;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}

		return null;

	}

}
