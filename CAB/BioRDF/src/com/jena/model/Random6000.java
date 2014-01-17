package com.jena.model;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.tdb.TDBFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;

public class Random6000 extends Object {

	@SuppressWarnings("deprecation")
	public static void main(String args[]) throws IOException {

//creation of RDF model for 4000 interactions from inter4000.txt		

		try {
			File datap = new File("C:/Users/angeliki/Desktop/inter6000.txt");
			
			BufferedReader bReader = new BufferedReader(new FileReader(datap));

			String line;

			String directory = "C:/Users/angeliki/Desktop/ran_model6000";
			

			// Create an empty graph
			Model model = TDBFactory.createModel(directory);

			while (((line = bReader.readLine()) != null)) {

				/**
				 * Splitting the content of tabbed separated line
				 */
				String datavalue[] = line.split("\t", -1);
				String value1 = datavalue[0];
				String value2 = datavalue[1];
				long value3 = Integer.parseInt(datavalue[2]);
				long value4 = Integer.parseInt(datavalue[3]);

				String sPostcon = "http://www.protein_interactions.com/";
				String sRelated = "interacts";// "interactorA"; //define
												// properties
				// String sRelated1="interactorB";
				String sRelated2 = "Row_A";
				String sRelated3 = "Row_B";

				// String sRelated4="interaction";

				// Create the resources
				Resource protein = model.createResource(sPostcon + value1);
				Resource protein2 = model.createResource(sPostcon + value2);
				Resource interaction = model.createResource(sPostcon
						+ "interaction");

				// Create the predicate (property)
				Property interacts = model.createProperty(sPostcon, sRelated);
			
				Property row_a = model.createProperty(sPostcon, sRelated2);
				Property row_b = model.createProperty(sPostcon, sRelated3);
				interaction.addProperty(
						interacts,
						model.createResource().addProperty(interacts, protein)
								.addProperty(interacts, protein2)
								.addLiteral(row_a, value3)
								.addLiteral(row_b, value4));

				
			}
		
			model.close();

			bReader.close();

		} catch (Exception e) {
			System.out.println("Failed: " + e);
		}

	}

}