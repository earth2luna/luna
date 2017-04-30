/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.componet;

/**
 * @author laulyl
 * @date 2017年4月30日 上午7:50:59
 * @description
 */
public class SuggetVo {

	private Long id;
	private String value;
	private String label;
	
	public SuggetVo(Long id, String value, String label) {
		super();
		this.id = id;
		this.value = value;
		this.label = label;
	}

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
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label
	 *            the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

}
