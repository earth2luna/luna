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

	private long rsid;// 所属资源
	private long pid;// 父段落
	private int hc;// 处理方式
	private String pth;// 路径
	private String tt;// 标题
	private int st;// 排序
	private String cont;// 内容

	/**
	 * @return the rsid
	 */
	public long getRsid() {
		return rsid;
	}

	/**
	 * @param rsid
	 *            the rsid to set
	 */
	public void setRsid(long rsid) {
		this.rsid = rsid;
	}

	/**
	 * @return the pid
	 */
	public long getPid() {
		return pid;
	}

	/**
	 * @param pid
	 *            the pid to set
	 */
	public void setPid(long pid) {
		this.pid = pid;
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
	public int getSt() {
		return st;
	}

	/**
	 * @param st
	 *            the st to set
	 */
	public void setSt(int st) {
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
