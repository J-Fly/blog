package com.wjh.service;


import com.wjh.NotFoundException;
import com.wjh.dao.TagRepository;
import com.wjh.po.Blog;
import com.wjh.po.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

	@Autowired
	private TagRepository tagRepository;

	@Transactional
	@Override
	public Tag saveTag(Tag tag) {
		return tagRepository.save(tag);
	}

	@Transactional
	@Override
	public Tag getTag(Long id) {
		return tagRepository.getOne(id);
	}

	@Override
	public Tag getTagByName(String name) {
		return tagRepository.findByName(name);
	}

	@Transactional
	@Override
	public Page<Tag> listTag(Pageable pageable) {
		return tagRepository.findAll(pageable);
	}


	@Transactional
	@Override
	public Tag updateTag(Long id, Tag tag) {
		Tag t = tagRepository.getOne(id);
		if (t == null) {
			throw new NotFoundException("不存在该标签");
		}
		BeanUtils.copyProperties(tag, t);
		return tagRepository.save(t);
	}


	private List<Long> convertToList(String ids) {
		List<Long> list = new ArrayList<>();
		if (!"".equals(ids) && ids != null) {
			String[] idarray = ids.split(",");
			for (int i = 0; i < idarray.length; i++) {
				list.add(new Long(idarray[i]));
			}
		}
		return list;
	}
	@Override
	public List<Tag> listTag(String ids) { //1,2,3
		return tagRepository.findAllById(convertToList(ids));
	}

	@Transactional
	@Override
	public void deleteTag(Long id) {
		tagRepository.deleteById(id);
	}

	@Override
	public List<Tag> listTag() {
		return tagRepository.findAll();
	}

	@Override
	public List<Tag> listTagTop(Integer i) {
		//SpringBoot 2.0 之后的用法
		Sort sort = Sort.by(Sort.Direction.DESC,"blogs.size");
		Pageable pageable = PageRequest.of(0,i,sort);
		List<Tag> tags = tagRepository.findTop(pageable);
		for (Tag tag : tags) {
			List<Blog> blogs = tag.getBlogs();
			//正确 可删除多个
			Iterator<Blog> iterator = blogs.iterator();
			while (iterator.hasNext()) {
				Blog b = iterator.next();
				if (b.isPublished()==false) {
					iterator.remove();//使用迭代器的删除方法删除
				}
			}
		}
		return tags;
	}

}
