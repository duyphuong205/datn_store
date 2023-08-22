package com.anime.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.anime.entity.ImagesProduct;
import com.anime.entity.Product;
import com.anime.service.FileService;
import com.anime.service.ImagesProductService;
import com.anime.service.ProductService;
import com.anime.utils.SessionUtil;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	private final SessionUtil session;

	private final FileService fileService;

	private final ImagesProductService imagesProductService;

	@GetMapping("/admin/product")
	public String doShowView(Model model,
			@RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber,
			@RequestParam(value = "size", required = false, defaultValue = "10") int pageSize) {
		List<Product> products = new ArrayList<>();
		try {
			Page<Product> pageProducts = productService.getByIsActive(PageRequest.of(pageNumber - 1, pageSize));
			products = pageProducts.getContent();
			model.addAttribute("totalPages", pageProducts.getTotalPages());
			model.addAttribute("currentPage", pageNumber);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		model.addAttribute("products", products);
		return "admin/product";
	}

	@PostMapping("/admin/product/add")
	public String doAdd(@RequestParam("imageFiles") MultipartFile[] imageFiles, Product product) {
		try {
			if (product.getDiscount() == null) {
				product.setDiscount(0f);
			}

			product.setSelled(0);
			product.setView(0);

			productService.create(product);

			// save images of product
			List<ImagesProduct> images = new ArrayList<>();
			for (MultipartFile file : imageFiles) {
				String fileName = fileService.uploadFileCloudinary(file);

				ImagesProduct imagesProduct = null;
				if (product.getId() != null) {
					imagesProduct = new ImagesProduct(fileName, product);
				}
				images.add(imagesProduct);
			}
			imagesProductService.saveImages(images);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return "redirect:/admin/product";
	}

	@ResponseBody
	@GetMapping("/admin/product/edit/{id}")
	public Product doEdit(@PathVariable("id") Long id) {
		return productService.findById(id);
	}

	@PostMapping("/admin/product/update")
	public String doUpdate(Product product) {
		try {
			productService.update(product);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return "redirect:/admin/product";
	}

	@GetMapping("/admin/product/delete/{id}")
	public String doDelete(@PathVariable("id") Long id) {
		try {
			productService.deleteLogical(id);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return "redirect:/admin/product";
	}

	@GetMapping("/admin/product/search")
	public String doSearch(Model model, @RequestParam(value = "keyword", required = false) Optional<String> kw,
			@RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber,
			@RequestParam(value = "size", required = false, defaultValue = "10") int pageSize) {
		List<Product> products = new ArrayList<>();
		try {
			String keyword = kw.orElse(session.getAttribute("keyword"));
			session.setAttribute("keyword", keyword);
			Page<Product> pageProducts = productService.getByKeyword(keyword, PageRequest.of(pageNumber - 1, pageSize));
			products = pageProducts.getContent();
			model.addAttribute("totalPages", pageProducts.getTotalPages());
			model.addAttribute("currentPage", pageNumber);
			model.addAttribute("keyword", keyword);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		model.addAttribute("products", products);
		return "admin/product";
	}
	
	@ResponseBody
	@GetMapping("/admin/product/images-detail/{id}")
	public List<ImagesProduct> doShowImagesDetail(@PathVariable("id") Long id) {
		return imagesProductService.getByProductId(id);
	}

}
