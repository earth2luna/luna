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

	private String ticket;
	private Set<String> nodeDomains;

	public Passport(String ticket, Set<String> nodeDomains) {
		super();
		this.ticket = ticket;
		this.nodeDomains = nodeDomains;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public Set<String> getNodeDomains() {
		return nodeDomains;
	}

	public void setNodeDomains(Set<String> nodeDomains) {
		this.nodeDomains = nodeDomains;
	}

}
