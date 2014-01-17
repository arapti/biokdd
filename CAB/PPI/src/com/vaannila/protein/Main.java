package com.vaannila.protein;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.FileNotFoundException;

import org.hibernate.HibernateException;

import org.hibernate.classic.Session;
import org.hibernate.Transaction;
import com.vaannila.util.HibernateUtil;
import java.util.List;

import java.util.Iterator;

import org.hibernate.cfg.AnnotationConfiguration;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {

		AnnotationConfiguration config = new AnnotationConfiguration();
		config.addAnnotatedClass(Protein.class);
		config.configure("hibernate.cfg.xml");

		Session session = HibernateUtil.getSessionFactory().openSession();

		Transaction transaction = null;
		// PrintStream orgStream = null;
		// PrintStream fileStream = null;

		try {

			transaction = session.beginTransaction();
			// orgStream = System.out;
			File file = new File("C:/Users/angeliki/Desktop/2000.txt");
			PrintStream printStream = new PrintStream(
					new FileOutputStream(file));
			System.setOut(printStream);// this turns output from console to the
										// file specified above

			String query = "FROM RandomInteract ";
			List list = session.createQuery(query).list();

			Iterator iterator = list.iterator();
			while (iterator.hasNext()) {
				RandomInteract2000 obj = (RandomInteract2000) iterator.next();
				System.out.println(obj.getIdinteractorA() + "\t"
						+ obj.getIdinteractorB() + "\t" + obj.getRowA() + "\t"
						+ obj.getRowB());

			}

			transaction.commit();

		} catch (HibernateException e) {

			transaction.rollback();

			e.printStackTrace();

		} finally {

			session.close();

		}

	}

}
