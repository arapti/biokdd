package com.org.knowceans.mcl;

import java.util.ArrayList;
import java.util.Collections;

import com.org.knowceans.util.Vectors;

public class SparseMatrix extends ArrayList<SparseVector> {
	private static final long serialVersionUID = 1L;
	private int maxVLength;

	/**
	 * empty sparse matrix
	 */
	public SparseMatrix() {
		super();
	}

	/**
	 * empty sparse matrix with allocated number of rows
	 * 
	 * @param rows
	 * @param cols
	 */
	public SparseMatrix(int rows, int cols) {
		this();
		adjustMaxIndex(rows - 1, cols - 1);
	}

	/**
	 * create sparse matrix from full matrix
	 * 
	 * @param x
	 */
	public SparseMatrix(double[][] x) {
		this(x.length - 1, x[0].length - 1);
		for (int i = 0; i < x.length; i++) {
			SparseVector v = new SparseVector(x[i]);
			set(i, v);
		}
	}

	/**
	 * copy contructor
	 * 
	 * @param matrix
	 */
	public SparseMatrix(SparseMatrix matrix) {
		for (SparseVector s : matrix) {
			add(s.copy());
		}
	}

	/**
	 * create dense representation
	 * 
	 * @return
	 */

	public double[][] getDense() {
		double[][] aa = new double[size()][];
		for (int i = 0; i < size(); i++) {
			aa[i] = new double[maxVLength];
			for (int j : get(i).keySet()) {
				aa[i][j] = get(i).get(j);
			}
		}
		return aa;
	}

	/**
	 * set the sparse vector at index i.
	 * 
	 * @param i
	 * @param x
	 * @return the old value of the element
	 */
	public SparseVector set(int i, SparseVector x) {
		adjustMaxIndex(i, x.getLength() - 1);
		return super.set(i, x);
	}

	/**
	 * get number at index or 0. if not set. If index > size, returns 0.
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public double get(int i, int j) {
		if (i > size() - 1) {
			return 0.;
		}
		return get(i).get(j);
	}

	/**
	 * set the value at the index i,j, returning the old value or 0. Increase
	 * matrix size if index exceeds the dimension.
	 * 
	 * @param i
	 * @param j
	 * @param a
	 * @return
	 */
	public double set(int i, int j, double a) {
		adjustMaxIndex(i, j);
		double b = get(i).get(j);
		get(i).put(j, a);
		return b;
	}

	/**
	 * adjusts the size of the matrix.
	 * 
	 * @param i
	 *            index addressed
	 * @param j
	 *            index addressed
	 */
	public void adjustMaxIndex(int i, int j) {
		if (i > size() - 1) {
			increase(i);
		}
		if (j >= maxVLength) {
			maxVLength = j + 1;
			for (int row = 0; row < size(); row++) {
				get(row).setLength(maxVLength);
			}
		}
	}

	/**
	 * increase the size of the matrix with empty element SparseVectors.
	 * 
	 * @param i
	 */
	private void increase(int i) {
		addAll(Collections.nCopies(i - size() + 1, new SparseVector()));
	}

	/**
	 * get the size of the matrix
	 * 
	 * @return
	 */
	public int[] getSize() {
		return new int[] { size(), maxVLength };

	}

	/**
	 * adds a to the specified element, growing the matrix if necessary.
	 * 
	 * @param i
	 * @param j
	 * @param a
	 * @return new value
	 */
	public double add(int i, int j, double a) {
		adjustMaxIndex(i, j);
		double b = get(i, j);
		a += b;
		set(i, j, a);
		return a;
	}

	/**
	 * normalise rows to rowsum
	 * 
	 * @param rowsum
	 *            for each row
	 * @return vector of old row sums
	 */
	public SparseVector normalise(double rowsum) {
		SparseVector sums = new SparseVector();
		int i = 0;
		for (SparseVector vec : this) {
			sums.put(i, vec.normalise(rowsum));
			i++;
		}
		return sums;
	}

	/**
	 * normalise by major dimension (rows)
	 */
	public void normaliseRows() {
		for (SparseVector vec : this) {
			vec.normalise();
		}
	}

	/**
	 * normalise by minor dimension (columns), expensive.
	 */
	public void normaliseCols() {
		double[] sums = new double[maxVLength];
		for (int row = 0; row < size(); row++) {
			for (int col = 0; col < get(row).getLength(); col++) {
				sums[col] += get(row).get(col);
			}
		}
		for (int row = 0; row < size(); row++) {
			for (int col = 0; col < get(row).getLength(); col++) {
				get(row).mult(col, 1 / sums[col]);
			}
		}
	}

	/**
	 * copy the matrix and its elements
	 */
	public SparseMatrix copy() {
		return new SparseMatrix(this);
	}

	/**
	 * immutable multiply this times the vector: A * x, i.e., rowwise.
	 * 
	 * @param v
	 * @return
	 */
	public SparseVector times(SparseVector v) {
		SparseVector w = new SparseVector();
		for (int i = 0; i < size(); i++) {
			w.add(i, get(i).times(v));
		}
		return w;
	}

	/**
	 * immutable multiply the vector times this: x' * A, i.e., colwise.
	 * 
	 * @param v
	 * @return
	 */
	public SparseVector vectorTimes(SparseVector v) {
		SparseVector w = new SparseVector();
		// only the rows in A that v is nonzero
		for (int i : v.keySet()) {
			SparseVector a = get(i).copy();
			a.factor(v.get(i));
			w.add(a);
		}
		return w;
	}

	/**
	 * mutable multiply this matrix (A) with M : A * M'
	 * 
	 * @param m
	 * @return modified this
	 */
	public SparseMatrix timesTransposed(SparseMatrix m) {
		// A*M = ;( A(i,:) * M )
		for (int i = 0; i < size(); i++) {
			set(i, m.times(get(i)));
		}
		return this;
	}

	/**
	 * immutable multiply this matrix (A) with M : A * M
	 * 
	 * @param m
	 * @return matrix product
	 */
	public SparseMatrix times(SparseMatrix m) {
		SparseMatrix s = new SparseMatrix();
		for (int i = 0; i < size(); i++) {
			for (int j = 0; j < m.size(); j++) {
				for (int k : get(i).keySet()) {
					double a = m.get(k, j);
					if (a != 0.) {
						s.add(i, j, get(i, k) * a);
					}
				}
			}
		}
		return s;
	}

	/**
	 * immutable multiply matrix M with this (A) : M * A
	 * 
	 * @param m
	 * @return
	 */
	public SparseMatrix matrixTimes(SparseMatrix m) {
		return m.times(this);
	}

	/**
	 * immutable transpose.
	 * 
	 * @return
	 */
	public SparseMatrix transpose() {
		SparseMatrix s = new SparseMatrix();
		for (int i = 0; i < this.size(); i++) {
			s.set(i, getColum(i));
		}
		return s;
	}

	/**
	 * get a column of the sparse matrix (expensive).
	 * 
	 * @return
	 */
	public SparseVector getColum(int i) {
		SparseVector s = new SparseVector();
		for (int row = 0; row < size(); row++) {
			double v = get(row, i);
			if (v != 0.) {
				s.put(row, v);
			}
		}
		return s;
	}

	/**
	 * mutable Hadamard product
	 * 
	 * @param m
	 */
	public void hadamardProduct(SparseMatrix m) {
		for (int i = 0; i < size(); i++) {
			get(i).hadamardProduct(m.get(i));
		}
	}

	/**
	 * mutable m2 = m .^ s
	 * 
	 * @param s
	 * @return
	 */
	public void hadamardPower(double s) {
		for (int i = 0; i < size(); i++) {
			get(i).hadamardPower(s);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < size(); i++) {
			sb.append(i).append(" => ").append(get(i)).append("\n");
		}
		return sb.toString();
	}

	/**
	 * prints a dense representation
	 * 
	 * @return
	 */
	public String toStringDense() {
		return Vectors.print(getDense());
	}

	/**
	 * prune all values whose magnitude is below threshold
	 */
	public void prune(double threshold) {
		// for (SparseVector v : this) {
		for (int i = 0; i < size(); i++) {
			SparseVector a = get(i);
			a.prune(threshold);
		}

	}

	public SparseMatrix print() {
		SparseMatrix s = new SparseMatrix();
		for (int i = 0; i < size(); i++) {
			for (int j = 0; j < get(i).getLength(); j++) {

				double a = this.get(i, j);
				System.out.print(a + " ");

			}

			System.out.print("\n");
		}
		return s;
	}

	public double mean() {
		double[] sums = new double[this.size()];
		double num = 0;

		for (int row = 0; row < this.size(); row++) {
			for (int col = 0; col < get(row).getLength(); col++) {
				sums[col] += get(row).get(col);
			}

		}

		for (int col = 0; col < sums.length; col++) {
			num = num + sums[col];

		}
		num = num / sums.length;
		return num;

	}

	public double[] colsums() {
		double[] sums = new double[this.size()];
		for (int i = 0; i < this.size(); i++) {
			for (int col = 0; col < get(i).getLength(); col++) {
				sums[col] += get(i).get(col);
			}
		}
		return sums;
	}

	public static double sum(double[] vec) {
		double sum = 0;
		for (int i = 0; i < vec.length; i++) {
			sum += vec[i];
		}
		return sum;

	}

	public static SparseMatrix outmult(SparseVector ds, SparseVector dt) {
		double a = 0;
		double b = 0;

		// if (ds.length != dt.length)
		// throw new IllegalArgumentException("Vector dimensions must agree.");
		SparseMatrix s = new SparseMatrix();
		for (int i = 0; i < ds.size(); i++) {
			for (int j = 0; j < dt.size(); j++) {
				a = ds.get(i);
				b = dt.get(j);

				s.add(i, j, a * b);
			}

		}
		return s;
	}

	public static SparseMatrix eye(int x, int y) {
		SparseMatrix matrix = new SparseMatrix();
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {

				if (i == j) {
					matrix.set(i, j, 1);
				} else
					matrix.set(i, j, 0);
			}

		}
		return matrix;

	}

	public static SparseMatrix fill(int len, int factor) {
		SparseMatrix x = new SparseMatrix();
		for (int i = 0; i < len; i++) {
			// for(int j=0; j<len; j++){
			x.set(i, SparseVector.ones(len, factor));
		}

		return x;
	}

	public static SparseMatrix elemult(SparseMatrix a, SparseMatrix b) {
		SparseMatrix matrix = new SparseMatrix();
		double v = 0;
		double d = 0;
		for (int i = 0; i < a.size(); i++) {
			for (int j = 0; j < b.size(); j++) {
				v = a.get(i, j);
				d = b.get(i, j);

				matrix.set(i, j, v * d);
			}

		}
		return matrix;

	}

	public static SparseMatrix matrixminus(SparseMatrix a, SparseMatrix b) {
		double v1 = 0;
		double v2 = 0;
		SparseMatrix matrix = new SparseMatrix();
		for (int i = 0; i < a.size(); i++) {
			for (int j = 0; j < a.size(); j++) {
				v1 = a.get(i, j);
				v2 = b.get(i, j);
				matrix.set(i, j, v1 - v2);

			}

		}
		return matrix;
	}

	public static SparseMatrix matrixmult(SparseMatrix a, SparseMatrix b) {
		SparseMatrix matrix = new SparseMatrix();
		// for (int i = 0; i < a[0].length; i++) {
		// int i=0;
		// int k=0;
		double v1 = 0;
		double v2 = 0;
		for (int i = 0; i < a.size(); i++) {
			for (int j = 0; j < a.size(); j++) {
				for (int k = 0; k < b.size(); k++) {
					v1 = a.get(i, k);
					v2 = b.get(k, j);

					matrix.add(i, j, v1 * v2);

				}
			}
		}
		return matrix;
	}

	public SparseMatrix positive() {
		double v = 0;
		SparseMatrix a = new SparseMatrix();
		for (int i = 0; i < this.size(); i++) {
			for (int j = 0; j < this.size(); j++) {
				v = this.get(i, j);
				if (v > 0) {
					a.set(i, j, 1.0);
				} else
					a.set(i, j, 0.0);

			}
		}
		return a;
	}

	public SparseMatrix multisc(double scalar) {
		double a = 0;
		for (int i = 0; i < this.size(); i++) {
			for (int j = 0; j < this.size(); j++) {
				a = this.get(i, j);
				this.set(i, j, a * scalar);

			}
		}
		return this;
	}

	public static SparseMatrix check(SparseMatrix a, SparseMatrix b) {

		SparseMatrix matrix = new SparseMatrix();
		double v1 = 0;
		double v2 = 0;

		for (int i = 0; i < a.size(); i++) {
			for (int j = 0; j < a.size(); j++) {
				v1 = a.get(i, j);
				v2 = b.get(i, j);
				if (v1 > v2) {
					matrix.set(i, j, 1.0);
				} else
					matrix.set(i, j, 0);

			}

		}
		return matrix;

	}

	public SparseMatrix add(SparseMatrix a, SparseMatrix b) {
		SparseMatrix matrix = new SparseMatrix();
		// for (int i = 0; i < a[0].length; i++) {
		// int i=0;
		// int k=0;
		double v1 = 0;
		double v2 = 0;
		for (int i = 0; i < a.size(); i++) {
			for (int j = 0; j < a.size(); j++) {

				v1 = a.get(i, j);
				v2 = b.get(i, j);

				matrix.set(i, j, v1 + v2);

			}
		}
		return matrix;
	}

	public SparseMatrix minus(double scalar) {
		double v1 = 0;

		for (int i = 0; i < this.size(); i++) {
			for (int j = 0; j < this.size(); j++) {
				v1 = this.get(i, j);

				this.set(i, j, scalar - v1);

			}

		}
		return this;
	}

	public static SparseMatrix transpose2(SparseMatrix b) {
		SparseMatrix matrix = new SparseMatrix();
		double a = 0;
		for (int i = 0; i < b.size(); i++) {
			for (int j = 0; j < b.size(); j++) {
				a = b.get(j, i);
				matrix.set(i, j, a);

			}
		}
		return matrix;
	}

	public static SparseMatrix transposeadd(SparseMatrix b) {
		SparseMatrix matrix = new SparseMatrix();
		double a = 0;
		for (int i = 0; i < b.size(); i++) {
			for (int j = 0; j < b.size(); j++) {
				a = b.get(j, i);
				matrix.set(i, j, a);
				matrix.set(j, i, a);

			}
		}
		return matrix;
	}

	public static SparseMatrix checkmat(SparseMatrix a, SparseMatrix b) {

		SparseMatrix matrix = new SparseMatrix();
		double v1 = 0;
		double v2 = 0;

		for (int i = 0; i < a.size(); i++) {
			for (int j = 0; j < a.size(); j++) {
				v1 = a.get(i, j);
				v2 = b.get(i, j);
				if (v1 > v2) {
					matrix.set(i, j, v1);
				} else
					matrix.set(i, j, v2);

			}

		}
		return matrix;

	}

	public SparseMatrix multi(SparseMatrix m) {
		SparseMatrix s = new SparseMatrix();
		for (int i = 0; i < size(); i++) {
			for (int j = 0; j < m.size(); j++) {
				for (int k : get(i).keySet()) {
					double a = m.get(k, j);

					s.add(i, j, get(i, k) * a);

				}
			}
		}
		return s;
	}

}
