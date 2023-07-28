package com.anime.api;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anime.constants.ActiveConstant;
import com.anime.entity.Product;
import com.anime.entity.Review;
import com.anime.entity.User;
import com.anime.service.ProductService;
import com.anime.service.ReviewService;
import com.anime.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewApi {
	private final ReviewService reviewService;

	private final UserService userService;

	private final ProductService productService;

	@GetMapping("/submit")
	public ResponseEntity<?> doSubmitReview(HttpServletRequest request, 
											@RequestParam("productId") Long id,
											@RequestParam("rating") Integer rating,
											@RequestParam("comment") String comment) {
		try {
			String username = request.getRemoteUser();
			User user = userService.getByUsername(username);
			
			if (ObjectUtils.isEmpty(user)) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
			
			Product product = productService.findById(id);
			
			Review review = new Review();
			review.setRating(rating);
			review.setComment(comment);
			review.setUser(user);
			review.setProduct(product);
			review.setIsActive(ActiveConstant.DISABLE);
			
			reviewService.create(review);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
