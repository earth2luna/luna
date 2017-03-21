/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.dto;

/**
 * @author laulyl
 * @date 2017年3月13日 下午11:20:37
 * @description
 */
public class ContentForm {

	private long key;//id
	private long rsId;// 所属资源
	private Long pId;// 父段落
	private int hc;// 处理方式
	private String pth;// 路径
	private String tt;// 标题
	private String st;// 排序
	private String cont;// 内容

	/**
	 * @return the key
	 */
	public long getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(long key) {
		this.key = key;
	}
	/**
	 * @return the rsId
	 */
	public long getRsId() {
		return rsId;
	}

	/**
	 * @param rsId the rsId to set
	 */
	public void setRsId(long rsId) {
		this.rsId = rsId;
	}

	/**
	 * @return the pId
	 */
	public Long getpId() {
		return pId;
	}

	/**
	 * @param pId the pId to set
	 */
	public void setpId(Long pId) {
		this.pId = pId;
	}

	/**
	 * @return the hc
	 */
	public int getHc() {
		return hc;
	}

	/**
	 * @param hc
	 *            the hc to set
	 */
	public void setHc(int hc) {
		this.hc = hc;
	}

	/**
	 * @return the pth
	 */
	public String getPth() {
		return pth;
	}

	/**
	 * @param pth
	 *            the pth to set
	 */
	public void setPth(String pth) {
		this.pth = pth;
	}

	/**
	 * @return the tt
	 */
	public String getTt() {
		return tt;
	}

	/**
	 * @param tt
	 *            the tt to set
	 */
	public void setTt(String tt) {
		this.tt = tt;
	}

	/**
	 * @return the st
	 */
	public String getSt() {
		return st;
	}

	/**
	 * @param st the st to set
	 */
	public void setSt(String st) {
		this.st = st;
	}

	/**
	 * @return the cont
	 */
	public String getCont() {
		return cont;
	}

	/**
	 * @param cont
	 *            the cont to set
	 */
	public void setCont(String cont) {
		this.cont = cont;
	}

}
