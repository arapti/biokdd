package com.vaannila.protein;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.classic.Session;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import com.vaannila.util.HibernateUtil;

public class Query1 {
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws FileNotFoundException {

		AnnotationConfiguration config = new AnnotationConfiguration();
		config.addAnnotatedClass(Protein.class);
		config.configure("hibernate.cfg.xml");

		Session session = HibernateUtil.getSessionFactory().openSession();

		Transaction transaction = null;
		PrintStream orgStream = null;
		PrintStream fileStream = null;

		try {
			
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection conexion = (Connection) DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/ppi", "root", "");
            
			
			
			ArrayList <String> rowValues= new ArrayList<String>();
			double[][] matrix = null;
			if (!conexion.isClosed()) {
				Statement st = (Statement) conexion.createStatement();
				  //count time till algorithm convergence
		        //we get current time
				 long start = System.currentTimeMillis();
		      for(int i=0; i<10; i++){  
		       
				ResultSet rs = st
						.executeQuery("select Accession from protein ORDER BY RAND() LIMIT 3000");
				while(rs.next()){
				String a=rs.getString(1);
				//String b=rs.getString(2);
				//System.out.println(a);
				//System.out.println(b);
				
				
				String strSQL= "Select * from interaction2 use index(inter1, inter2) where Id_interactor_A=? or Id_interactor_B=?";
				PreparedStatement pstmt = (PreparedStatement) conexion.prepareStatement(strSQL);
				pstmt.setString(1,a );
				pstmt.setString(2,a );
			    ResultSet rs2=pstmt.executeQuery();
			    
		      
				//while (rs2.next()) {
				//System.out.println(rs2.getString(1)+"\t"+rs2.getString(2)+"\t"+rs2.getString(3)+"\t"+rs2.getInt(4));
				
					
				//}
				}
				
		      }
			long elapsedTimeMillis = (System.currentTimeMillis()-start)/10;
				  System.out.println(elapsedTimeMillis + "ms");
				
				
			
			

	}

			

	}
		catch (Exception e) {
			System.out.println("Exception " + e);
			e.printStackTrace();
		}
	
	
	
	
	}
	

}
