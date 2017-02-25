package com.luna.mapper;

import java.util.List;
import java.util.Map;

public interface IMapper<T> {

	public long insert(T t);

	public long deleteById(Long id);

	public long delete(Map<String, Object> map);

	public long updateById(Long id);

	public long update(Map<String, Object> map);

	public T selectById(Long id);

	public List<T> selectList(Map<String, Object> map);

	public int selectCount(Map<String, Object> map);
}
