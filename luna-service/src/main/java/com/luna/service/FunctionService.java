/**
 * copyright@laulyl
 */
package com.luna.service;

/**
 * @author laulyl
 * @date 2017年7月2日 下午5:43:52
 * @desction
 */
public interface FunctionService {

	public void exportResourceData(Long ltId, Long gtId, String filePath);

	public void exportResourceContentData(Long ltId, Long gtId, String filePath);
	
	public void exportResourceCasecadeData(Long ltId, Long gtId, String filePath);
}
