package com.wjh.service;

import com.wjh.po.Blog;
import com.wjh.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {
	Blog getBlog(Long id);

	Blog getAndConvert(Long id);

	//根据searchType来查询博客列表，searchType为type根据分类查找，searchType为tag根据标签查找
	Page<Blog> listBlog(Long tagId, Pageable pageable,String searchType);

	Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

	Blog saveBlog(Blog blog);

	Blog updateBlog(Long id, Blog blog);

	void deleteBlog(Long id);

	Page<Blog> listBlog(Pageable pageable);

	List<Blog> listRecommendBlogTop(Integer size);

	Page<Blog> listBlog(String query, Pageable pageable);

	Map<String,List<Blog>> archiveBlog();

	Long countBlog();

	Page<Blog> listBlogIsPublished(Pageable pageable);
}
