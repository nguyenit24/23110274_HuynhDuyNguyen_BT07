package vn.iot.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import ch.qos.logback.core.model.Model;
import vn.iot.enity.CategoryEntity;
import vn.iot.model.CategoryModel;
import vn.iot.service.ICategoryService;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {
	@Autowired
	ICategoryService categoryService;
	
	@GetMapping("/add")
	public String add(ModelMap model) {
		CategoryModel category = new CategoryModel();
		category.setEdit(false);
		model.addAttribute("category", category);
		return "admin/category/addOrEdit";
	}
	
	@PostMapping("/saveOrUpdate")
	public String saveOrUpdate(ModelMap model, @ModelAttribute("category") CategoryModel category) {
		CategoryEntity entity = new CategoryEntity();
		BeanUtils.copyProperties(category, entity);
		categoryService.save(entity);
		
		String message = "New category was created";
		if(category.isEdit()) {
			message = "Category was updated";
		}
		model.addAttribute("message", message);
		return "redirect:/admin/categories";
	}
	
	@GetMapping("")
	public String list(ModelMap model) {
		model.addAttribute("categories", categoryService.findAll());
		return "admin/category/list";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(ModelMap model, @PathVariable("id") Long id) {
		CategoryEntity entity = categoryService.findById(id).orElse(null);
		if(entity != null) {
			CategoryModel category = new CategoryModel();
			BeanUtils.copyProperties(entity, category);
			category.setEdit(true);
			model.addAttribute("category", category);
			return "admin/category/addOrEdit";
		}
		model.addAttribute("message", "Category is not existed");
		return "forward:/admin/categories";
	}
	@GetMapping("/delete/{id}")
	public String delete(ModelMap model, @PathVariable("id") Long id) {
		categoryService.deleteById(id);
		model.addAttribute("message", "Category was deleted");
		return "forward:/admin/categories";
	}
	
//	@GetMapping("/search")
//	public String search(ModelMap model, @RequestParam(name = "name", required = false) String name) {
//		
//	
//	}
//	
}
