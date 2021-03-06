package com.org.knowceans.mcl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.HashMap;
import java.util.Iterator;

import com.org.knowceans.util.Vectors;

public class SparseVector extends ConcurrentHashMap<Integer, Double> {
	private static final long serialVersionUID = 1L;

	private int length = 0;

	/**
	 * create empty vector
	 */
	public SparseVector() {
		super();
	}

	/**
	 * create empty vector with length
	 */
	public SparseVector(int i) {
		this();
		length = i;
	}

	/**
	 * create vector from dense vector
	 * 
	 * @param x
	 */
	public SparseVector(double[] x) {
		this(x.length);
		for (int i = 0; i < x.length; i++) {
			if (x[i] != 0) {
				put(i, x[i]);
			}
		}
	}

	/**
	 * copy constructor
	 * 
	 * @param v
	 */
	public SparseVector(SparseVector v) {
		super(v);
		this.length = v.length;
	}

	/**
	 * get ensures it returns 0 for empty hash values or if index exceeds
	 * length.
	 * 
	 * @param key
	 * @return val
	 */
	@Override
	public Double get(Object key) {
		Double b = super.get(key);
		if (b == null) {
			return 0.;
		}
		return b;
	}

	/**
	 * put increases the matrix size if the index exceeds the current size.
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	@Override
	public Double put(Integer key, Double value) {
		length = Math.max(length, key + 1);
		if (value == 0) {
			return remove(key);
		}
		return super.put(key, value);
	}

	/**
	 * normalises the vector to 1.
	 */
	public void normalise() {
		double invsum = 1. / sum();
		for (int i : keySet()) {
			mult(i, invsum);
		}
	}

	/**
	 * normalises the vector to newsum
	 * 
	 * @param the
	 *            value to which the element sum
	 * @return the old element sum
	 */
	public double normalise(double newsum) {
		double sum = sum();
		double invsum = newsum / sum;
		for (int i : keySet()) {
			mult(i, invsum);
		}
		return sum;
	}

	/**
	 * sum of the elements
	 * 
	 * @return
	 */
	private double sum() {
		double sum = 0;
		for (double a : values()) {
			sum += a;
		}
		return sum;
	}

	/**
	 * power sum of the elements
	 * 
	 * @return
	 */
	public double sum(double s) {
		double sum = 0;
		for (double a : values()) {
			sum += Math.pow(a, s);
		}
		return sum;
	}

	/**
	 * mutable add
	 * 
	 * @param v
	 */
	public void add(SparseVector v) {
		for (int i : keySet()) {
			add(i, v.get(i));
		}
	}

	/**
	 * mutable mult
	 * 
	 * @param i
	 *            index
	 * @param a
	 *            value
	 */
	public void mult(int i, double a) {
		Double c = get(i);
		c *= a;
		put(i, c);
	}

	/**
	 * mutable factorisation
	 * 
	 * @param a
	 */
	public void factor(double a) {
		SparseVector s = copy();
		for (int i : keySet()) {
			s.mult(i, a);
		}
	}

	/**
	 * immutable scalar product
	 * 
	 * @param v
	 * @return scalar product
	 */
	public double times(SparseVector v) {
		double sum = 0;
		for (int i : keySet()) {
			sum += get(i) * v.get(i);
		}
		return sum;
	}

	/**
	 * mutable Hadamard product (elementwise multiplication)
	 * 
	 * @param v
	 */
	public void hadamardProduct(SparseVector v) {
		for (int i : keySet()) {
			put(i, v.get(i) * get(i));
		}
	}

	/**
	 * mutable Hadamard power
	 * 
	 * @param s
	 */
	public void hadamardPower(double s) {
		for (int i : keySet()) {
			put(i, Math.pow(get(i), s));
		}
	}

	/**
	 * mutable add
	 * 
	 * @param i
	 * @param a
	 */
	public void add(int i, double a) {
		length = Math.max(length, i + 1);
		double c = get(i);
		c += a;
		put(i, c);
	}

	/**
	 * get the length of the vector
	 * 
	 * @return
	 */
	public final int getLength() {
		return length;
	}

	/**
	 * set the new length of the vector (regardless of the maximum index).
	 * 
	 * @param length
	 */
	public final void setLength(int length) {
		this.length = length;
	}

	/**
	 * copy the contents of the sparse vector
	 * 
	 * @return
	 */
	public SparseVector copy() {
		return new SparseVector(this);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i : keySet()) {
			sb.append(i).append("->").append(get(i)).append(", ");
		}
		return sb.toString();
	}

	/**
	 * create string representation of dense equivalent.
	 * 
	 * @return
	 */
	public String toStringDense() {
		return Vectors.print(getDense());
	}

	/**
	 * get dense represenation
	 * 
	 * @return
	 */
	public double[] getDense() {
		double[] a = new double[length];
		for (int i : keySet()) {
			a[i] = get(i);
		}
		return a;
	}

	/**
	 * maximum element value
	 * 
	 * @return
	 */
	public double max() {
		double max = 0;
		for (int i : keySet()) {
			max = Math.max(get(i), max);
		}
		return max;
	}

	/**
	 * exponential sum, i.e., sum (elements^p)
	 * 
	 * @param p
	 * @return
	 */
	public double expSum(int p) {
		double sum = 0;
		for (double a : values()) {
			sum += Math.pow(a, p);
		}
		return sum;
	}

	/**
	 * remove all elements whose magnitude is < threshold
	 * 
	 * @param threshold
	 */
	public void prune(double threshold) {
		for (Iterator<Integer> it = keySet().iterator(); it.hasNext();) {
			int key = it.next();
			if (Math.abs(get(key)) < threshold) {
				it.remove();
			}
		}
	}

	public static SparseVector ones(int len, double factor) {
		SparseVector x = new SparseVector();
		for (int i = 0; i < len; i++) {
			x.put2(i, factor);
		}
		return x;
	}

	public double sums() {
		double sum = 0;
		for (int i = 0; i < this.size(); i++) {
			sum = sum + this.get(i);
		}
		return sum;
	}

	public static SparseVector fill(int len, double factor) {
		SparseVector x = new SparseVector();
		for (int i = 0; i < len; i++) {

			x.put2(i, factor);
		}
		return x;
	}

	public SparseVector dotdiv() {
		double a = 0;
		for (int i = 0; i < this.size(); i++) {
			a = this.get(i);
			this.put2(i, 1.0 / a);
		}
		return this;
	}

	public static SparseVector check(SparseVector b, double scalar) {
		SparseVector a = new SparseVector();

		double v2 = 0;

		for (int i = 0; i < b.size(); i++) {

			v2 = b.get(i);
			if (v2 > scalar) {
				a.put2(i, 1.0);
			} else
				a.put2(i, 0.0);

		}
		return a;

	}

	public Double put2(Integer key, Double value) {
		length = Math.max(length, key + 1);

		return super.put(key, value);
	}

	public SparseMatrix hadamardProduct2(SparseVector v) {
		SparseMatrix v2 = new SparseMatrix();
		for (int i : this.keySet()) {
			for (int j : v.keySet()) {
				v2.set(i, j, v.get(i) * get(i));
			}
		}
		return v2;
	}

}
