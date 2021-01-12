package com.wjh.service;

import com.wjh.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by limi on 2017/10/16.
 */
public interface TagService {

	Tag saveTag(Tag type);

	Tag getTag(Long id);

	Tag getTagByName(String name);

	Page<Tag> listTag(Pageable pageable);

	Tag updateTag(Long id, Tag type);

	List<Tag> listTag(String ids);

	void deleteTag(Long id);

	List<Tag> listTag();

	List<Tag> listTagTop(Integer i);
}
