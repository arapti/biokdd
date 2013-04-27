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

public class Query2 {
	
	public static void main(String args[]) {
		
		String pubid;
		String directory = "C:/Users/lalou/workspace/BioRDF/fin_model";
		Dataset ds = TDBFactory.createDataset(directory);
		Model model = ds.getDefaultModel();// we load the dataset into an empty
		// model
		
		try {
			 long start = System.currentTimeMillis();
			for(int i=0; i<10; i++){
			// we make the query and specify the model in which it is made
			QueryExecution qExec = QueryExecutionFactory.create(
					"PREFIX ns:<http://www.protein_interactions.com/> "
							+ "SELECT ?pub " + "WHERE { ?s ns:PubIdentifier ?pub }"
							+ "ORDER BY RAND() LIMIT 3000", model);
			ResultSet rs = qExec.execSelect();
			while (rs.hasNext()) {
			
			QuerySolution accession = rs.next();
			
			Literal thing = accession.getLiteral("pub");
			pubid = thing.getLexicalForm();
			//System.out.println(acces+"==========");
			RDFNode prot = model.createProperty(pubid);
			//System.out.println(prot);
			QuerySolutionMap initialBindings = new QuerySolutionMap();
			initialBindings.add("pubid", thing);
			String queryString ="PREFIX ns:<http://www.protein_interactions.com/> "
					+ "SELECT ?protein ?protein2 ?type ?pub " + "WHERE {" +"?s  ns:PubIdentifier  ?pub"+"."
										+"?s ns:interacts ?protein"+"."
									+"?s ns:Interaction_type ?type"+"."
	
										+"?s ns:interacts ?protein2"+"."
										+ "FILTER (?pub=?pubid) }";
			
			Query query = QueryFactory.create(queryString);
			//execute the query over the model, providing the
			//initial bindings for all variables
			QueryExecution exec =
			QueryExecutionFactory.create(query, model, initialBindings);
			//ParameterizedSparqlString queryStr = new ParameterizedSparqlString(queryString);
			//queryStr.setLiteral("Accession", acces);
			//queryStr.setLiteral("Accession", acces);
			//Query query = QueryFactory.create(queryStr.toString());
			//Query query = QueryFactory.create(queryString);
			//execute the query over the model, providing the
			//initial bindings for all variables
			//QueryExecution exec =QueryExecutionFactory.create(query, model);
			
			ResultSet  rs2=exec.execSelect();
			
		//	while(rs2.hasNext()){
			//ResultSetFormatter.out(rs2);
				//System.out.println(rs2);
				
				
			//}
			
			}
			}
			
			long elapsedTimeMillis = (System.currentTimeMillis()-start)/10;
			  System.out.println(elapsedTimeMillis + "ms");
			model.close();
		}
		
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
}

}
