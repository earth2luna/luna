/**
 * COPYRIGHT@LAULYL
 */
package com.luna.dao.po;

import java.io.Serializable;

/**
 * @author laulyl
 * @date 2017年5月31日 下午10:44:32
 * @description
 */
public class ResourcesCategory implements Serializable {

	private static final long serialVersionUID = -2483940940502340650L;

	private Long id;// 主键
	private String name;// 名称
	private Long parentId;// 父id
	private Integer handleCode;// 处理编码

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

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getHandleCode() {
		return handleCode;
	}

	public void setHandleCode(Integer handleCode) {
		this.handleCode = handleCode;
	}

}
