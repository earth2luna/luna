/**
 * COPYRIGHT@LAULYL
 */
package com.luna.security;

import java.util.Set;

/**
 * @author laulyl
 * @date 2017年5月4日 下午6:19:32
 * @description
 */
public class Passport {

	private String ret;
	private Set<String> nodeDomains;

	public Passport(String ret, Set<String> nodeDomains) {
		super();
		this.ret = ret;
		this.nodeDomains = nodeDomains;
	}

	public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}

	public Set<String> getNodeDomains() {
		return nodeDomains;
	}

	public void setNodeDomains(Set<String> nodeDomains) {
		this.nodeDomains = nodeDomains;
	}

}
