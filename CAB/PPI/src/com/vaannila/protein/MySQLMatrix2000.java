package com.vaannila.protein;

import com.vaannila.protein.RandomInteract2000;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;
//import org.hibernate.criterion.Order;
import org.hibernate.Transaction;

import com.vaannila.util.HibernateUtil;

import java.util.Iterator;
import java.util.List;

public class MySQLMatrix2000 {

	public static double[][] getInteractionMatrix(boolean print) {

		Session session = HibernateUtil.getSessionFactory().openSession();

		Transaction transaction = null;

		try {

			transaction = session.beginTransaction();
			double[][] matrix = null;

			int a = 0;
			int b = 0;
			// double b=0.0;

			String SQL_QUERY = "select MAX(CAST(row_a, integer))  from RandomInteract2000 ";
			Query query = session.createQuery(SQL_QUERY);
			List list = query.list();
			a = (Integer) list.get(0);

			String SQL_QUERY2 = "select MAX(CAST(row_b,integer)) from RandomInteract2000 ";
			Query query2 = session.createQuery(SQL_QUERY2);
			List list2 = query2.list();
			b = (Integer) list2.get(0);

			if (a > b) {
				matrix = new double[a][a];
			}

			else {
				matrix = new double[b][b];
			}
			// System.out.print(a);
			// System.out.print(b);
			// matrix = new double[a][a];

			String query3 = "FROM RandomInteract2000";
			List list3 = session.createQuery(query3).list();
			Iterator iterator2 = list3.iterator();
			while (iterator2.hasNext()) {
				RandomInteract2000 obj = (RandomInteract2000) iterator2.next();
				// System.out.println(obj.getIdinteractorA() + "\t"
				// + obj.getIdinteractorB() + "\t" + obj.getRowA() + "\t"
				// + obj.getRowB());

				int x = (int) (obj.getRowA() - 1);
				int y = (int) (obj.getRowB() - 1);
				// double b = obj.getVal();
				matrix[(int) (obj.getRowA() - 1)][(int) (obj.getRowB() - 1)] = 1.0;
				matrix[(int) (obj.getRowB() - 1)][(int) (obj.getRowA() - 1)] = 1.0;

			}

			if (print == true) {
				for (int i = 0; i < matrix[0].length; i++) {
					for (int j = 0; j < matrix[0].length; j++) {
						System.out.print(matrix[i][j] + " ");

					}
					System.out.print("\n");

				}
			}
			return matrix;

		} catch (HibernateException e) {

			transaction.rollback();

			e.printStackTrace();

		}

		finally {

			session.close();

		}
		return null;
	}

}
