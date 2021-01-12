package com.wjh.service;

import com.wjh.po.Blog;
import com.wjh.po.Comment;

import java.util.List;

public interface CommentService {
	//	获取评论
	List<Comment> listCommentByBlogId(Long blogId);

	//	保存评论
	Comment saveComment(Comment comment);

	//根据博客删除评论
	void deleteByBlog(Blog blog);
}
