package com.wjh.dao;

import com.wjh.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {
	@Query("select b from Blog b where b.recommend = true and b.published = true")
	List<Blog> findTop(Pageable pageable);

	//全局搜索  ?1代表传入的第一个参数，即query，这里做like查询并不会自动帮我们处理字符串，需要在controller层自己拼接%%
	@Query("select b from Blog b where (b.title like ?1 or b.content like ?1) and b.published = true")
	Page<Blog> findByQuery(String query, Pageable pageable);

	@Modifying
	@Query("update Blog b set b.views = b.views+1 where b.id=?1")
	int updateViews(Long id);

	//获得数据库中博客信息所有年份分组
	@Query("select function('date_format',b.updateTime,'%Y') as year from Blog b where b.published = true group by function('date_format',b.updateTime,'%Y') order by year desc ")
	List<String> findGroupYear();


	//根据年份查询对应博客
	@Query("select b from Blog b where function('date_format',b.updateTime,'%Y') = ?1 and b.published = true")
	List<Blog> findByYear(String year);


	@Query("select b from Blog b where b.published = true")
	Page<Blog> listBlogIsPublished(Pageable pageable);
}
