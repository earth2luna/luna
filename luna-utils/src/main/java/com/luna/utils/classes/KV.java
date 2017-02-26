/**
 * copyright@laulyl
 */
package com.luna.utils.classes;

/**
 * @author laulyl 2016-5-14
 * @description
 */
public class KV<K, V> {
	private K k;
	private V v;

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

	public KV(K k, V v) {
		super();
		this.k = k;
		this.v = v;
	}

	
}
