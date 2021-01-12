package com.wjh.service;

import com.wjh.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by limi on 2017/10/16.
 */
public interface TypeService {

	//保存
	Type saveType(Type type);

	//根据id获取
	Type getType(Long id);

	//根据分类名称获取
	Type getTypeByName(String name);

	//获取分类列表
	Page<Type> listType(Pageable pageable);

	List<Type> listType();

	//更新
	Type updateType(Long id, Type type);

	//删除分类
	void deleteType(Long id);

	List<Type> listTypeTop(Integer i);
}

