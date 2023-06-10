package com.anime.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.anime.entity.ImagesProduct;
import com.anime.entity.Product;
import com.anime.entity.Review;
import com.anime.service.ImagesProductService;
import com.anime.service.ProductService;
import com.anime.service.ReviewService;
import com.anime.utils.SessionUtil;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ShopController {

	private final ProductService productService;

	private final ImagesProductService imagesProductService;

	private final SessionUtil session;

	private final ReviewService reviewService;

	@GetMapping("/shop")
	public String doShowShop(@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
			@RequestParam(value = "size", required = false, defaultValue = "9") int pageSize, Model model) {
		List<Product> products = new ArrayList<>();
		try {
			Page<Product> pageProducts = productService.getByIsActive(PageRequest.of(pageNum - 1, pageSize));
			products = pageProducts.getContent();
			model.addAttribute("totalPages", pageProducts.getTotalPages());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("products", products);
		return "/user/shop";
	}

	@GetMapping("/shop/{slug}")
	public String doFindBySlug(Model model, @PathVariable("slug") String slug,
			@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
			@RequestParam(value = "size", required = false, defaultValue = "4") int pageSize) {
		Product productResp = productService.getBySlug(slug);
		if (productResp.getView() == 0 || productResp.getView() == null) {
			productResp.setView(1);
		} else {
			productResp.setView(productResp.getView() + 1);
		}
		productService.update(productResp);
		List<ImagesProduct> images = imagesProductService.getByProductId(productResp.getId());
		model.addAttribute("images", images);
		model.addAttribute("product", productResp);

		// display review
		List<Review> reviews = new ArrayList<>();
		try {
			Page<Review> reviewPage = reviewService.getByProductId(productResp.getId(),
					PageRequest.of(pageNum - 1, pageSize));
			reviews = reviewPage.getContent();
			model.addAttribute("totalPages", reviewPage.getTotalPages());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("reviews", reviews);

		return "user/product-detail";
	}

	@GetMapping("/shop/category/{name}")
	public String doFindByCategoryName(@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
			@RequestParam(value = "size", required = false, defaultValue = "9") int pageSize, Model model,
			@PathVariable("name") String name) {
		List<Product> products = new ArrayList<>();
		try {
			Page<Product> pageProducts = productService.getByCategoryName(name, PageRequest.of(pageNum - 1, pageSize));
			products = pageProducts.getContent();
			model.addAttribute("totalPages", pageProducts.getTotalPages());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("products", products);
		return "/user/shop";
	}

	@GetMapping("/shop/search")
	public String doSearchProduct(Model model, @RequestParam(value = "keyword", required = false) Optional<String> kw,
			@RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber,
			@RequestParam(value = "size", required = false, defaultValue = "9") int pageSize) {
		List<Product> products = new ArrayList<>();
		try {
			String keyword = kw.orElse(session.getAttribute("keyword"));
			session.setAttribute("keyword", keyword);
			Page<Product> pageProducts = productService.getByKeyword(keyword, PageRequest.of(pageNumber - 1, pageSize));
			products = pageProducts.getContent();
			model.addAttribute("totalPages", pageProducts.getTotalPages());
			model.addAttribute("currentPage", pageNumber);
			model.addAttribute("keyword", keyword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("products", products);
		return "user/shop";
	}

	@GetMapping("/shop/searchPrice")
	public String doSearchPriceProduct(Model model, @RequestParam(value = "min", required = false) Optional<Double> min,
			@RequestParam(value = "max", required = false) Optional<Double> max,
			@RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber,
			@RequestParam(value = "size", required = false, defaultValue = "9") int pageSize) {
		List<Product> products = new ArrayList<>();
		try {
			Double minPrice = min.orElse(session.getAttribute("min"));
			Double maxPrice = max.orElse(session.getAttribute("max"));
			session.setAttribute("min", minPrice);
			session.setAttribute("max", maxPrice);
			Page<Product> pageProducts = productService.getByPrice(minPrice, maxPrice,
					PageRequest.of(pageNumber - 1, pageSize));
			products = pageProducts.getContent();
			model.addAttribute("totalPages", pageProducts.getTotalPages());
			model.addAttribute("currentPage", pageNumber);
			model.addAttribute("min", minPrice);
			model.addAttribute("max", maxPrice);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("products", products);
		return "user/shop";
	}

	@GetMapping("/shop/sortPriceDesc")
	public String sortPriceDesc(@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
			@RequestParam(value = "size", required = false, defaultValue = "9") int pageSize, Model model) {
		List<Product> products = new ArrayList<>();
		try {
			Page<Product> pageProducts = productService.sortHightToLow(PageRequest.of(pageNum - 1, pageSize));
			products = pageProducts.getContent();
			model.addAttribute("totalPages", pageProducts.getTotalPages());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("products", products);
		return "/user/shop";
	}

	@GetMapping("/shop/sortPriceAsc")
	public String sortPriceAsc(@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
			@RequestParam(value = "size", required = false, defaultValue = "9") int pageSize, Model model) {
		List<Product> products = new ArrayList<>();
		try {
			Page<Product> pageProducts = productService.sortLowToHight(PageRequest.of(pageNum - 1, pageSize));
			products = pageProducts.getContent();
			model.addAttribute("totalPages", pageProducts.getTotalPages());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("products", products);
		return "/user/shop";
	}

	@GetMapping("/shop/sortViewDesc")
	public String sortViewDesc(@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
			@RequestParam(value = "size", required = false, defaultValue = "9") int pageSize, Model model) {
		List<Product> products = new ArrayList<>();
		try {
			Page<Product> pageProducts = productService.sortView(PageRequest.of(pageNum - 1, pageSize));
			products = pageProducts.getContent();
			model.addAttribute("totalPages", pageProducts.getTotalPages());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("products", products);
		return "/user/shop";
	}

	@GetMapping("/shop/sortSelledDesc")
	public String sortSelledDesc(@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
			@RequestParam(value = "size", required = false, defaultValue = "9") int pageSize, Model model) {
		List<Product> products = new ArrayList<>();
		try {
			Page<Product> pageProducts = productService.sortSelled(PageRequest.of(pageNum - 1, pageSize));
			products = pageProducts.getContent();
			model.addAttribute("totalPages", pageProducts.getTotalPages());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("products", products);
		return "/user/shop";
	}
}
