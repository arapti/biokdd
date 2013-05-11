package com.vaannila.protein;
import java.util.HashSet;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.FileNotFoundException;

import org.hibernate.HibernateException;
import org.hibernate.classic.Session;
import org.hibernate.Transaction;
import com.vaannila.util.HibernateUtil;
import java.util.List;
import org.hibernate.SessionFactory;
import java.util.Iterator;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.cfg.AnnotationConfiguration;

//import org.hibernate.cfg.Configuration;
//import org.slf4j.Logger;  
//import org.slf4j.LoggerFactory; 


public class Main {
	//final static Logger logger = LoggerFactory.getLogger(Student.class); 
	
	
	
	public static void main(String[] args) throws FileNotFoundException {
	
		AnnotationConfiguration config = new AnnotationConfiguration();
		config.addAnnotatedClass(Protein.class);
		config.configure("hibernate.cfg.xml");
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Transaction transaction = null;
		PrintStream orgStream 	= null;
		PrintStream fileStream 	= null;
		
		try {
		
		transaction = session.beginTransaction();
		//orgStream = System.out;
		File file  = new File("/C:/Documents and Settings/Stefanos/Desktop/PPI/proteins.log");
        PrintStream printStream = new PrintStream(new FileOutputStream(file));
        System.setOut(printStream);//this turns output from console to the file specified above
		//String searchId="Q14342";
		String query="from Protein";
        List list = session.createQuery(query).list();
                    //.setString("sId", searchId).list();
        
        Iterator iterator = list.iterator();
        
        while (iterator.hasNext()) {
        Protein obj = (Protein) iterator.next();
        	System.out.println(obj.getAccession() +"\t" +obj.getEntryName()+"\t"+ obj.getStatus() + "\t" + obj.getProteinName() + "\t" + obj.getGeneNames() + "\t" + obj.getLength()+"\t" + obj.getProteinExistence()+ "\t"+obj.getDateOfModification()+"\t"+ obj.getPubmedId()+"\t"+obj.getGeneLocation()+"\t"+obj.getProteinFamily()+"\n");
        
        	
        	// while (iterator.hasNext()) {
        	  //      Interactions obj = (Interactions) iterator.next();
        	    //    	System.out.println(obj.getIdinteractorA() +"\t" +obj.getIdinteractorB()+"\t"+ obj.getInteractionType() + "\t" + obj.getPublicationIdentifier()+"\n");
        	
        	
        }	
        
		/*List students = session.createSQLQuery("select * from Student")
				.addEntity("student", Student.class)
				.list();
		System.out.println(students.size()+"rows(s) found:");
		
	    for (Iterator<Student> iterator = students.iterator(); iterator.hasNext();)

	    {

	    Student element = (Student) iterator.next();
	    logger.debug("{}", element);
	    }
		
		//Set<Phone> phoneNumbers = new HashSet<Phone>();
		
		 */
		
		transaction.commit();
		
		} catch (HibernateException e) {
		
		transaction.rollback();
		
		e.printStackTrace();
		
		} finally {
		
		session.close();
		
		}
		
		
		
		
		/*AnnotationConfiguration config = new AnnotationConfiguration();
	    config.addAnnotatedClass(Student.class);
	    config.configure();
	    new SchemaExport(config).create(true, true);*/
	    
	    
	    
	    

	    

	    

		 
		}
	
	



public void listProtein()

{

Session session = HibernateUtil.getSessionFactory().openSession();

Transaction transaction = null;

try {

transaction = session.beginTransaction();

/*List students = session.createQuery("from Student").list();

for (Iterator iterator = students.iterator(); iterator.hasNext();)

{

Student student = (Student) iterator.next();

System.out.println("name:"+student.getStudentName());


}*/

transaction.commit();

} catch (HibernateException e) {

transaction.rollback();

e.printStackTrace();

} finally {

session.close();

}

}
}

