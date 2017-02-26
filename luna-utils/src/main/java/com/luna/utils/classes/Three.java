/**
 * copyright@laulyl
 */
package com.luna.utils.classes;

/**
 * @author laulyl
 * 2016-5-14	
 * @description 
 */
public class Three<K,V,T> {
	private K k;
	private V v;
	private T t;

	public K getK() {
		return k;
	}

	public void setK(K k) {
		this.k = k;
	}

	public V getV() {
		return v;
	}

	public void setV(V v) {
		this.v = v;
	}

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}

	public Three(K k, V v, T t) {
		super();
		this.k = k;
		this.v = v;
		this.t = t;
	}

	
}
