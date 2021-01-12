package com.wjh.web;

import com.wjh.po.Comment;
import com.wjh.po.User;
import com.wjh.service.BlogService;
import com.wjh.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {

	@Autowired
	private CommentService commentService;

	@Autowired
	private BlogService blogService;

	@Value("${comment.avatar}")
	private String avatar;

	@GetMapping("/comments/{blogId}")
	public String comments(@PathVariable Long blogId, Model model) {
		model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
		return "blog :: commentList";
	}

	//提交和恢复评论
	@PostMapping("/comments")
	public String post(Comment comment, HttpSession session) {
		Long blogId = comment.getBlog().getId();
		comment.setBlog(blogService.getBlog(blogId));
		User user = (User) session.getAttribute("user");
		if (user != null) {
			//说明用户登录了
			comment.setAvatar(user.getAvatar());
			comment.setAdminComment(true);
		} else {
			comment.setAvatar(avatar);
		}
		commentService.saveComment(comment);
		return "redirect:/comments/" + comment.getBlog().getId();
	}
}
