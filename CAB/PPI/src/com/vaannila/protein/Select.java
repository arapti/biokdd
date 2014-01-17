package com.vaannila.protein;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.DriverManager;
import java.sql.ResultSet;

import org.hibernate.cfg.AnnotationConfiguration;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

//Class for selecting random accession ids and the corresponding interactions

public class Select {

	public static void main(String[] args) throws FileNotFoundException {

		AnnotationConfiguration config = new AnnotationConfiguration();
		config.addAnnotatedClass(Protein.class);
		config.configure("hibernate.cfg.xml");

		try {
			File file = new File("C:/Users/angeliki/Desktop/order2000.txt");
			PrintStream printStream = new PrintStream(
					new FileOutputStream(file));
			System.setOut(printStream);// this turns output from console to the
										// file specified above

			Class.forName("com.mysql.jdbc.Driver");
			Connection conexion = (Connection) DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/ppi", "root", "angelikirapti");

			if (!conexion.isClosed()) {
				Statement st = (Statement) conexion.createStatement();
				// count time till algorithm convergence
				// we get current time
				int limit = 10000;

				ResultSet rs = st
						.executeQuery("select Accession from protein_pickle where Id<10000 LIMIT "
								+ limit);
				while (rs.next()) {
					String a = rs.getString(1);
					// String b=rs.getString(2);
					// System.out.println(a);
					// System.out.println(b);

					String strSQL = "Select * from interaction_matrix use index(inter1, inter2) where Id_interactor_A<=? and Id_interactor_B<=? LIMIT 10000";
					PreparedStatement pstmt = (PreparedStatement) conexion
							.prepareStatement(strSQL);
					pstmt.setString(1, a);
					pstmt.setString(2, a);
					ResultSet rs2 = pstmt.executeQuery();

					while (rs2.next()) {
						System.out.println(rs2.getString(1) + "\t"
								+ rs2.getString(2) + "\t" + rs2.getInt(5)
								+ "\t" + rs2.getInt(6));

					}
					pstmt.close();
				}

				st.close();
			}
		} catch (Exception e) {
			System.out.println("Exception " + e);
			e.printStackTrace();
		}

	}

}