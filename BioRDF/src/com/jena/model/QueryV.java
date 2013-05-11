package com.jena.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolutionMap;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.shared.Lock;
import com.hp.hpl.jena.tdb.TDBFactory;

public class QueryV {
	public static String directory = "C:/Users/angeliki/Desktop/workspace/BioRDF/fin_model";

	public static void main(String args[]) {
		String acces;

		Dataset ds = TDBFactory.createDataset(directory);
		Model model = ds.getDefaultModel();
		try {
			long start = System.currentTimeMillis();
			List<String> rowValues = new ArrayList<String>();
			try {
				model.enterCriticalSection(Lock.READ);
				QueryExecution qExec = QueryExecutionFactory
						.create("PREFIX ns:<http://www.protein_interactions.com/> "
								+ "CONSTRUCT { ?proteins  ns:Accession ?Accession } "
								+ "WHERE { ?proteins ns:Accession ?Accession }"
								+ " ORDER BY RAND() LIMIT 1000 ", model);
 				Iterator<Triple> rs = qExec.execConstructTriples();
 				while (rs.hasNext()) {
					try {
						Triple t = rs.next().asTriple();
						acces = t.getObject().getLiteralValue().toString();
						//System.out.println(t.getObject().toString());
						rowValues.add(acces);
					} catch (Exception e) {
						System.out.println("ERROR");
					}
				}// while
				qExec.close();
				model.close();
				for (String protein : rowValues) {
					queryC(protein);
				}// for2
			} finally {
				model.leaveCriticalSection();
			}
			// }// for
			long elapsedTimeMillis = (System.currentTimeMillis() - start) / 5;
			System.out.println(elapsedTimeMillis + "ms");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}

	}

	public static void queryC(String protein) {
		try {
			Dataset ds = TDBFactory.createDataset(directory);
			Model model = ds.getDefaultModel();
			protein="http://www.protein_interactions.com/"+protein;
			//System.out.println(protein);
 
			String queryString = "PREFIX ns:<http://www.protein_interactions.com/> "
					+ "PREFIX xsd:<http://www.w3.org/2001/XMLSchema#>  "
					+ "Select ?protein ?protein2 ?pub ?type  " 
					+ "WHERE {"
					+ "?s  ns:PubIdentifier  ?pub" 	+ "."
					+ "?s ns:Interaction_type ?type" + "."
					+ "?s ns:interacts ?protein" 	+ "."
					+ "?s ns:interacts ?protein2" 	+ "."
					+ "FILTER  ( str(?protein)=\""+protein+"\"^^xsd:string || str(?protein2)=\""+protein+"\"^^xsd:string  ) "  
							
					+		" }";
			Query query = QueryFactory.create(queryString);
			QueryExecution exec = QueryExecutionFactory.create(query, model);
			ResultSet rs2=exec.execSelect();
			/*if (rs2.hasNext())
			 	try {
					ResultSetFormatter.out(rs2);
				} catch (Exception e) {
					System.out.println("ERROR");
				}*/
			 
			exec.close();
			model.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}
