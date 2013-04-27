package com.vaannila.protein;

import java.sql.DriverManager;
import java.sql.ResultSet;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class mySQLload {

	public static double[][] getInteractionMatrix(boolean print) {
		try {

			int i;
			int j;
			Class.forName("com.mysql.jdbc.Driver");
			Connection conexion = (Connection) DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/ppi", "root", "");

			double[][] matrix = null;
			if (!conexion.isClosed()) {
				Statement st = (Statement) conexion.createStatement();

				ResultSet rs = st
						.executeQuery("select max(row_A),max(row_B) from interaction2 ");
				rs.next();
				int a = rs.getInt(1);
				int b = rs.getInt(2);
				if (a > b) {
					matrix = new double[a][a];
				} else {
					matrix = new double[b][b];
				}
				ResultSet rs2 = st
						.executeQuery("select row_A,row_B from interaction2 ");
				while (rs2.next()) {

					int x = rs2.getInt(1) - 1;
					int y = rs2.getInt(2) - 1;
					matrix[x][y] = 1;
					matrix[y][x] = 1.0;
				}
			}
			if (print == true) {
				for (i = 0; i < matrix.length; i++) {
					for (j = 0; j < matrix.length; j++) {

						System.out.print(matrix[i][j] + " ");

					}
					System.out.println(" ");

				}
			}
			return matrix;
		}

		catch (Exception e) {
			System.out.println("Exception " + e);
			e.printStackTrace();
		}
		return null;

	}

}
