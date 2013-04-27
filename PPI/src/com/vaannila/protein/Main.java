package com.vaannila.protein;

import java.util.ArrayList;
import java.sql.ResultSet;
import java.util.HashSet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.FileNotFoundException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.hibernate.Transaction;
import com.vaannila.util.HibernateUtil;
import java.util.List;
import org.hibernate.SessionFactory;
import java.util.Iterator;
import org.hibernate.SQLQuery;

import org.hibernate.mapping.Map;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.cfg.AnnotationConfiguration;
import java.sql.Connection;
import java.sql.Statement;

public class Main {

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

			transaction = session.beginTransaction();
			// orgStream = System.out;
			File file = new File(
					"/C:/Users/lalou/Documents/eclipse_test/interactRDF2.log");
			PrintStream printStream = new PrintStream(
					new FileOutputStream(file));
			System.setOut(printStream);// this turns output from console to the
										// file specified above

			String query = "FROM Interactions ";
			List list = session.createQuery(query).list();

			Iterator iterator = list.iterator();
			while (iterator.hasNext()) {
				Interactions obj = (Interactions) iterator.next();
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

	public void listProtein()

	{

		Session session = HibernateUtil.getSessionFactory().openSession();

		Transaction transaction = null;

		try {

			transaction = session.beginTransaction();

			transaction.commit();

		} catch (HibernateException e) {

			transaction.rollback();

			e.printStackTrace();

		} finally {

			session.close();

		}

	}
}
