package com.jena.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.function.library.bnode;
import com.hp.hpl.jena.tdb.TDBFactory;

public class InterRDF extends Object {

	@SuppressWarnings("deprecation")
	public static void main(String args[]) throws IOException {

		try {
			File datap = new File("C:/Users/angeliki/Desktop/interactPPI.txt");
			// System.out.println(datap.getCanonicalPath());
			BufferedReader bReader = new BufferedReader(new FileReader(datap));

			String line;

			String directory = "C:/Users/angeliki/Desktop/inter_model";

			// Create an empty graph
			Model model = TDBFactory.createModel(directory);

			while (((line = bReader.readLine()) != null)) {

				/**
				 * Splitting the content of tabbed separated line
				 */
				String datavalue[] = line.split("\t", -1);
				String value1 = datavalue[0];
				String value2 = datavalue[1];
				String value3 = datavalue[2];
				String value4 = datavalue[3];
				long value5 = Integer.parseInt(datavalue[4]);
				long value6 = Integer.parseInt(datavalue[5]);

				String sPostcon = "http://www.protein_interactions.com/";
				String sRelated = "interacts";// "interactorA"; //define
												// properties
				// String sRelated1="interactorB";

				String sRelated2 = "Interaction_type";
				String sRelated3 = "PubIdentifier";
				String sRelated4 = "Row_A";
				String sRelated5 = "Row_B";

				String sRelated8 = "interaction";

				// Create the resources
				Resource protein = model.createResource(sPostcon + value1);
				Resource protein2 = model.createResource(sPostcon + value2);
				Resource interaction = model.createResource(sPostcon
						+ sRelated8);

				// Create the predicate (property)
				Property interacts = model.createProperty(sPostcon, sRelated);
				Property type = model.createProperty(sPostcon, sRelated2);
				Property pub = model.createProperty(sPostcon, sRelated3);

				Property row_a = model.createProperty(sPostcon, sRelated4);
				Property row_b = model.createProperty(sPostcon, sRelated5);
				interaction
						.addProperty(
								interacts,
								model.createResource()
										.addProperty(interacts, protein)
										.addProperty(interacts, protein2)
										.addProperty(type, value3,
												XSDDatatype.XSDstring)
										.addProperty(pub, value4,
												XSDDatatype.XSDstring)
										.addLiteral(row_a, value5)
										.addLiteral(row_b, value6));

			}
			// Print RDF/XML of model to system output
			model.write(new PrintWriter(System.out));
			model.close();

			bReader.close();

		} catch (Exception e) {
			System.out.println("Failed: " + e);
		}

	}

}
