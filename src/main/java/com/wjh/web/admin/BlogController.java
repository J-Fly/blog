package com.wjh.web.admin;

import com.wjh.po.Blog;
import com.wjh.po.User;
import com.wjh.service.BlogService;
import com.wjh.service.CommentService;
import com.wjh.service.TagService;
import com.wjh.service.TypeService;
import com.wjh.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BlogController {

	private static final String INPUT = "admin/blogs-input";
	private static final String LIST = "admin/blogs";
	private static final String REDIRECT_LIST = "redirect:/admin/blogs";

	@Autowired
	private BlogService blogService;
	@Autowired
	private TypeService typeService;
	@Autowired
	private TagService tagService;
	@Autowired
	private CommentService commentService;

	@GetMapping("/blogs")
	public String blogs(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
						BlogQuery blog, Model model) {
		model.addAttribute("types", typeService.listType());
		model.addAttribute("page", blogService.listBlog(pageable, blog));
		return LIST;
	}

	@PostMapping("/blogs/search")
	public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
						 BlogQuery blog, Model model) {
		model.addAttribute("page", blogService.listBlog(pageable, blog));
		//返回admin下面的blogs.html页面下的blogList片段
		return "admin/blogs :: blogList";
	}

	@GetMapping("/blogs/input")
	public String input(Model model) {
		model.addAttribute("types", typeService.listType());
		model.addAttribute("tags", tagService.listTag());
		model.addAttribute("blog", new Blog());
		return INPUT;
	}


	private void setTypeAndTag(Model model) {
		model.addAttribute("types", typeService.listType());
		model.addAttribute("tags", tagService.listTag());
	}

	//编辑博客
	@GetMapping("/blogs/{id}/input")
	public String editInput(@PathVariable Long id, Model model) {
		setTypeAndTag(model);
		Blog blog = blogService.getBlog(id);
		blog.init();
		model.addAttribute("blog", blog);
		model.addAttribute("content", blog.getContent());
		return INPUT;
	}

	//获取博客内容
	@GetMapping("/blogs/{id}/content")
	@ResponseBody
	public Blog getContent(@PathVariable Long id, Model model) {
		Blog blog = blogService.getBlog(id);
		return blog;
	}

	//删除博客
	@GetMapping("/blogs/{id}/delete")
	public String delete(@PathVariable Long id,RedirectAttributes attributes) {
		//删除博客前要先删除对应评论，不然会因为有外键关联报错
		Blog blog = blogService.getBlog(id);
		commentService.deleteByBlog(blog);
		blogService.deleteBlog(id);
		attributes.addFlashAttribute("message", "删除成功");
		return REDIRECT_LIST;
	}


	//post方式提交表单数据
	@PostMapping("/blogs")
	public String post(Blog blog, RedirectAttributes attributes, HttpSession session) {
		blog.setUser((User) session.getAttribute("user"));
		if (blog.getFlag()==null||blog.getFlag().equals("")){
			blog.setFlag("原创");
		}
		blog.setType(typeService.getType(blog.getType().getId()));
		blog.setTags(tagService.listTag(blog.getTagIds()));
		Blog b;
		if (blog.getId() == null) {
			b = blogService.saveBlog(blog);
		} else {
			b = blogService.updateBlog(blog.getId(), blog);
		}
		if (b == null) {
			attributes.addFlashAttribute("message", "操作失败");
		} else {
			attributes.addFlashAttribute("message", "操作成功");
		}
		return REDIRECT_LIST;
	}
}
