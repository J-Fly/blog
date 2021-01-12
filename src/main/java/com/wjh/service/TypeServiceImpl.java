package com.wjh.service;

import com.wjh.NotFoundException;
import com.wjh.dao.TypeRepository;
import com.wjh.po.Blog;
import com.wjh.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

//开启事务
@Transactional
@Service
public class TypeServiceImpl implements TypeService {
	@Autowired
	private TypeRepository typeRepository;

	@Override
	public Type saveType(Type type) {
		return typeRepository.save(type);
	}

	@Override
	public Type getType(Long id) {
		return typeRepository.getOne(id);
	}

	@Override
	public Type getTypeByName(String name) {
		return typeRepository.findByName(name);
	}

	@Override
	public Page<Type> listType(Pageable pageable) {
		return typeRepository.findAll(pageable);
	}

	@Override
	public List<Type> listType() {
		return typeRepository.findAll();
	}

	@Override
	public Type updateType(Long id, Type type) {
		Type t = typeRepository.getOne(id);
		if (t == null) {
			throw new NotFoundException("不存在该类型");
		}
		BeanUtils.copyProperties(type, t);

		return typeRepository.save(t);
	}

	@Override
	public void deleteType(Long id) {
		typeRepository.deleteById(id);
	}

	@Override
	public List<Type> listTypeTop(Integer i) {
		//SpringBoot 2.0 之后的用法
		Sort sort = Sort.by(Sort.Direction.DESC,"blogs.size");
		Pageable pageable = PageRequest.of(0,i,sort);
		List<Type> types = typeRepository.findTop(pageable);
		for (Type type : types) {
			List<Blog> blogs = type.getBlogs();
			//正确 可删除多个
			Iterator<Blog> iterator = blogs.iterator();
			while (iterator.hasNext()) {
				Blog b = iterator.next();
				if (b.isPublished()==false) {
					iterator.remove();//使用迭代器的删除方法删除
				}
			}
		}
		return types;
	}
}
