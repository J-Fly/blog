package com.wjh.web.admin;


import com.wjh.po.Blog;
import com.wjh.po.Type;
import com.wjh.service.BlogService;
import com.wjh.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Created by limi on 2017/10/16.
 */

@Controller
@RequestMapping("/admin")
public class TypeController {
	@Autowired
	private TypeService typeService;

	@Autowired
	private BlogService blogService;

	//@PageableDefault指定分页的默认参数
	@GetMapping("/types")
	public String types(@PageableDefault(size = 3, sort = {"id"}, direction = Sort.Direction.DESC)
								Pageable pageable, Model model) {
		model.addAttribute("page", typeService.listType(pageable));
		return "admin/types";
	}

	//返回新增的一个页面
	@GetMapping("/types/input")
	public String input(Model model) {
		model.addAttribute("type", new Type());
		return "admin/types-input";
	}

	//添加分类
	@PostMapping("/types")
	public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes) {
		Type type1 = typeService.getTypeByName(type.getName());
		System.out.println("type1=" + type1);
		if (type1 != null) {
			result.rejectValue("name", "nameError", "不能添加重复的分类");
		}
		if (result.hasErrors()) {
			return "admin/types-input";
		}
		Type t = typeService.saveType(type);
		if (t == null) {
			attributes.addFlashAttribute("message", "分类添加失败");
		} else {
			attributes.addFlashAttribute("message", "分类添加成功");
		}
		return "redirect:/admin/types";
	}


	//跳转到编辑分类页面
	@GetMapping("/types/{id}/input")
	public String editInput(@PathVariable Long id, Model model) {
		model.addAttribute("type", typeService.getType(id));
		return "admin/types-input";
	}

	//修改分类
	@PostMapping("/types/{id}")
	public String editPost(@Valid Type type, BindingResult result, @PathVariable Long id, RedirectAttributes attributes) {
		Type type1 = typeService.getTypeByName(type.getName());
		if (type1 != null) {
			result.rejectValue("name", "nameError", "不能添加重复的分类");
		}
		if (result.hasErrors()) {
			return "admin/types-input";
		}
		Type t = typeService.updateType(id, type);
		if (t == null) {
			attributes.addFlashAttribute("message", "分类修改失败");
		} else {
			attributes.addFlashAttribute("message", "分类修改成功");
		}
		return "redirect:/admin/types";
	}

	//	删除分类
	@GetMapping("/types/{id}/delete")
	public String delete(@PathVariable Long id, RedirectAttributes attributes,Pageable pageable) {
		//先判断是否有博客关联该分类
		Page<Blog> blogs = blogService.listBlog(id,pageable,"type");
		if (blogs.getTotalElements()>0){
			attributes.addFlashAttribute("message", "分类已被使用，请先删除对应博客！");
			attributes.addFlashAttribute("result", "negative");
			return "redirect:/admin/types";
		}
		typeService.deleteType(id);
		attributes.addFlashAttribute("message", "分类删除成功");
		attributes.addFlashAttribute("result", "success");
		return "redirect:/admin/types";
	}

}
