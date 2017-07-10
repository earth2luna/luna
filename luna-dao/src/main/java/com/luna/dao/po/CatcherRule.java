/**
 * copyright@laulyl
 */
package com.luna.dao.po;

import java.io.Serializable;

/**
 * @author laulyl
 * @date 2017年7月10日 上午12:07:16
 * @desction
 */
public class CatcherRule implements Serializable {

	private static final long serialVersionUID = 388860077186368690L;

	private Long id;

	private String content;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

}
