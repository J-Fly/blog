package com.wjh.web;


import com.wjh.po.Type;
import com.wjh.service.BlogService;
import com.wjh.service.TypeService;
import com.wjh.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TypeShowController {
	@Autowired
	private TypeService typeService;

	@Autowired
	private BlogService blogService;

	@GetMapping("types/{id}")
	public String types(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
						@PathVariable Long id, Model model) {

		List<Type> types = typeService.listTypeTop(10000);
		if (id == -1&&types.size()>0) {
			id = types.get(0).getId();
		}
		Type type = typeService.getType(id);
		int blogSize = type.getBlogs().size();
		BlogQuery blogQuery=new BlogQuery();
		blogQuery.setTypeId(id);
		model.addAttribute("types",types);
		model.addAttribute("page", blogService.listBlog(id,pageable,"type"));
		model.addAttribute("activeTypeId",id);
		model.addAttribute("blogSize", blogSize);
		return "types";
	}
}
