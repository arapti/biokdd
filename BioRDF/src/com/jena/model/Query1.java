package com.jena.model;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.ParameterizedSparqlString;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.QuerySolutionMap;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.tdb.TDBFactory;

public class Query1 {

	public static void main(String args[]) {

		String acces;
		String directory = "C:/Users/lalou/workspace/BioRDF/fin_model";
		Dataset ds = TDBFactory.createDataset(directory);
		Model model = ds.getDefaultModel();// we load the dataset into an empty
		// model

		try {
			long start = System.currentTimeMillis();
			for (int i = 0; i < 5; i++) {
				long start2 = System.currentTimeMillis();
				// we make the query and specify the model in which it is made
				QueryExecution qExec = QueryExecutionFactory.create(
						"PREFIX ns:<http://www.protein_interactions.com/> "
								+ "SELECT ?proteins "
								+ "WHERE { ?proteins ns:Accession ?Accession }"
								+ "ORDER BY RAND() LIMIT 1000", model);
				ResultSet rs = qExec.execSelect();
				System.out.println("-------------------------" + i);
				//System.out.println("Q1 "+ (System.currentTimeMillis() - start2) + "ms");
				while (rs.hasNext()) {
					try {
						start2 = System.currentTimeMillis();
						QuerySolution accession = rs.next();
						System.out.print("Q1 iteration"
								+ (System.currentTimeMillis() - start2)
								+ "ms  ");

						Resource thing = accession.getResource("proteins");
						acces = thing.toString();
						// System.out.println(acces + "==========");
						RDFNode prot = model.createResource(thing);
						QuerySolutionMap initialBindings = new QuerySolutionMap();
						initialBindings.add("thing", thing);
						String queryString = "PREFIX ns:<http://www.protein_interactions.com/> "
								+ "SELECT ?protein ?protein2 ?thing ?type ?pub "
								+ "WHERE {"
								+ "?s  ns:PubIdentifier  ?pub"
								+ "."
								+ "?s ns:interacts ?protein"
								+ "."
								+ "?s ns:Interaction_type ?type"
								+ "."
								+ "?s ns:interacts ?protein"
								+ "."
								+ "?s ns:interacts ?protein2"
								+ "."
								+ "FILTER (?protein=?thing || ?protein2=?thing) }";

						Query query = QueryFactory.create(queryString);
						// execute the query over the model, providing the
						// initial bindings for all variables
						QueryExecution exec = QueryExecutionFactory.create(
								query, model, initialBindings);
						// ParameterizedSparqlString queryStr = new
						// ParameterizedSparqlString(queryString);
						// queryStr.setLiteral("Accession", acces);
						// queryStr.setLiteral("Accession", acces);
						// Query query =
						// QueryFactory.create(queryStr.toString());
						// Query query = QueryFactory.create(queryString);
						// execute the query over the model, providing the
						// initial bindings for all variables
						// QueryExecution exec
						// =QueryExecutionFactory.create(query,
						// model);
						start2 = System.currentTimeMillis();
						ResultSet rs2 = exec.execSelect();
						//System.out.println("Q2"	+ (System.currentTimeMillis() - start2) + "ms");
						// while(rs2.hasNext()){
						// ResultSetFormatter.out(rs2);
						// System.out.println(rs2);

						// }
						exec.close();
					} catch (Exception e) {
							System.out.println("ERROR");
					}
				}// while
				qExec.close();
				//System.out.println((System.currentTimeMillis() - start2) + "ms");
			}// for

			long elapsedTimeMillis = (System.currentTimeMillis() - start) / 5;
			System.out.println(elapsedTimeMillis + "ms");
		}

		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}

	}

}
