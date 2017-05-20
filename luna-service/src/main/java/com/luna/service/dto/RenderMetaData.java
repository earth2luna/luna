/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.dto;

/**
 * @author laulyl
 * @date 2017年5月20日 下午6:58:58
 * @description
 */
public class RenderMetaData {

	private Long id;
	private String name;

	public RenderMetaData(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
