/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luna.dao.mapper.IResourcesCategoryMapper;
import com.luna.service.data.utils.CategoryUtils;
import com.luna.utils.node.INode;

/**
 * @author laulyl
 * @date 2017年6月1日 下午11:26:19
 * @description
 */
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private IResourcesCategoryMapper categoryMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.service.CategoryService#getCategories()
	 */
	@Override
	public List<INode> getCategories() {
		return CategoryUtils.getCategories(categoryMapper);
	}

}
